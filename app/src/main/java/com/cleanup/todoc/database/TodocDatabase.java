package com.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

/**
 * Created by Yann MANCEL on 03/09/2019.
 * Name of the project: todoc-master
 * Name of the package: com.cleanup.todoc.database
 *
 * A abstract class that extends {@link RoomDatabase}
 *
 * Pattern Singleton
 */
@Database(entities = {Project.class},
          version = 1,
          exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    // FIELDS --------------------------------------------------------------------------------------

    private static volatile TodocDatabase INSTANCE;
    public static final String DATABASE_NAME = "TodocDatabase.db";

    public abstract ProjectDao mProjectDao();

    // METHODS -------------------------------------------------------------------------------------

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

    // PREPOPULATE THE DATABASE ********************************************************************

    /**
     * Returns a {@link android.arch.persistence.room.RoomDatabase.Callback} that
     * alows to prepopulate the database
     * @return a {@link android.arch.persistence.room.RoomDatabase.Callback}
     */
    private static Callback prepopulateDatabase() {
        return new Callback() {
                                  @Override
                                  public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                    ContentValues contentValues = new ContentValues();
//                                    contentValues.put("id", 1);
                                    contentValues.put("name", "Projet Tartampion");
                                    contentValues.put("color", 0xFFEADAD1);

                                    db.insert("Project", OnConflictStrategy.IGNORE, contentValues);



//                                      new Project(1L, "Projet Tartampion", 0xFFEADAD1),
//                                              new Project(2L, "Projet Lucidia", 0xFFB4CDBA),
//                                              new Project(3L, "Projet Circus", 0xFFA3CED2),

                                  }
                              };
    }

}
