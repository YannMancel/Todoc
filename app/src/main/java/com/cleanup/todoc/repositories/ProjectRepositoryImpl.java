package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.cleanup.todoc.model.dao.ProjectDao;
import com.cleanup.todoc.model.pojos.Project;

import java.util.List;

/**
 * Created by Yann MANCEL on 05/09/2019.
 * Name of the project: todoc-master
 * Name of the package: com.cleanup.todoc.repositories
 *
 * A class which implements {@link Repository.ProjectRepository}.
 */
public class ProjectRepositoryImpl implements Repository.ProjectRepository {

    // FIELDS --------------------------------------------------------------------------------------

    @NonNull
    private final ProjectDao mProjectDao;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Constructor
     * @param projectDao a {@link ProjectDao}
     */
    public ProjectRepositoryImpl(@NonNull ProjectDao projectDao) {
        this.mProjectDao = projectDao;
    }

    // METHODS -------------------------------------------------------------------------------------

    // -- CREATE --

    @Override
    public long insertProject(@NonNull final Project project) {
        return this.mProjectDao.insertProject(project);
    }

    // -- READ --

    @NonNull
    @Override
    public LiveData<List<Project>> getProjects() {
        return this.mProjectDao.getProjects();
    }

    @NonNull
    @Override
    public LiveData<Project> getProjectById(final long projectId) {
        return this.mProjectDao.getProjectById(projectId);
    }
}
