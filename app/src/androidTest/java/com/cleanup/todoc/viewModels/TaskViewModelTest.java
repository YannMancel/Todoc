package com.cleanup.todoc.viewModels;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.utils.LiveDataTestUtil;
import com.cleanup.todoc.model.database.TodocDatabase;
import com.cleanup.todoc.model.pojos.Project;
import com.cleanup.todoc.model.pojos.Task;
import com.cleanup.todoc.repositories.ProjectRepositoryImpl;
import com.cleanup.todoc.repositories.Repository;
import com.cleanup.todoc.repositories.TaskRepositoryImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

/**
 * Created by Yann MANCEL on 06/09/2019.
 * Name of the project: todoc-master
 * Name of the package: com.cleanup.todoc.viewModels
 *
 * Android test on {@link TaskViewModel}.
 */
@RunWith(AndroidJUnit4.class)
public class TaskViewModelTest {

    // FIELDS --------------------------------------------------------------------------------------

    private TodocDatabase mDatabase;
    private Repository.ProjectRepository mProjectRepository;
    private Repository.TaskRepository mTaskRepository;
    private TaskViewModel mViewModel;

    private final Project PROJECT = new Project("Project 1", 0xFFEADAD1);
    private final Task FIRST_TASK = new Task(1, "Task 1", 0);
    private final Task SECOND_TASK = new Task(1, "Task 2", 1);
    private final Task THIRD_TASK = new Task(1, "Task 2", 2);

    // RULES (Synchronized Tests) ------------------------------------------------------------------

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    // METHODS -------------------------------------------------------------------------------------

    @Before
    public void setUp() {
        // DATABASE
        this.mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                                                      TodocDatabase.class)
                             .allowMainThreadQueries()
                             .build();

        // REPOSITORIES
        this.mProjectRepository = new ProjectRepositoryImpl(this.mDatabase.mProjectDao());
        this.mTaskRepository = new TaskRepositoryImpl(this.mDatabase.mTaskDao());

        // DATA
        this.mProjectRepository.insertProject(PROJECT);
        this.mTaskRepository.insertTask(FIRST_TASK);
        this.mTaskRepository.insertTask(SECOND_TASK);

        // VIEW MODEL
        this.mViewModel = new TaskViewModel(this.mProjectRepository,
                                            this.mTaskRepository,
                                            Executors.newSingleThreadExecutor());

        this.mViewModel.init();
    }

    @After
    public void tearDown() {
        this.mDatabase.close();
    }

    // -- PROJECTS --

    @Test
    public void getProjects_shouldBeSuccess() throws InterruptedException {
        final List<Project> projects = LiveDataTestUtil.getValue(this.mViewModel.getProjects());

        // TESTS
        assertEquals(1, projects.size());
    }

    // -- TASKS --

    @Test
    public void getTasks_shouldBeSuccess() throws InterruptedException {
        final List<Task> tasks = LiveDataTestUtil.getValue(this.mViewModel.getTasks());

        // TESTS
        assertEquals(2, tasks.size());
    }

    @Test
    public void insertTask_Then_getTasks_shouldBeSuccess() throws InterruptedException {
        // BEFORE: Add a task
        this.mViewModel.insertTask(THIRD_TASK);

        // THEN: Retrieves the tasks
        final List<Task> tasks = LiveDataTestUtil.getValue(this.mViewModel.getTasks());

        // TESTS
        assertEquals(3, tasks.size());
    }

    @Test
    public void deleteTaskById_Then_getTasks_shouldBeSuccess() throws InterruptedException {
        // BEFORE: delete a task
        this.mViewModel.deleteTaskById(2);

        // THEN: Retrieves the tasks
        final List<Task> tasks = LiveDataTestUtil.getValue(this.mViewModel.getTasks());

        // TESTS
        assertEquals(1, tasks.size());
    }
}
