package com.cleanup.todoc.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import com.cleanup.todoc.model.pojos.Project;

import java.util.List;

/**
 * Created by Yann MANCEL on 03/09/2019.
 * Name of the project: todoc-master
 * Name of the package: com.cleanup.todoc.model.dao
 *
 * Design Pattern: Data Access Object
 */
@Dao
public interface ProjectDao {

    // METHODS -------------------------------------------------------------------------------------

    // -- CREATE --

    @Insert
    long insertProject(@NonNull final Project project);

    // -- READ --

    @Query("SELECT * FROM project")
    LiveData<List<Project>> getProjects();

    @Query("SELECT * FROM project WHERE id = :projectId")
    LiveData<Project> getProjectById(final long projectId);

    // -- UPDATE --

    @Update
    int updateProject(@NonNull final Project project);

    // -- DELETE --

    @Query("DELETE FROM project WHERE id = :projectId")
    int deleteProjectById(final long projectId);
}
