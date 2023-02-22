package nl.suriani.validation.exercise.application.usecase.shipment;

import nl.suriani.validation.exercise.adapters.shipment.ShipmentRepositoryStub;
import nl.suriani.validation.exercise.domain.sensor.SensorId;
import nl.suriani.validation.exercise.domain.shared.DomainEvent;
import nl.suriani.validation.exercise.domain.shipment.Shipment;
import nl.suriani.validation.exercise.domain.shipment.ShipmentDescription;
import nl.suriani.validation.exercise.domain.shipment.ShipmentId;
import nl.suriani.validation.exercise.domain.shipment.events.ShipmentNotValidated;
import nl.suriani.validation.exercise.domain.shipment.events.ShipmentReceived;
import nl.suriani.validation.exercise.domain.shipment.events.ShipmentValidated;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static nl.suriani.validation.exercise.application.usecase.shipment.ValidateShipmentUseCaseResultCode.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidateShipmentUseCaseTest {

    @Spy
    private ShipmentRepositoryStub shipmentRepository;

    @Captor
    private ArgumentCaptor<Shipment> shipmentArgumentCaptor;

    @InjectMocks
    private  ValidateShipmentUseCase useCase;

    @Test
    void emptyShipment_shipmentIsNotSaved() {
        var sensorIds = List.<SensorId>of();
        var command = givenCommand(sensorIds);

        var result = useCase.apply(command);
        thenResult(result)
                .is(EMPTY_SHIPMENT);
    }

    @Test
    void duplicateIds_shipmentReceivedAndNotValidated() {
        var sensorIds = List.of(new SensorId("123456"), new SensorId(), new SensorId("123456"));
        var command = givenCommand(sensorIds);

        var result = useCase.apply(command);

        thenResult(result)
                .is(DUPLICATE_IDS);

        thenSavedShipment(getSavedShipment())
                .containsEvents(ShipmentReceived.class, ShipmentNotValidated.class);
    }

    @Test
    void shipmentReceivedAndValidated() {
        var sensorIds = List.of(new SensorId("123456"), new SensorId());
        var command = givenCommand(sensorIds);

        var result = useCase.apply(command);

        thenResult(result)
                .is(OK);

        thenSavedShipment(getSavedShipment())
                .containsEvents(ShipmentReceived.class, ShipmentValidated.class);
    }

    private Shipment getSavedShipment() {
        Mockito.verify(shipmentRepository).save(shipmentArgumentCaptor.capture());
        return shipmentArgumentCaptor.getValue();
    }

    private ValidateShipmentCommand givenCommand(List<SensorId> sensorIds) {
        return new ValidateShipmentCommand(new ShipmentId(),
                new ShipmentDescription("test"),
                sensorIds);
    }

    private ThenResult thenResult(ValidateShipmentUseCaseResult result) {
        return new ThenResult(result);
    }

    private record ThenResult(ValidateShipmentUseCaseResult result) {
        void is(ValidateShipmentUseCaseResultCode code) {
            assertEquals(code, result.code());
        }
    }

    private ThenShipment thenSavedShipment(Shipment shipment) {
        return new ThenShipment(shipment);
    }

    private record ThenShipment(Shipment shipment) {
        void containsEvents(Class... domainEvents) {
            assertEquals(domainEvents.length, shipment.events().size());
            var eventsTypes = shipment.events().stream()
                    .map(DomainEvent::getClass)
                    .toArray();

            assertArrayEquals(domainEvents, eventsTypes);
        }
    }
}