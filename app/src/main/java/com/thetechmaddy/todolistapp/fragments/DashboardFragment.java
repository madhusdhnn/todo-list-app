package com.thetechmaddy.todolistapp.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thetechmaddy.todolistapp.NewTodoActivity;
import com.thetechmaddy.todolistapp.R;
import com.thetechmaddy.todolistapp.adapters.TodoListAdapter;
import com.thetechmaddy.todolistapp.callbacks.SwipeToDeleteCallback;
import com.thetechmaddy.todolistapp.models.Todo;
import com.thetechmaddy.todolistapp.viewmodels.TodoViewModel;

import java.time.OffsetDateTime;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class DashboardFragment extends Fragment {

    private static final int NEW_TODO_ACTIVITY_REQUEST_CODE = 1;

    private TodoViewModel todoViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        FragmentActivity activity = Objects.requireNonNull(this.getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);

        RecyclerView recyclerView = rootView.findViewById(R.id.todo_list_recycler_view);
        TodoListAdapter todoListAdapter = new TodoListAdapter(this.getContext());
        recyclerView.setAdapter(todoListAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(todoListAdapter));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);

        itemTouchHelper.attachToRecyclerView(recyclerView);

        this.todoViewModel = new ViewModelProvider(activity).get(TodoViewModel.class);
        this.todoViewModel.getAllTodos().observe(activity, todoListAdapter::setTodos);

        todoListAdapter.setOnDeleteTodoListener(todo -> todoViewModel.deleteTodo(todo.getId()));
        todoListAdapter.setOnRestoreTodoListener(restoredTodo -> todoViewModel.insert(restoredTodo));

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.nav_menus, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add_todo) {
            Intent intent = new Intent(this.getContext(), NewTodoActivity.class);
            startActivityForResult(intent, NEW_TODO_ACTIVITY_REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_TODO_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Todo newTodo = new Todo(data.getStringExtra(NewTodoActivity.EXTRA_REPLY), OffsetDateTime.now());
                this.todoViewModel.insert(newTodo);
            }
        } else {
            Toast toast = Toast.makeText(
                    Objects.requireNonNull(this.getContext()).getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

}
