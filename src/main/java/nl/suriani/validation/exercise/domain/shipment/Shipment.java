package nl.suriani.validation.exercise.domain.shipment;

import nl.suriani.validation.exercise.domain.shared.DomainEvent;
import nl.suriani.validation.exercise.domain.shared.Guards;
import nl.suriani.validation.exercise.domain.shipment.events.ShipmentNotValidated;
import nl.suriani.validation.exercise.domain.shipment.events.ShipmentReceived;
import nl.suriani.validation.exercise.domain.shipment.events.ShipmentValidated;
import nl.suriani.validation.exercise.domain.shipment.events.ShipmentValidationFailed;

import java.util.ArrayList;
import java.util.List;

public record Shipment(ShipmentId id, ShipmentDescription description, List<DomainEvent> events) {

    public Shipment {
        Guards.isRequired(id);
        Guards.isNotEmpty(events);
        checkEvents(events);
    }

    public static Shipment received(ShipmentId id, ShipmentDescription description) {
        return new Shipment(id, description, List.of(new ShipmentReceived()));
    }

    public Shipment validated() {
        return new Shipment(id, description, addEvent(new ShipmentValidated()));
    }

    public Shipment notValidated(String reason) {
        return new Shipment(id, description, addEvent(new ShipmentNotValidated(reason)));
    }

    public Shipment validationFailed(String reason) {
        return new Shipment(id, description, addEvent(new ShipmentNotValidated(reason)));
    }

    private List<DomainEvent> addEvent(DomainEvent event) {
        var events = new ArrayList<>(this.events);
        events.add(event);
        return List.copyOf(events);
    }

    private void checkEvents(List<DomainEvent> events) {
        var legalCombinations = List.of(
                List.of(ShipmentReceived.class),
                List.of(ShipmentReceived.class, ShipmentValidated.class),
                List.of(ShipmentReceived.class, ShipmentNotValidated.class),
                List.of(ShipmentReceived.class, ShipmentValidationFailed.class)
        );

        var eventsNames = events.stream()
                .map(DomainEvent::getClass)
                .toList();

        if (!legalCombinations.contains(eventsNames)) {
            throw new IllegalStateException("Illegal sequence events: " + eventsNames);
        }
    }
}
