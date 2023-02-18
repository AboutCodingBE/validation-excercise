package nl.suriani.validation.exercise.domain.shared;

import java.time.LocalDateTime;

public abstract class DomainEvent {
    private final LocalDateTime creationDateTime;
    private final String name;

    public DomainEvent(LocalDateTime creationDateTime, String name) {
        Guards.isRequired(creationDateTime);
        Guards.isRequired(name);

        this.creationDateTime = creationDateTime;
        this.name = name;
    }

    public DomainEvent(LocalDateTime creationDateTime, Class clazz) {
        this(creationDateTime, clazz.getSimpleName());
    }

    public LocalDateTime creationDateTime() {
        return creationDateTime;
    }

    public String name() {
        return name;
    }
}
