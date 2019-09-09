package com.cleanup.todoc.views.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.injections.Injection;
import com.cleanup.todoc.injections.ViewModelFactory;
import com.cleanup.todoc.model.pojos.Project;
import com.cleanup.todoc.model.pojos.Task;
import com.cleanup.todoc.viewModels.TaskViewModel;
import com.cleanup.todoc.views.recyclerViews.TasksAdapter;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Yann MANCEL on 03/09/2019.
 * Name of the project: todoc-master
 * Name of the package: com.cleanup.todoc.views.activities
 *
 * A {@link AppCompatActivity} subclass which implements {@link TasksAdapter.DeleteTaskListener}.
 */
public class MainActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener {

    // FIELDS --------------------------------------------------------------------------------------

    // -- XML --

    @SuppressWarnings("NullableProblems")
    @NonNull
    private RecyclerView mRecyclerView;
    @SuppressWarnings("NullableProblems")
    @NonNull
    private TextView mNoDataTextView;

    // -- DIALOG --

    @Nullable
    public AlertDialog mDialog = null;
    @Nullable
    private EditText mDialogEditText = null;
    @Nullable
    private Spinner mDialogSpinner = null;

    // -- SORT --

    private enum SortMethod {ALPHABETICAL,
                             ALPHABETICAL_INVERTED,
                             RECENT_FIRST,
                             OLD_FIRST,
                             NONE}
    @NonNull
    private SortMethod mSortMethod = SortMethod.NONE;

    // -- RECYCLER VIEW --

    @SuppressWarnings("NullableProblems")
    @NonNull
    private TaskViewModel mViewModel;
    @SuppressWarnings("NullableProblems")
    @NonNull
    private TasksAdapter mAdapter;

    // METHODS -------------------------------------------------------------------------------------

