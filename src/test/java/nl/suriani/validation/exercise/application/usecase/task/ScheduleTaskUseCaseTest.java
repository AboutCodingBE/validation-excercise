package nl.suriani.validation.exercise.application.usecase.task;

import nl.suriani.validation.exercise.application.port.sensor.SensorAtManufacturer;
import nl.suriani.validation.exercise.application.port.sensor.SensorManufacturerGateway;
import nl.suriani.validation.exercise.application.port.sensor.SensorRepository;
import nl.suriani.validation.exercise.application.port.task.TaskRepository;
import nl.suriani.validation.exercise.domain.sensor.*;
import nl.suriani.validation.exercise.domain.sensor.events.ConfigurationUpdateNotScheduled;
import nl.suriani.validation.exercise.domain.sensor.events.ConfigurationUpdateScheduled;
import nl.suriani.validation.exercise.domain.sensor.events.FirmwareUpdateNotScheduled;
import nl.suriani.validation.exercise.domain.sensor.events.FirmwareUpdateScheduled;
import nl.suriani.validation.exercise.domain.shared.FileName;
import nl.suriani.validation.exercise.domain.task.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.exceptions.verification.WantedButNotInvoked;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleTaskUseCaseTest {

    @Mock
    private SensorRepository sensorRepository;

    @Mock
    private SensorManufacturerGateway sensorManufacturerGateway;

    @Mock
    private TaskRepository taskRepository;

    @Captor
    private ArgumentCaptor<Task> taskArgumentCaptor;

    @Captor
    private ArgumentCaptor<Sensor> sensorArgumentCaptor;

    @InjectMocks
    private ScheduleTaskUseCase useCase;

    @ParameterizedTest
    @MethodSource("commandAndUpdateNotScheduledEventProvider")
    void sensorIsNotFound(ScheduleTaskCommand command) {
        whenSensorIsNotFound();

        var result = useCase.apply(command);

        thenUpdateHasNotBeenScheduledBecauseSensorIsNotFound(result);
    }

    @ParameterizedTest
    @MethodSource("commandAndUpdateNotScheduledEventProvider")
    void sensorIsNotKnownByManufacturer(ScheduleTaskCommand command, Class generatedEvent) {
        whenSensorHasBeenFetchedAndHasStatus(SensorTasksStatus.IDLE);
        whenSensorAtManufacturerHasBeenFetchedAndItIsNotFound();

        var result = useCase.apply(command);

        thenUpdateHasNotBeenScheduledBecauseSensorIsNotKnownByManufacturer(result);
        thenSensorIsUpdatedWithEvent(generatedEvent);
    }

    @ParameterizedTest
    @MethodSource("commandAndUpdateNotScheduledEventProvider")
    void sensorIsAlreadyUpdating(ScheduleTaskCommand command, Class generatedEvent) {
        whenSensorHasBeenFetchedAndHasStatus(SensorTasksStatus.IDLE);
        whenSensorAtManufacturerHasBeenFetchedAndHasStatus(SensorTasksStatus.UPDATING);

        var result = useCase.apply(command);

        thenUpdateHasNotBeenScheduledBecauseSensorIsAlreadyUpdating(result);
        thenSensorIsUpdatedWithEvent(generatedEvent);
    }

    @ParameterizedTest
    @MethodSource("commandAndUpdateNotScheduledEventProvider")
    void lastUpdateInformationIsNotFound(ScheduleTaskCommand command, Class generatedEvent) {
        whenSensorHasBeenFetchedAndHasStatus(SensorTasksStatus.IDLE);
        whenSensorAtManufacturerHasBeenFetchedAndHasStatus(SensorTasksStatus.IDLE);
        whenEitherLastFirmwareOrLastConfigurationIsNotFound();

        var result = useCase.apply(command);

        thenUpdateHasNotBeenScheduledAndResultCodeIsError(result);
        thenSensorIsUpdatedWithEvent(generatedEvent);
    }

    @ParameterizedTest
    @MethodSource("commandAndUpdateNotScheduledEventProvider")
    void errorWhileSchedulingTask(ScheduleTaskCommand command, Class generatedEvent) {
        whenSensorHasBeenFetchedAndHasStatus(SensorTasksStatus.IDLE);
        whenSensorAtManufacturerHasBeenFetchedAndHasStatus(SensorTasksStatus.IDLE);
        whenEitherLastFirmwareOrLastConfigurationIsFound();
        whenSchedulingTaskFailsDueToAnError();

        var result = useCase.apply(command);

        thenUpdateHasNotBeenScheduledAndResultCodeIsError(result);
        thenSensorIsUpdatedWithEvent(generatedEvent);
    }

    @ParameterizedTest
    @MethodSource("commandAndUpdateScheduledEventProvider")
    void taskIsScheduledSuccessfully(ScheduleTaskCommand command, Class generatedEvent) {
        whenSensorHasBeenFetchedAndHasStatus(SensorTasksStatus.IDLE);
        whenSensorAtManufacturerHasBeenFetchedAndHasStatus(SensorTasksStatus.IDLE);
        whenEitherLastFirmwareOrLastConfigurationIsFound();
        whenSchedulingIsSuccessful(command.taskType());

        var result = useCase.apply(command);

        thenResultCodeIsTaskScheduled(result);
        thenTheTaskHasBeenScheduled(command.taskType());
        thenSensorIsUpdatedWithEvent(generatedEvent);
        thenTheTaskHasBeenSavedInternally(command.taskType());
    }

    private static Stream<Arguments> commandAndUpdateNotScheduledEventProvider() {
        return Stream.of(
                Arguments.of(givenCommandWithTaskTypeUpdateFirmware(), FirmwareUpdateNotScheduled.class),
                Arguments.of(givenCommandWithTaskTypeUpdateConfiguration(), ConfigurationUpdateNotScheduled.class)
        );
    }

    private static Stream<Arguments> commandAndUpdateScheduledEventProvider() {
        return Stream.of(
                Arguments.of(givenCommandWithTaskTypeUpdateFirmware(), FirmwareUpdateScheduled.class),
                Arguments.of(givenCommandWithTaskTypeUpdateConfiguration(), ConfigurationUpdateScheduled.class)
        );
    }

    private static ScheduleTaskCommand givenCommandWithTaskTypeUpdateFirmware() {
        return new ScheduleTaskCommand(new SensorId(), TaskType.UPDATE_FIRMWARE);
    }

    private static ScheduleTaskCommand givenCommandWithTaskTypeUpdateConfiguration() {
        return new ScheduleTaskCommand(new SensorId(), TaskType.UPDATE_CONFIGURATION);
    }

    private void whenEitherLastFirmwareOrLastConfigurationIsNotFound() {
        lenient().when(sensorManufacturerGateway.getLastFirmwareInformation()).thenReturn(Optional.empty());
        lenient().when(sensorManufacturerGateway.getLastConfigurationInformation()).thenReturn(Optional.empty());
    }

    private void whenEitherLastFirmwareOrLastConfigurationIsFound() {
        var fileName = new FileName("SomeFileName.whatever");
        lenient().when(sensorManufacturerGateway.getLastFirmwareInformation()).thenReturn(Optional.of(fileName));
        lenient().when(sensorManufacturerGateway.getLastConfigurationInformation()).thenReturn(Optional.of(fileName));
    }

    private void whenSensorHasBeenFetchedAndHasStatus(SensorTasksStatus sensorTasksStatus) {
        when(sensorRepository.findById(any()))
                .thenReturn(Optional.of(Sensor.registered(new SensorId(),
                                new Firmware(new FirmwareVersion(1, 1, 2020, 1)),
                                new Configuration(new FileName("OldConf.cfg")),
                                sensorTasksStatus,
                                SensorActivityStatus.ONLINE)
                        .firmwareUpdateRequired()));
    }

    private void whenSensorIsNotFound() {
        when(sensorRepository.findById(any()))
                .thenReturn(Optional.empty());
    }

    private void whenSensorAtManufacturerHasBeenFetchedAndHasStatus(SensorTasksStatus sensorTasksStatus) {
        when(sensorManufacturerGateway.findSensorById(any()))
                .thenReturn(Optional.of(new SensorAtManufacturer(new SensorId(),
                                new Firmware(new FirmwareVersion(1, 1, 2020, 1)),
                                new Configuration(new FileName("OldConf.cfg")),
                                sensorTasksStatus,
                                SensorActivityStatus.ONLINE)));
    }

    private void whenSensorAtManufacturerHasBeenFetchedAndItIsNotFound() {
        when(sensorManufacturerGateway.findSensorById(any()))
                .thenReturn(Optional.empty());
    }

    private void whenSchedulingTaskFailsDueToAnError() {
        lenient().when(sensorManufacturerGateway.scheduleConfigurationUpdate(any(), any()))
                .thenThrow(new RuntimeException("boom!"));

        lenient().when(sensorManufacturerGateway.scheduleFirmwareUpdate(any(), any()))
                .thenThrow(new RuntimeException("boom!"));
    }

    private void whenSchedulingIsSuccessful(TaskType taskType) {
        var task = new Task(new SensorId(), new TaskId(), new UpdateFile(new FileName("bla")),
                taskType, TaskStatus.PENDING);

        switch (taskType) {
            case UPDATE_FIRMWARE -> when(sensorManufacturerGateway.scheduleFirmwareUpdate(any(), any()))
                    .thenReturn(task);
            case UPDATE_CONFIGURATION -> when(sensorManufacturerGateway.scheduleConfigurationUpdate(any(), any()))
                    .thenReturn(task);
        }
    }

    private void thenUpdateHasNotBeenScheduledBecauseSensorIsNotFound(ScheduleTaskUseCaseResult result) {
        assertEquals(ScheduleTaskUseCaseResultCode.SENSOR_NOT_FOUND, result.code());
        verify(sensorRepository, times(0)).save(any());
    }

    private void thenUpdateHasNotBeenScheduledBecauseSensorIsNotKnownByManufacturer(ScheduleTaskUseCaseResult result) {
        assertEquals(ScheduleTaskUseCaseResultCode.SENSOR_NOT_KNOWN_BY_MANUFACTURER, result.code());
    }

    private void thenUpdateHasNotBeenScheduledBecauseSensorIsAlreadyUpdating(ScheduleTaskUseCaseResult result) {
        assertEquals(ScheduleTaskUseCaseResultCode.SENSOR_IS_ALREADY_UPDATING, result.code());
    }

    private void thenUpdateHasNotBeenScheduledAndResultCodeIsError(ScheduleTaskUseCaseResult result) {
        assertEquals(ScheduleTaskUseCaseResultCode.ERROR, result.code());
    }

    private void thenResultCodeIsTaskScheduled(ScheduleTaskUseCaseResult result) {
        assertEquals(ScheduleTaskUseCaseResultCode.TASK_SCHEDULED, result.code());
    }

    private void thenSensorIsUpdatedWithEvent(Class clazz) {
        verify(sensorRepository).save(sensorArgumentCaptor.capture());

        var sensor = sensorArgumentCaptor.getValue();
        assertEquals(clazz, sensor.events().get(sensor.events().size() -1).getClass());
    }

    private void thenTheTaskHasBeenScheduled(TaskType taskType) {
        var scheduledFirmwareUpdate = verificationSucceeds(() ->
                verify(sensorManufacturerGateway).scheduleFirmwareUpdate(any(), any()));

        var scheduledConfigurationUpdate = verificationSucceeds(() ->
                verify(sensorManufacturerGateway).scheduleConfigurationUpdate(any(), any()));

        switch (taskType) {
            case UPDATE_FIRMWARE -> assertTrue(scheduledFirmwareUpdate);
            case UPDATE_CONFIGURATION -> assertTrue(scheduledConfigurationUpdate);
        }
    }

    private void thenTheTaskHasBeenSavedInternally(TaskType taskType) {
        verify(taskRepository, only()).save(taskArgumentCaptor.capture());

        var task = taskArgumentCaptor.getValue();
        assertEquals(taskType, task.type());
    }

    private boolean verificationSucceeds(Runnable verification) {
        try {
            verification.run();
            return true;
        } catch (WantedButNotInvoked wantedButNotInvoked) {
            return false;
        }
    }
}