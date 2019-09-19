package com.cleanup.todoc.dagger.components;

import android.content.Context;

import com.cleanup.todoc.dagger.modules.DatabaseModule;
import com.cleanup.todoc.dagger.modules.ExecutorModule;
import com.cleanup.todoc.dagger.modules.ProjectRepositoryModule;
import com.cleanup.todoc.dagger.modules.TaskRepositoryModule;
import com.cleanup.todoc.viewModels.ViewModelFactory;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by Yann MANCEL on 19/09/2019.
 * Name of the project: todoc-master
 * Name of the package: com.cleanup.todoc.dagger.components
 */
@Singleton
@Component(modules = {DatabaseModule.class,
                      ProjectRepositoryModule.class,
                      TaskRepositoryModule.class,
                      ExecutorModule.class})
public interface ApplicationComponent {

    /*
        Information:    Singleton annotation is not necessary because "IN THIS ONLY CASE",
                        the Application instance, "TodocApplication", is unique.

        see: https://github.com/google/dagger/issues/832#issuecomment-320508239
     */

    // METHODS -------------------------------------------------------------------------------------

    /**
     * Gets the {@link ViewModelFactory}
     * @return the {@link ViewModelFactory}
     */
    ViewModelFactory getViewModelFactory();

    // INTERFACES ----------------------------------------------------------------------------------

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder context(final Context context);

        ApplicationComponent build();
    }
}
