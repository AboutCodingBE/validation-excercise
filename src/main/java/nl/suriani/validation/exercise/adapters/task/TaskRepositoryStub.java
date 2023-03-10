package nl.suriani.validation.exercise.adapters.task;

import nl.suriani.validation.exercise.application.port.task.TaskRepository;
import nl.suriani.validation.exercise.domain.task.Task;
import nl.suriani.validation.exercise.domain.task.TaskId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskRepositoryStub implements TaskRepository {

    private final Map<TaskId, Task> db = new ConcurrentHashMap<>();

    @Override
    public void save(Task task) {
        db.put(task.id(), task);
    }
}
