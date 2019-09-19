package com.cleanup.todoc.viewModels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.cleanup.todoc.repositories.Repository;

import java.util.concurrent.Executor;

import javax.inject.Inject;

/**
 * Created by Yann MANCEL on 06/09/2019.
 * Name of the project: todoc-master
 * Name of the package: com.cleanup.todoc.viewModels
 *
 * A class which implements {@link ViewModelProvider.Factory}.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    // FIELDS --------------------------------------------------------------------------------------

    @Inject
    Repository.ProjectRepository mProjectRepository;

    @Inject
    Repository.TaskRepository mTaskRepository;

    @Inject
    Executor mExecutor;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Constructor
     */
    @Inject
    public ViewModelFactory() {}

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
