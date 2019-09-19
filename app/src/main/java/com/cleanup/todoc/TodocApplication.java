package com.cleanup.todoc;

import android.app.Application;
import android.support.annotation.NonNull;

import com.cleanup.todoc.dagger.components.ApplicationComponent;
import com.cleanup.todoc.dagger.components.DaggerApplicationComponent;

/**
 * Created by Yann MANCEL on 19/09/2019.
 * Name of the project: todoc-master
 * Name of the package: com.cleanup.todoc
 *
 * A {@link Application} subclass.
 */
public class TodocApplication extends Application {

    // FIELDS --------------------------------------------------------------------------------------

    @SuppressWarnings("NullableProblems")
    @NonNull
    private ApplicationComponent mComponent;

    // METHODS -------------------------------------------------------------------------------------

    // -- APPLICATION --

    @Override
    public void onCreate() {
        super.onCreate();

        // Dagger2
        this.mComponent = DaggerApplicationComponent.builder()
                                                    .context(getApplicationContext())
                                                    .build();
    }

    // -- COMPONENT --

    /**
     * Returns the {@link ApplicationComponent}
     * @return the {@link ApplicationComponent}
     */
    public ApplicationComponent getApplicationComponent() {
        return this.mComponent;
    }
}
