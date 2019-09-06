package com.cleanup.todoc.injections;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.cleanup.todoc.repositories.Repository;
import com.cleanup.todoc.viewModels.TaskViewModel;

import java.util.concurrent.Executor;

/**
 * Created by Yann MANCEL on 06/09/2019.
 * Name of the project: todoc-master
 * Name of the package: com.cleanup.todoc.injections
 *
 * A class which implements {@link ViewModelProvider.Factory}.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    // FIELDS --------------------------------------------------------------------------------------

    private final Repository.ProjectRepository mProjectRepository;
    private final Repository.TaskRepository mTaskRepository;
    private final Executor mExecutor;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Constructor
     * @param projectRepository a {@link Repository.ProjectRepository}
     * @param taskRepository a {@link Repository.TaskRepository}
     * @param executor a {@link Executor}
     */
    public ViewModelFactory(Repository.ProjectRepository projectRepository,
                            Repository.TaskRepository taskRepository,
                            Executor executor) {
        this.mProjectRepository = projectRepository;
        this.mTaskRepository = taskRepository;
        this.mExecutor = executor;
    }

    // METHODS -------------------------------------------------------------------------------------

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(this.mProjectRepository,
                                         this.mTaskRepository,
                                         this.mExecutor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
