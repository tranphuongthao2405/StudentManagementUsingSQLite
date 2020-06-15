package com.example.studentmanagementusingsqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Student> students;
    StudentAdapter studentAdapter;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(MainActivity.this);
        listView = findViewById(R.id.list);
        students = databaseHelper.getAllStudents();
        studentAdapter = new StudentAdapter(MainActivity.this, R.layout.layout_display, students);
        listView.setAdapter(studentAdapter);

        // register menu for listview
        registerForContextMenu(listView);
    }

    // add: insert student menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // process menu insert student
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_add) {
            final Student student = new Student();

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("Insert student");
            builder.setCancelable(false);

            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View view = inflater.inflate(R.layout.layout_input, null);

            final EditText editId = view.findViewById(R.id.edit_id);
            final EditText editName = view.findViewById(R.id.edit_name);
            final EditText editDatebirth = view.findViewById(R.id.edit_datebirth);
            final EditText editEmail = view.findViewById(R.id.edit_email);
            final EditText editAddress = view.findViewById(R.id.edit_address);

            builder.setView(view);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    student.setId(editId.getText().toString());
                    student.setName(editName.getText().toString());
                    student.setDatebirth(editDatebirth.getText().toString());
                    student.setEmail(editEmail.getText().toString());
                    student.setAddress(editAddress.getText().toString());

                    databaseHelper.insertStudent(student);
                    students.add(student);
                    studentAdapter.notifyDataSetChanged();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    // add update menu & delete menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    // process update menu & delete menu
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int id = item.getItemId();
        if(id == R.id.menu_update) {
            final Student student = students.get(info.position);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("Update student");
            builder.setCancelable(false);

            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View view = inflater.inflate(R.layout.layout_input, null);

            final EditText editId = view.findViewById(R.id.edit_id);
            final EditText editName = view.findViewById(R.id.edit_name);
            final EditText editDatebirth = view.findViewById(R.id.edit_datebirth);
            final EditText editEmail = view.findViewById(R.id.edit_email);
            final EditText editAddress = view.findViewById(R.id.edit_address);

            editId.setText(student.getId());
            editName.setText(student.getName());
            editDatebirth.setText(student.getDatebirth());
            editEmail.setText(student.getEmail());
            editAddress.setText(student.getAddress());

            builder.setView(view);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    student.setId(editId.getText().toString());
                    student.setName(editName.getText().toString());
                    student.setDatebirth(editDatebirth.getText().toString());
                    student.setEmail(editEmail.getText().toString());
                    student.setAddress(editAddress.getText().toString());

                    databaseHelper.updateStudent(student);
                    students.set(info.position, student);
                    studentAdapter.notifyDataSetChanged();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else if (id == R.id.menu_delete) {
            final Student student = students.get(info.position);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Delete student");
            builder.setCancelable(false);
            builder.setMessage("Are you sure to delete " + student.getName() + " ?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    databaseHelper.deleteStudent(student);
                    students.remove(info.position);
                    studentAdapter.notifyDataSetChanged();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return super.onContextItemSelected(item);
    }
}
