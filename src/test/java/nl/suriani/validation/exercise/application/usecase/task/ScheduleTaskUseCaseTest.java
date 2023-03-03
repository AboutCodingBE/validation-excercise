package nl.suriani.validation.exercise.application.usecase.task;

import nl.suriani.validation.exercise.application.port.sensor.SensorAtManufacturer;
import nl.suriani.validation.exercise.application.port.sensor.SensorManufacturerGateway;
import nl.suriani.validation.exercise.application.port.sensor.SensorRepository;
import nl.suriani.validation.exercise.application.port.task.TaskRepository;
import nl.suriani.validation.exercise.domain.sensor.*;
import nl.suriani.validation.exercise.domain.shared.FileName;
import nl.suriani.validation.exercise.domain.task.Task;
import nl.suriani.validation.exercise.domain.task.TaskType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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

    @Test
    void sensorIsNotFound() {
        var command = givenCommandWithTaskTypeUpdateFirmware();
        whenSensorIsNotFound();

        var result = useCase.apply(command);

        thenUpdateHasFailedBecauseSensorIsNotFound(result);
    }

    @Test
    void updateLastFirmwareNotFound_error() {
        //var command = givenCommandWithTaskTypeUpdateFirmware();
        //
        //whenLastFirmwareIsNotFound();
//
        //var result = useCase.apply(command);
//
        //thenUpdateHasFailed(result);
        //thenSensorIsUpdatedWithEvent(FirmwareUpdateFailed.class);
    }

    private ScheduleTaskCommand givenCommandWithTaskTypeUpdateFirmware() {
        return new ScheduleTaskCommand(new SensorId(), TaskType.UPDATE_FIRMWARE);
    }

    private ScheduleTaskCommand givenCommandWithTaskTypeUpdateConfiguration() {
        return new ScheduleTaskCommand(new SensorId(), TaskType.UPDATE_CONFIGURATION);
    }

    private void whenLastFirmwareIsNotFound() {
        when(sensorManufacturerGateway.getLastFirmwareInformation()).thenReturn(Optional.empty());
    }

    private void whenLastConfigurationIsNotFound() {
        when(sensorManufacturerGateway.getLastConfigurationInformation()).thenReturn(Optional.empty());
    }

    private void thenUpdateHasFailed(ScheduleTaskUseCaseResult result) {
        assertEquals(ScheduleTaskUseCaseResultCode.ERROR, result.code());
    }

    private void thenUpdateHasFailedBecauseSensorIsNotFound(ScheduleTaskUseCaseResult result) {
        assertEquals(ScheduleTaskUseCaseResultCode.SENSOR_NOT_FOUND, result.code());
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

    private void thenSensorIsUpdatedWithEvent(Class clazz) {
        verify(sensorRepository).save(sensorArgumentCaptor.capture());

        var sensor = sensorArgumentCaptor.getValue();
        assertEquals(clazz, sensor.events().get(sensor.events().size() -1).getClass());
    }
}