package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.cleanup.todoc.model.dao.TaskDao;
import com.cleanup.todoc.model.pojos.Task;

import java.util.List;

/**
 * Created by Yann MANCEL on 05/09/2019.
 * Name of the project: todoc-master
 * Name of the package: com.cleanup.todoc.repositories
 *
 * A class which implements {@link Repository.TaskRepository}.
 */
public class TaskRepositoryImpl implements Repository.TaskRepository {

    // FIELDS --------------------------------------------------------------------------------------

    @NonNull
    private final TaskDao mTaskDao;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Constructor
     * @param taskDao a {@link TaskDao}
     */
    public TaskRepositoryImpl(@NonNull TaskDao taskDao) {
        this.mTaskDao = taskDao;
    }

    // METHODS -------------------------------------------------------------------------------------

    // -- CREATE --

    @Override
    public long insertTask(@NonNull final Task task) {
        return this.mTaskDao.insertTask(task);
    }

    // -- READ --

    @NonNull
    @Override
    public LiveData<List<Task>> getTasks() {
        return this.mTaskDao.getTasks();
    }

    // -- DELETE --

    @Override
    public int deleteTaskById(final long taskId) {
        return this.mTaskDao.deleteTaskById(taskId);
    }
}
