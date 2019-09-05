package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.model.pojos.Project;
import com.cleanup.todoc.model.pojos.Task;

import java.util.List;

/**
 * Created by Yann MANCEL on 05/09/2019.
 * Name of the project: todoc-master
 * Name of the package: com.cleanup.todoc.repositories
 */
public interface Repository {

    // INTERFACES ----------------------------------------------------------------------------------

    // -- PROJECT REPOSITORY --

    interface ProjectRepository {

        // METHODS ---------------------------------------------------------------------------------

        // -- CREATE --

        /**
         * Inserts a {@link Project} into database and returns the id value
         * @param project a {@link Project}
         * @return an integer that contains the id value
         */
        long insertProject(final Project project);

        // -- READ --

        /**
         * Returns a {@link LiveData} of {@link List} of {@link Project}
         * @return a {@link LiveData} of {@link List} of {@link Project}
         */
        LiveData<List<Project>> getProjects();

        /**
         * Returns a {@link LiveData} of {@link Project} which has the same id that the argument
         * @param projectId an integer that contains the id value
         * @return a {@link LiveData} of {@link Project}
         */
        LiveData<Project> getProjectById(final long projectId);
    }

    // -- TASK REPOSITORY --

    interface TaskRepository {

        // METHODS ---------------------------------------------------------------------------------

        // -- CREATE --

        /**
         * Inserts a {@link Task} into database and returns the id value
         * @param task a {@link Task}
         * @return an integer that contains the id value
         */
        long insertTask(final Task task);

        // -- READ --

        /**
         * Returns a {@link LiveData} of {@link List} of {@link Task}
         * @return a {@link LiveData} of {@link List} of {@link Task}
         */
        LiveData<List<Task>> getTasks();

        /**
         * Deletes a {@link Task} into database and returns the deleted tuple number
         * @param taskId an integer that contains the id value
         * @return an integer that contains the deleted tuple number
         */
        int deleteTaskById(final long taskId);
    }
}
