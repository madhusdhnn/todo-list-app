package com.thetechmaddy.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NewTodoActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.learning.android.todolist.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo);

        EditText todoEditText = findViewById(R.id.todo_edit_text);
        Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(todoEditText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String word = todoEditText.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY, word);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }

}
