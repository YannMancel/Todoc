package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc.model.Project;

import java.util.List;

/**
 * Created by Yann MANCEL on 03/09/2019.
 * Name of the project: todoc-master
 * Name of the package: com.cleanup.todoc.model.dao
 *
 * Pattern Data Access Object
 */
@Dao
public interface ProjectDao {

    // CREATE **************************************************************************************

    @Insert
    long insertProject(Project project);

    // READ ****************************************************************************************

    @Query("SELECT * FROM project")
    LiveData<List<Project>> getProjects();

    @Query("SELECT * FROM project WHERE id = :projectId")
    LiveData<Project> getProject(final long projectId);

    // UPDATE **************************************************************************************

    @Update
    int updateProject(Project project);

    // DELETE **************************************************************************************

    @Query("DELETE FROM project WHERE id = :projectId")
    int deleteProject(final long projectId);
}
