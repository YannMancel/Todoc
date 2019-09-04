package com.cleanup.todoc.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Objects;

/**
 * <p>Models for project in which tasks are included.</p>
 *
 * @author Gaëtan HERFRAY
 */
@Entity(tableName = "project")
public class Project {

    // FIELDS --------------------------------------------------------------------------------------

    /**
     * The unique identifier of the project
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    /**
     * The name of the project
     */
    @NonNull
    @ColumnInfo(name = "name")
    private final String name;

    /**
     * The hex (ARGB) code of the color associated to the project
     */
    @ColorInt
    @ColumnInfo(name = "color")
    private final int color;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Instantiates a new Project.
     *
     * @param id    the unique identifier of the project to set
     * @param name  the name of the project to set
     * @param color the hex (ARGB) code of the color associated to the project to set
     */
    @Ignore
    public Project(long id, @NonNull String name, @ColorInt int color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    /**
     * Instantiates a new Project.
     * @param name  the name of the project to set
     * @param color the hex (ARGB) code of the color associated to the project to set
     */
    public Project(@NonNull String name, @ColorInt int color) {
        this.name = name;
        this.color = color;
    }


    // METHODS -------------------------------------------------------------------------------------

    /**
     * Returns all the projects of the application.
     *
     * @return all the projects of the application
     */
    @NonNull
    public static Project[] getAllProjects() {
        return new Project[]{
                new Project(1L, "Projet Tartampion", 0xFFEADAD1),
                new Project(2L, "Projet Lucidia", 0xFFB4CDBA),
                new Project(3L, "Projet Circus", 0xFFA3CED2),
        };
    }

    /**
     * Returns the project with the given unique identifier, or null if no project with that
     * identifier can be found.
     *
     * @param id the unique identifier of the project to return
     * @return the project with the given unique identifier, or null if it has not been found
     */
    @Nullable
    public static Project getProjectById(long id) {
        for (Project project : getAllProjects()) {
            if (project.id == id)
                return project;
        }
        return null;
    }

    // GETTER AND SETTER ***************************************************************************

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @ColorInt
    public int getColor() {
        return color;
    }

    // OBJECT **************************************************************************************

    @Override
    public boolean equals(@Nullable Object obj) {
        // Same address
        if (this == obj) return true;

        // Null or different class
        if (obj == null || getClass() != obj.getClass()) return false;

        // Cast Object to Project
        Project project = (Project) obj;

        return Objects.equals(this.id, project.id)     &&
               Objects.equals(this.name, project.name) &&
               Objects.equals(this.color, project.color);
    }

    @Override
    @NonNull
    public String toString() {
        return getName();
    }
}
