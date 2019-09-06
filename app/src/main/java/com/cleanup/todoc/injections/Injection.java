package com.cleanup.todoc.injections;

import android.content.Context;

import com.cleanup.todoc.model.database.TodocDatabase;
import com.cleanup.todoc.repositories.ProjectRepositoryImpl;
import com.cleanup.todoc.repositories.Repository;
import com.cleanup.todoc.repositories.TaskRepositoryImpl;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Yann MANCEL on 06/09/2019.
 * Name of the project: todoc-master
 * Name of the package: com.cleanup.todoc.injections
 */
public abstract class Injection {

    // METHODS -------------------------------------------------------------------------------------

    // -- PROJECT REPOSITORY --

    /**
     * Provides a {@link Repository.ProjectRepository}
     * @param context a {@link Context}
     * @return a {@link Repository.ProjectRepository}
     */
    public static Repository.ProjectRepository provideProjectRepository(final Context context) {
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new ProjectRepositoryImpl(database.mProjectDao());
    }

    // -- TASK REPOSITORY --

    /**
     * Provides a {@link Repository.TaskRepository}
     * @param context a {@link Context}
     * @return a {@link Repository.TaskRepository}
     */
    public static Repository.TaskRepository provideTaskRepository(final Context context) {
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new TaskRepositoryImpl(database.mTaskDao());
    }

    // -- EXECUTOR --

    /**
     * Provides a {@link Executor}
     * @return a {@link Executor}
     */
    public static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    // -- VIEW MODEL FACTORY --

    /**
     * Provides a {@link ViewModelFactory}
     * @param context a {@link Context}
     * @return a {@link ViewModelFactory}
     */
    public static ViewModelFactory provideViewModelFactory(final Context context) {
        return new ViewModelFactory(provideProjectRepository(context),
                                    provideTaskRepository(context),
                                    provideExecutor());
    }
}
