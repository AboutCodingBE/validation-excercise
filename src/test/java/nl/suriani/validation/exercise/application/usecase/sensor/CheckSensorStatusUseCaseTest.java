package nl.suriani.validation.exercise.application.usecase.sensor;

import nl.suriani.validation.exercise.adapters.sensor.SensorManufacturerGatewayStub;
import nl.suriani.validation.exercise.adapters.sensor.SensorRepositoryStub;
import nl.suriani.validation.exercise.application.port.sensor.SensorAtManufacturer;
import nl.suriani.validation.exercise.domain.sensor.*;
import nl.suriani.validation.exercise.domain.sensor.events.ConfigurationUpdateRequired;
import nl.suriani.validation.exercise.domain.sensor.events.FirmwareUpdateRequired;
import nl.suriani.validation.exercise.domain.sensor.events.SensorRegistered;
import nl.suriani.validation.exercise.domain.shared.DomainEvent;
import nl.suriani.validation.exercise.domain.shared.FileName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static nl.suriani.validation.exercise.application.usecase.sensor.UpdateSensorStatusUseCaseResultCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateSensorStatusUseCaseTest {

    @Mock
    private SensorRepositoryStub sensorRepository;

    @Mock
    private SensorManufacturerGatewayStub sensorManufacturerGateway;

    @Captor
    private ArgumentCaptor<Sensor> sensorArgumentCaptor;

    @InjectMocks
    private UpdateSensorStatusUseCase useCase;

    @Test
    void sensorHasNotBeenRegistered() {
        var sensorId = new SensorId("notRegistered");
        var command = new UpdateSensorStatusCommand(sensorId);

        var result = useCase.apply(command);

        assertEquals(NOT_REGISTERED, result.code());

        verify(sensorRepository, only()).findById(sensorId);
    }

    @Nested
    class SensorHasBeenRegistered {
        private final SensorId sensorId = new SensorId();
        private final UpdateSensorStatusCommand command = new UpdateSensorStatusCommand(sensorId);

        private final Sensor sensor = Sensor.registered(sensorId, new Firmware(new FirmwareVersion(1, 1, 2020, 1)),
                new Configuration(new FileName("someOldCfg.cfg")),
                SensorTasksStatus.IDLE,
                SensorActivityStatus.ONLINE);

        private final SensorAtManufacturer sensorAtManufacturer = new SensorAtManufacturer(sensorId, sensor.firmware(), sensor.configuration(), sensor.tasksStatus(),
                SensorActivityStatus.DISCONNECTED);

        @Test
        void sensorNotKnownByManufacturer() {
            whenSensorIsRegistered();
            whenSensorIsNotKnownByManufacturer();

            var result = whenSensorStatusIsChecked();

            thenAnswerContainsErrorSensorNotKnownByManufacturer(result);

            thenSensorHasBeenLookedUp();
            thenSensorHasBeenSaved1Time();
            thenSensorHasBeenLookedUpAtManufacturer();
            thenSensorEventsLookLikeThis(SensorRegistered.class);
        }

        @Test
        void sensorKnownByManufacturer_firmwareUpdateNeeded() {
            whenSensorIsRegistered();
            whenSensorIsKnownByManufacturer();
            whenFirmwareUpdateIsNeeded();

            var result = whenSensorStatusIsChecked();

            thenAnswerIs(result, FIRMWARE_UPDATE_REQUIRED);

            thenSensorHasBeenLookedUp();
            thenSensorHasBeenSaved2Times();
            thenSensorEventsLookLikeThis(SensorRegistered.class, FirmwareUpdateRequired.class);
        }

        @Test
        void sensorKnownByManufacturer_configurationUpdateNeeded() {
            whenSensorIsRegistered();
            whenSensorIsKnownByManufacturer();
            whenNoFirmwareUpdateIsNeeded();
            whenConfigurationUpdateIsNeeded();

            var result = whenSensorStatusIsChecked();

            thenAnswerIs(result, CONFIGURATION_UPDATE_REQUIRED);

            thenSensorHasBeenLookedUp();
            thenSensorHasBeenSaved2Times();
            thenSensorEventsLookLikeThis(SensorRegistered.class, ConfigurationUpdateRequired.class);
        }

        @Test
        void sensorKnownByManufacturer_noActionNeeded() {

            whenSensorIsRegistered();
            whenSensorIsKnownByManufacturer();
            whenNoFirmwareUpdateIsNeeded();
            whenNoConfigurationUpdateIsNeeded();
            whenNoConfigurationUpdateIsNeeded();

            var result = whenSensorStatusIsChecked();

            thenAnswerIs(result, NO_ACTION_NEEDED);

            thenSensorHasBeenLookedUp();
            thenSensorHasBeenSaved2Times();
            thenSensorEventsLookLikeThis(SensorRegistered.class);
        }

        private void whenSensorIsNotKnownByManufacturer() {
            when(sensorManufacturerGateway.findSensorById(sensorId))
                    .thenReturn(Optional.empty());
        }

        private void whenSensorIsKnownByManufacturer() {
            when(sensorManufacturerGateway.findSensorById(sensorId))
                    .thenReturn(Optional.of(sensorAtManufacturer));
        }

        private void whenSensorIsRegistered() {
            when(sensorRepository.findById(sensorId))
                    .thenReturn(Optional.of(sensor));
        }

        private void whenNoConfigurationUpdateIsNeeded() {
            when(sensorManufacturerGateway.needsConfigurationUpdate(sensorAtManufacturer))
                    .thenReturn(false);
        }

        private void whenNoFirmwareUpdateIsNeeded() {
            when(sensorManufacturerGateway.needsFirmwareUpdate(sensorAtManufacturer))
                    .thenReturn(false);
        }

        private void whenFirmwareUpdateIsNeeded() {
            when(sensorManufacturerGateway.needsFirmwareUpdate(sensorAtManufacturer))
                    .thenReturn(true);
        }

        private void whenConfigurationUpdateIsNeeded() {
            when(sensorManufacturerGateway.needsConfigurationUpdate(sensorAtManufacturer))
                    .thenReturn(true);
        }

        private UpdateSensorStatusUseCaseResult whenSensorStatusIsChecked() {
            return useCase.apply(command);
        }

        private void thenSensorHasBeenSaved1Time() {
            verify(sensorRepository, times(1)).save(sensorArgumentCaptor.capture());
        }

        private void thenSensorHasBeenSaved2Times() {
            verify(sensorRepository, times(2)).save(sensorArgumentCaptor.capture());
        }

        private void thenSensorHasBeenLookedUpAtManufacturer() {
            verify(sensorManufacturerGateway, times(1)).findSensorById(sensorId);
        }

        private void thenSensorHasBeenLookedUp() {
            verify(sensorRepository, times(1)).findById(sensorId);
        }

        private void thenAnswerContainsErrorSensorNotKnownByManufacturer(UpdateSensorStatusUseCaseResult result) {
            assertEquals(ERROR, result.code());
            assertFalse(result.errors().isEmpty());
            assertEquals("Sensor not known by manufacturer", result.errors().get(0));
        }

        private void thenAnswerIs(UpdateSensorStatusUseCaseResult result, UpdateSensorStatusUseCaseResultCode expectedCode) {
            assertEquals(expectedCode, result.code());
            assertTrue(result.errors().isEmpty());
        }

        private void thenSensorEventsLookLikeThis(Class... domainEventsTypes) {
            var sensor = sensorArgumentCaptor.getValue();
            assertEquals(domainEventsTypes.length, sensor.events().size());
            assertArrayEquals(domainEventsTypes, sensor.events().stream().map(DomainEvent::getClass).toList().toArray());
        }
    }
}