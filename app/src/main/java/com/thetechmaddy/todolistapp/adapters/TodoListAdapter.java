package com.thetechmaddy.todolistapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.thetechmaddy.todolistapp.R;
import com.thetechmaddy.todolistapp.converters.DateConverter;
import com.thetechmaddy.todolistapp.models.Todo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder> {

    @FunctionalInterface
    public interface OnDeleteTodoListener {
        void onDeleteTodo(Todo todo);
    }

    @FunctionalInterface
    public interface OnRestoreTodoListener {
        void onRestoreTodo(Todo todo);
    }

    static class TodoListViewHolder extends RecyclerView.ViewHolder {

        private final TextView todoTextView;
        private final TextView timestampTextView;

        private TodoListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.todoTextView = itemView.findViewById(R.id.todo_text);
            this.timestampTextView = itemView.findViewById(R.id.timestamp);
        }

    }

    private final Context context;
    private int lastDeletedItemPosition;
    private List<Todo> todos;
    private Todo lastDeleteTodo;
    private OnDeleteTodoListener onDeleteTodoListener;
    private OnRestoreTodoListener onRestoreTodoListener;

    public Context getContext() {
        return context;
    }

    public TodoListAdapter(Context context) {
        this.todos = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public TodoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(this.context).inflate(R.layout.todo_list_item, parent, false);
        return new TodoListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListViewHolder holder, int position) {
        if (this.todos.isEmpty()) {
            holder.todoTextView.setText(R.string.no_todos);
            holder.timestampTextView.setVisibility(View.GONE);
        } else {
            Todo currentTodo = this.todos.get(position);
            holder.todoTextView.setText(currentTodo.getText());
            holder.itemView.setOnClickListener(view -> Toast.makeText(this.context, currentTodo.getText(), Toast.LENGTH_SHORT).show());
            holder.timestampTextView.setText(new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(DateConverter.toDate(currentTodo.getTimestamp())));
        }
    }

    @Override
    public int getItemCount() {
        return this.todos.size();
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
        notifyDataSetChanged();
    }

    public void setOnDeleteTodoListener(OnDeleteTodoListener l) {
        this.onDeleteTodoListener = l;
    }

    public void setOnRestoreTodoListener(OnRestoreTodoListener l) {
        this.onRestoreTodoListener = l;
    }

    public void deleteTodo(int position, View itemView) {
        this.lastDeletedItemPosition = position;
        this.lastDeleteTodo = this.todos.remove(position);
        Objects.requireNonNull(this.onDeleteTodoListener);
        this.onDeleteTodoListener.onDeleteTodo(this.lastDeleteTodo);
        notifyItemRemoved(position);
        showSnackbar(itemView);
    }

    private void showSnackbar(View view) {
        Snackbar snackbar = Snackbar.make(view, "Todo removed", Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", v -> undoDelete());
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }

    private void undoDelete() {
        this.todos.add(lastDeletedItemPosition, lastDeleteTodo);
        Objects.requireNonNull(this.onRestoreTodoListener);
        this.onRestoreTodoListener.onRestoreTodo(lastDeleteTodo);
    }

}
