package com.cleanup.todoc.dagger.modules;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Yann MANCEL on 19/09/2019.
 * Name of the project: todoc-master
 * Name of the package: com.cleanup.todoc.dagger.modules
 */
@Module
public abstract class ExecutorModule {

    /*
        Information:    Singleton annotation is not necessary because "IN THIS ONLY CASE",
                        the Application instance, "TodocApplication", is unique.

        see: https://github.com/google/dagger/issues/832#issuecomment-320508239
     */

    // METHODS -------------------------------------------------------------------------------------

    /**
     * Returns an {@link Executor}
     * @return an {@link Executor}
     */
    @Singleton
    @Provides
    static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }
}
