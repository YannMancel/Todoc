package com.cleanup.todoc.model.pojos;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import java.util.Comparator;
import java.util.Objects;

/**
 * Created by Yann MANCEL on 03/09/2019.
 * Name of the project: todoc-master
 * Name of the package: com.cleanup.todoc.model.pojos
 */
@Entity(tableName = "task",
        foreignKeys = @ForeignKey(entity = Project.class,
                                  parentColumns = "id",
                                  childColumns = "project_id"))
public class Task {

    // FIELDS --------------------------------------------------------------------------------------

    /**
     * The unique identifier of the task
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    /**
     * The unique identifier of the project associated to the task
     */
    @ColumnInfo(name = "project_id")
    private long projectId;

    /**
     * The name of the task
     */
    // Suppress warning because setName is called in constructor
    @SuppressWarnings("NullableProblems")
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    /**
     * The timestamp when the task has been created
     */
    @ColumnInfo(name = "creation_timestamp")
    private long creationTimestamp;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Instantiates a new Task.
     * @param id                the unique identifier of the task to set
     * @param projectId         the unique identifier of the project associated to the task to set
     * @param name              the name of the task to set
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    @Ignore
    @VisibleForTesting
    public Task(long id, long projectId, @NonNull String name, long creationTimestamp) {
        this.setId(id);
        this.setProjectId(projectId);
        this.setName(name);
        this.setCreationTimestamp(creationTimestamp);
    }

    /**
     * Instantiates a new Task.
     * @param projectId         the unique identifier of the project associated to the task to set
     * @param name              the name of the task to set
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    public Task(long projectId, @NonNull String name, long creationTimestamp) {
        this.projectId = projectId;
        this.name = name;
        this.creationTimestamp = creationTimestamp;
    }

    // METHODS -------------------------------------------------------------------------------------

    // -- GETTER AND SETTER --

    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return this.projectId;
    }
    private void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    @NonNull
    public String getName() {
        return this.name;
    }
    private void setName(@NonNull String name) {
        this.name = name;
    }

    public long getCreationTimestamp() {
        return this.creationTimestamp;
    }
    private void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    // -- FROM OBJECT CLASS --

    @Override
    public boolean equals(@Nullable Object obj) {
        // Same address
        if (this == obj) return true;

        // Null or different class
        if (obj == null || getClass() != obj.getClass()) return false;

        // Cast Object to Task
        Task task = (Task) obj;

        return Objects.equals(this.id, task.id)               &&
               Objects.equals(this.projectId, task.projectId) &&
               Objects.equals(this.name, task.name)           &&
               Objects.equals(this.creationTimestamp, task.creationTimestamp);
    }

    // COMPARATORS ---------------------------------------------------------------------------------

    /**
     * Comparator to sort task from A to Z
     */
    public static class TaskAZComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return left.name.compareToIgnoreCase(right.name);
        }
    }

    /**
     * Comparator to sort task from Z to A
     */
    public static class TaskZAComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return right.name.compareToIgnoreCase(left.name);
        }
    }

    /**
     * Comparator to sort task from last created to first created
     */
    public static class TaskRecentComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (right.creationTimestamp - left.creationTimestamp);
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    public static class TaskOldComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (left.creationTimestamp - right.creationTimestamp);
        }
    }
}
