package nl.suriani.validation.exercise.application.port.task;

import nl.suriani.validation.exercise.domain.task.Task;

public interface TaskRepository {
    void save(Task task);
}
