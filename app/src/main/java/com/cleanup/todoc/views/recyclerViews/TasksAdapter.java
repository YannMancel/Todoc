package com.cleanup.todoc.views.recyclerViews;

import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.pojos.Project;
import com.cleanup.todoc.model.pojos.Task;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yann MANCEL on 03/09/2019.
 * Name of the project: todoc-master
 * Name of the package: com.cleanup.todoc.views.recyclerViews
 *
 * A {@link RecyclerView.Adapter} subclass.
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    // INTERFACES ----------------------------------------------------------------------------------

    /**
     * Listener for deleting tasks
     */
    public interface DeleteTaskListener {
        /**
         * Called when a task needs to be deleted.
         * @param task the task that needs to be deleted
         */
        void onDeleteTask(final Task task);
    }

    // FIELDS --------------------------------------------------------------------------------------

    @NonNull
    private List<Task> mTasks;
    @NonNull
    private List<Project> mProjects;
    @NonNull
    private final DeleteTaskListener mDeleteTaskListener;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Instantiates a new TasksAdapter.
     * @param deleteTaskListener the listener for when a task needs to be deleted
     */
    public TasksAdapter(@NonNull final DeleteTaskListener deleteTaskListener) {
        this.mTasks = new ArrayList<>();
        this.mProjects = new ArrayList<>();
        this.mDeleteTaskListener = deleteTaskListener;
    }

    // METHODS -------------------------------------------------------------------------------------

    // -- ADAPTERS --

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        final View view = LayoutInflater.from(viewGroup.getContext())
                                        .inflate(R.layout.item_task, viewGroup, false);
        return new TaskViewHolder(view, this.mDeleteTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
        taskViewHolder.bind(this.mTasks.get(position), this.mProjects);
    }

    @Override
    public int getItemCount() {
        return this.mTasks.size();
    }

    // -- TASKS --

    /**
     * Returns the current {@link List<Task>}
     * @return the {@link List<Task>}
     */
    @NonNull
    public List<Task> getCurrentTasks() {
        return this.mTasks;
    }

    /**
     * Updates the {@link List<Task>} the adapter deals with.
     * @param newTasks a {@link List<Task>}
     */
    public void updateTasks(@NonNull final List<Task> newTasks) {
        this.mTasks = newTasks;
        notifyDataSetChanged();
    }

    // -- PROJECTS --

    /**
     * Returns the current {@link List<Project>}
     * @return the {@link List<Project>}
     */
    @NonNull
    public List<Project> getCurrentProjects() {
        return this.mProjects;
    }

    /**
     * Updates the {@link List<Project>} the adapter deals with.
     * @param newProjects a {@link List<Project>}
     */
    public void updateProjects(@NonNull final List<Project> newProjects) {
        this.mProjects = newProjects;
    }

    // INNER CLASS ---------------------------------------------------------------------------------

    /**
     * <p>ViewHolder for task items in the tasks list</p>
     */
    class TaskViewHolder extends RecyclerView.ViewHolder {

        // FIELDS ----------------------------------------------------------------------------------

        @NonNull
        private final AppCompatImageView mImgProject;
        @NonNull
        private final TextView mTaskName;
        @NonNull
        private final TextView mProjectName;
        @NonNull
        private final AppCompatImageView mImgDelete;

        @NonNull
        private final WeakReference<DeleteTaskListener> mDeleteTaskListener;

        // CONSTRUCTORS ----------------------------------------------------------------------------

        /**
         * Instantiates a new TaskViewHolder.
         * @param itemView the view of the task item
         * @param deleteTaskListener the listener for when a task needs to be deleted to set
         */
        TaskViewHolder(@NonNull View itemView, @NonNull DeleteTaskListener deleteTaskListener) {
            super(itemView);

            this.mDeleteTaskListener =  new WeakReference<>(deleteTaskListener);

            this.mImgProject = itemView.findViewById(R.id.img_project);
            this.mTaskName = itemView.findViewById(R.id.lbl_task_name);
            this.mProjectName = itemView.findViewById(R.id.lbl_project_name);
            this.mImgDelete = itemView.findViewById(R.id.img_delete);

            this.mImgDelete.setOnClickListener((view) -> {
                    final Object tag = view.getTag();
                    if (tag instanceof Task) {
                        final DeleteTaskListener callback = this.mDeleteTaskListener.get();

                        if (callback != null) {
                            callback.onDeleteTask((Task) tag);
                        }
                    }
                });
        }

        // METHODS ---------------------------------------------------------------------------------

        /**
         * Binds a task to the item view.
         * @param task the task to bind in the item view
         */
        void bind(@NonNull final Task task, @NonNull final List<Project> projects) {
            // NAME
            this.mTaskName.setText(task.getName());

            // PROJECT (First id = 1 and not 0)
            final long projectId = task.getProjectId();
            final Project taskProject = projects.get((int)projectId - 1);

            if (taskProject != null) {
                this.mImgProject.setSupportImageTintList(ColorStateList.valueOf(taskProject.getColor()));
                this.mProjectName.setText(taskProject.getName());
            }
            else {
                this.mImgProject.setVisibility(View.INVISIBLE);
                this.mProjectName.setText("");
            }

            // DELETE ACTION
            this.mImgDelete.setTag(task);
        }
    }
}
