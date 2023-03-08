package nl.suriani.validation.exercise.domain.sensor;

import nl.suriani.validation.exercise.domain.sensor.events.*;
import nl.suriani.validation.exercise.domain.shared.DomainEvent;
import nl.suriani.validation.exercise.domain.shared.Guards;

import java.util.ArrayList;
import java.util.List;

public record Sensor(SensorId id,
                     Firmware firmware,
                     Configuration configuration,
                     SensorTasksStatus tasksStatus,
                     SensorActivityStatus activityStatus,
                     List<DomainEvent> events) {

    /* Issue: the task status actually doesn't belong here. This is clearly an error in the model.
         A task can be scheduled now and in this specific implementation, but variations where the task gets scheduled
         automatically by an external system are possible.

         Idea: an option would be keeping the SensorTasksStatus outside this aggregate,
         made only available when querying the Manufacturer API and returning the result to the users.
         However, this option isn't pragmatic as it would cause unnecessary querying to be performed.
         I can keep it for the moment ("there is nothing more permanent than a temporary solution" - someone wise).
    */

    public Sensor {
        Guards.isRequired(id);
        Guards.isRequired(firmware);
        Guards.isRequired(configuration);
        Guards.isRequired(tasksStatus);
        Guards.isRequired(activityStatus);
        Guards.isNotEmpty(events);
        checkEvents(events);
    }

    public static Sensor registered(SensorId id, Firmware firmware, Configuration configuration,
                                    SensorTasksStatus tasksStatus, SensorActivityStatus activityStatus) {

        return new Sensor(id, firmware, configuration, tasksStatus, activityStatus, List.of(new SensorRegistered()));
    }

    public Sensor refresh(SensorTasksStatus tasksStatus, SensorActivityStatus activityStatus) {
        return new Sensor(id,
                firmware,
                configuration,
                tasksStatus,
                activityStatus,
                events
        );
    }

    public Sensor firmwareUpdateRequired() {
        return new Sensor(id,
                firmware,
                configuration,
                tasksStatus,
                activityStatus,
                addEvent(new FirmwareUpdateRequired())
        );
    }

    public Sensor firmwareUpdateScheduled(Firmware newFirmware) {
        return new Sensor(id,
                firmware,
                configuration,
                tasksStatus,
                activityStatus,
                addEvent(new FirmwareUpdateScheduled(versionOf(firmware), versionOf(newFirmware)))
        );
    }

    public Sensor firmwareUpdateNotScheduled(String reason) {
        return new Sensor(id,
                firmware,
                configuration,
                tasksStatus,
                activityStatus,
                addEvent(new FirmwareUpdateNotScheduled(versionOf(firmware), reason))
        );
    }

    public Sensor firmwareUpdated(Firmware newFirmware) {
        return new Sensor(id,
                newFirmware,
                configuration,
                tasksStatus,
                activityStatus,
                addEvent(new FirmwareUpdated(versionOf(firmware), versionOf(newFirmware)))
        );
    }

    public Sensor firmwareUpdateCancelled(Firmware newFirmware) {
        return new Sensor(id,
                firmware,
                configuration,
                tasksStatus,
                activityStatus,
                addEvent(new FirmwareUpdateCancelled(versionOf(firmware), versionOf(newFirmware)))
        );
    }

    public Sensor firmwareUpdateFailed(Firmware newFirmware, String reason) {
        return new Sensor(id,
                firmware,
                configuration,
                tasksStatus,
                activityStatus,
                addEvent(new FirmwareUpdateFailed(versionOf(firmware), versionOf(newFirmware), reason))
        );
    }

    public Sensor configurationUpdateRequired() {
        return new Sensor(id,
                firmware,
                configuration,
                tasksStatus,
                activityStatus,
                addEvent(new ConfigurationUpdateRequired())
        );
    }

    public Sensor configurationUpdateScheduled(Firmware newFirmware) {
        return new Sensor(id,
                firmware,
                configuration,
                tasksStatus,
                activityStatus,
                addEvent(new ConfigurationUpdateScheduled(versionOf(firmware), versionOf(newFirmware)))
        );
    }

    public Sensor configurationUpdateNotScheduled(String reason) {
        return new Sensor(id,
                firmware,
                configuration,
                tasksStatus,
                activityStatus,
                addEvent(new ConfigurationUpdateNotScheduled(versionOf(firmware), reason))
        );
    }

    public Sensor configurationUpdated(Configuration newConfiguration) {
        return new Sensor(id,
                firmware,
                newConfiguration,
                tasksStatus,
                activityStatus,
                addEvent(new ConfigurationUpdated(versionOf(configuration), versionOf(newConfiguration)))
        );
    }

    public Sensor configurationUpdateCancelled(Configuration newConfiguration) {
        return new Sensor(id,
                firmware,
                configuration,
                tasksStatus,
                activityStatus,
                addEvent(new ConfigurationUpdateCancelled(versionOf(configuration), versionOf(newConfiguration)))
        );
    }

    public Sensor configurationUpdateFailed(Configuration newConfiguration, String reason) {
        return new Sensor(id,
                firmware,
                configuration,
                tasksStatus,
                activityStatus,
                addEvent(new ConfigurationUpdateFailed(versionOf(configuration), versionOf(newConfiguration), reason))
        );
    }

    private void checkEvents(List<DomainEvent> events) {
        checkFirstEvent(events);

        var legalEvents = List.of(SensorRegistered.class,
                        FirmwareUpdated.class,
                        FirmwareUpdated.class,
                        FirmwareUpdateFailed.class,
                        ConfigurationUpdated.class,
                        ConfigurationUpdateCancelled.class,
                        ConfigurationUpdateFailed.class);

        legalEvents.stream()
                .filter(event -> !legalEvents.contains(event))
                .findFirst()
                .ifPresent(ignore -> failBecauseOfIllegalSequence(events));
    }

    private void checkFirstEvent(List<DomainEvent> events) {
        var firstEventType = events.get(0).getClass();
        if (!firstEventType.equals(SensorRegistered.class)) {
            failBecauseOfIllegalSequence(events);
        }
    }

    private void failBecauseOfIllegalSequence(List<DomainEvent> events) {
        throw new IllegalStateException("Illegal sequence events " + events);
    }

    private List<DomainEvent> addEvent(DomainEvent event) {
        var events = new ArrayList<>(this.events);
        events.add(event);
        return List.copyOf(events);
    }
    
    private Version versionOf(Firmware firmware) {
        return new Version(firmware.version().toString());
    }

    private Version versionOf(Configuration configuration) {
        return new Version(configuration.name().toString());
    }
}