    // -- ACTIVITY --

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureDesign();
        this.configureViewModel();
        this.configureObserverOfProjects();
        this.configureObserverOfTasks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_alphabetical:
                this.mSortMethod = SortMethod.ALPHABETICAL;
                break;
            case R.id.filter_alphabetical_inverted:
                this.mSortMethod = SortMethod.ALPHABETICAL_INVERTED;
                break;
            case R.id.filter_recent_first:
                this.mSortMethod = SortMethod.RECENT_FIRST;
                break;
            case R.id.filter_oldest_first:
                this.mSortMethod = SortMethod.OLD_FIRST;
                break;
        }

        // SORT
        final List<Task> newTasks = this.sortTasks(this.mAdapter.getCurrentTasks());
        this.mAdapter.updateTasks(newTasks);

        return super.onOptionsItemSelected(item);
    }

    // -- DELETE LISTENER INTERFACE OF TASKS ADAPTER --

    @Override
    public void onDeleteTask(final Task task) {
        this.deleteTask(task);
    }

    // -- UI --

    /**
     * Configures the UI
     */
    private void configureDesign() {
        // ADAPTER
        this.mAdapter = new TasksAdapter(this);

        // RECYCLER VIEW
        this.mRecyclerView = findViewById(R.id.list_tasks);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // TEXT VIEW
        this.mNoDataTextView = findViewById(R.id.lbl_no_task);

        // FLOATING ACTION BUTTON
        findViewById(R.id.fab_add_task).setOnClickListener((view) -> this.showAddTaskDialog());
    }

    // -- VIEW MODEL --

    /**
     * Configures the {@link android.arch.lifecycle.ViewModel}
     */
    private void configureViewModel() {
        // VIEW MODEL FACTORY
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);

        // VIEW MODEL
        this.mViewModel = ViewModelProviders.of(this, viewModelFactory)
                                            .get(TaskViewModel.class);

        this.mViewModel.init();
    }

    /**
     * Configures the observer of change of {@link Task}
     */
    private void configureObserverOfTasks() {
        this.mViewModel.getTasks().observe(this, this::updateTasks);
    }

    /**
     * Configures the observer of change of {@link Project}
     */
    private void configureObserverOfProjects() {
        this.mViewModel.getProjects().observe(this, this::updateProjects);
    }

    // -- TASKS --

    /**
     * Inserts a {@link Task}
     * @param task a {@link Task}
     */
    private void insertTask(@NonNull final Task task) {
        this.mViewModel.insertTask(task);
    }

    /**
     * Updates the {@link List<Task>}
     * @param newTasks a {@link List<Task>}
     */
    private void updateTasks(@NonNull final List<Task> newTasks) {
        // TEXT VIEW
        this.mNoDataTextView.setVisibility((newTasks.size() == 0) ? View.VISIBLE : View.GONE);

        // RECYCLER VIEW
        this.mRecyclerView.setVisibility((newTasks.size() == 0) ? View.GONE : View.VISIBLE);

        // SORT AND DISPLAY
        this.mAdapter.updateTasks(this.sortTasks(newTasks));
    }

    /**
     * Deletes a {@link Task}
     * @param task a {@link Task}
     */
    private void deleteTask(@NonNull final Task task) {
        this.mViewModel.deleteTaskById(task.getId());
    }

    // -- PROJECTS --

    /**
     * Updates the {@link List<Project>}
     * @param newProjects a {@link List<Project>}
     */
    private void updateProjects(@NonNull final List<Project> newProjects) {
        this.mAdapter.updateProjects(newProjects);
    }

    // -- ACTIONS --

    /**
     * Shows the Dialog for adding a Task
     */
    private void showAddTaskDialog() {
        final AlertDialog dialog = this.getAddTaskDialog();
        dialog.show();

        // DIALOG
        this.mDialogEditText = dialog.findViewById(R.id.txt_task_name);
        this.mDialogSpinner = dialog.findViewById(R.id.project_spinner);

        this.populateDialogSpinner();
    }

    // -- DIALOG --

    /**
     * Returns the dialog allowing the user to create a new task.
     * @return the dialog allowing the user to create a new task
     */
    @NonNull
    private AlertDialog getAddTaskDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

        alertBuilder.setTitle(R.string.add_task);
        alertBuilder.setView(R.layout.dialog_add_task);
        alertBuilder.setPositiveButton(R.string.add, null);
        alertBuilder.setOnDismissListener((dialogInterface) -> {this.mDialogEditText = null;
                                                                this.mDialogSpinner = null;
                                                                this.mDialog = null; });

        this.mDialog = alertBuilder.create();

        // This instead of listener to positive button in order to avoid automatic dismiss
        this.mDialog.setOnShowListener((dialogInterface) -> {
                final Button button = this.mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener((view) -> this.onPositiveButtonClick(this.mDialog));
            });

        return this.mDialog;
    }

    /**
     * Called when the user clicks on the positive button of the Create Task Dialog.
     * @param dialogInterface the current displayed dialog
     */
    private void onPositiveButtonClick(DialogInterface dialogInterface) {
        // If dialog is open
        if (this.mDialogEditText != null && this.mDialogSpinner != null) {
            // Get the name of the task
            String taskName = this.mDialogEditText.getText().toString();

            // Get the selected project to be associated to the task
            Project taskProject = null;
            if (this.mDialogSpinner.getSelectedItem() instanceof Project) {
                taskProject = (Project) this.mDialogSpinner.getSelectedItem();
            }

            // If a name has not been set
            if (taskName.trim().isEmpty()) {
                this.mDialogEditText.setError(getString(R.string.empty_task_name));
            }
            // If both project and name of the task have been set
            else if (taskProject != null) {
                Task task = new Task(taskProject.getId(),
                                     taskName,
                                     new Date().getTime());

                this.insertTask(task);

                dialogInterface.dismiss();
            }
            // If name has been set, but project has not been set (this should never occur)
            else{
                dialogInterface.dismiss();
            }
        }
        // If dialog is already closed
        else {
            dialogInterface.dismiss();
        }
    }

    // -- SPINNER --

    /**
     * Sets the data of the Spinner with projects to associate to a new task
     */
    private void populateDialogSpinner() {
        final List<Project> currentProjects = this.mAdapter.getCurrentProjects();

        final ArrayAdapter<Project> adapter = new ArrayAdapter<>(this,
                                                                  android.R.layout.simple_spinner_item,
                                                                  currentProjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (this.mDialogSpinner != null) {
            this.mDialogSpinner.setAdapter(adapter);
        }
    }

    // -- SORT --

    /**
     * Sorts the {@link List<Task>} in argument and returns it
     * @param tasks a {@link List<Task>}
     * @return the {@link List<Task>} but sorted
     */
    @NonNull
    private List<Task> sortTasks(@NonNull final List<Task> tasks) {
        if (tasks.size()!= 0) {
            switch (this.mSortMethod) {
                case ALPHABETICAL:
                    Collections.sort(tasks, new Task.TaskAZComparator());
                    break;
                case ALPHABETICAL_INVERTED:
                    Collections.sort(tasks, new Task.TaskZAComparator());
                    break;
                case RECENT_FIRST:
                    Collections.sort(tasks, new Task.TaskRecentComparator());
                    break;
                case OLD_FIRST:
                    Collections.sort(tasks, new Task.TaskOldComparator());
                    break;
            }
        }
        return tasks;
    }
}
