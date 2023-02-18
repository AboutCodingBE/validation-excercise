package nl.suriani.validation.exercise.domain.shipment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentTest {

    @Test
    void shipmentIsReceived() {
        assertDoesNotThrow(() -> Shipment.received(id("0102030405"), description("test")));
    }

    @Test
    void shipmentIsReceivedThenValidated() {
        assertDoesNotThrow(() -> Shipment.received(id("0102030405"), description("test"))
                .validated());
    }

    @Test
    void shipmentIsReceivedThenNotValidated() {
        assertDoesNotThrow(() -> Shipment.received(id("0102030405"), description("test"))
                .notValidated("not available"));
    }

    @Test
    void shipmentIsReceivedThenValidationFailed() {
        assertDoesNotThrow(() -> Shipment.received(id("0102030405"), description("test"))
                .validationFailed("technical issue"));
    }

    @Test
    void shipmentIsReceivedThenValidationFailedThenIsValidated_mustFail() {
        assertThrows(IllegalStateException.class, () -> Shipment.received(id("0102030405"), description("test"))
                .validationFailed("technical issue")
                .validated());
    }

    @Test
    void shipmentIsReceivedThenIsValidatedThenIsValidated_mustFail() {
        assertThrows(IllegalStateException.class, () -> Shipment.received(id("0102030405"), description("test"))
                .validated()
                .validated());
    }

    @Test
    void shipmentIsReceivedThenIsNotValidatedThenIsValidated_mustFail() {
        assertThrows(IllegalStateException.class, () -> Shipment.received(id("0102030405"), description("test"))
                .notValidated("not available")
                .validated());
    }

    private ShipmentId id(String id) {
        return new ShipmentId(id);
    }

    private ShipmentDescription description(String description) {
        return new ShipmentDescription(description);
    }
}