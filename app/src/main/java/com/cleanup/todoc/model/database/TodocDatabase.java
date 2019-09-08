package com.cleanup.todoc.model.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todoc.model.dao.ProjectDao;
import com.cleanup.todoc.model.dao.TaskDao;
import com.cleanup.todoc.model.pojos.Project;
import com.cleanup.todoc.model.pojos.Task;

/**
 * Created by Yann MANCEL on 03/09/2019.
 * Name of the project: todoc-master
 * Name of the package: com.cleanup.todoc.model.database
 *
 * A abstract class that extends {@link RoomDatabase}.
 *
 * Design Pattern: Singleton
 */
@Database(entities = {Project.class,
                      Task.class},
          version = 1,
          exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    // FIELDS --------------------------------------------------------------------------------------

    private static volatile TodocDatabase INSTANCE;

    private static final String DATABASE_NAME = "TodocDatabase.db";

    // DAO------------------------------------------------------------------------------------------

    public abstract ProjectDao mProjectDao();
    public abstract TaskDao mTaskDao();

    // METHODS -------------------------------------------------------------------------------------

    // -- INSTANCE --

    /**
     * Returns the instance of {@link TodocDatabase}
     * @param context a {@link Context}
     * @return the instance of {@link TodocDatabase}
     */
    public static TodocDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                                    TodocDatabase.class,
                                                    DATABASE_NAME)
                                   .addCallback(prepopulateDatabase())
                                   .build();
                }
            }
        }

        return INSTANCE;
    }

    // -- PREPOPULATE THE DATABASE --

    /**
     * Returns a {@link android.arch.persistence.room.RoomDatabase.Callback} that
     * allows to prepopulate the database
     * @return a {@link android.arch.persistence.room.RoomDatabase.Callback}
     */
    private static Callback prepopulateDatabase() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();

                // PROJECTS 1
                contentValues.put("name", "Projet Tartampion");
                contentValues.put("color", 0xFFEADAD1);
                db.insert("project", OnConflictStrategy.IGNORE, contentValues);

                // PROJECTS 2
                contentValues.clear();
                contentValues.put("name", "Projet Lucidia");
                contentValues.put("color", 0xFFB4CDBA);
                db.insert("project", OnConflictStrategy.IGNORE, contentValues);

                // PROJECTS 3
                contentValues.clear();
                contentValues.put("name", "Projet Circus");
                contentValues.put("color", 0xFFA3CED2);
                db.insert("project", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }
}
