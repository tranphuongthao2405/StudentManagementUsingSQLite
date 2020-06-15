package com.example.studentmanagementusingsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DatabaseHelper {

    public static final String DATABASE_NAME = "Students.sqlite";
    private static final String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase db = null;
    Context context;

    public DatabaseHelper(Context context) {
        this.context = context;

        processSQLite();
    }

    private void processSQLite() {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        if(!dbFile.exists()) {
            try {
                CopyDatabaseFromAsset();
                Toast.makeText(context, "Copied.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void CopyDatabaseFromAsset() {
        try {
            InputStream databaseInputStream = context.getAssets().open(DATABASE_NAME);

            String outputStream = getPathDatabaseSystem();

            File file = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if(!file.exists()) {
                file.mkdir();
            }

            OutputStream databaseOutputStream = new FileOutputStream(outputStream);

            byte[] buffer = new byte[1024];
            int length;
            while((length = databaseInputStream.read(buffer)) > 0) {
                databaseOutputStream.write(buffer, 0, length);
            }

            databaseOutputStream.flush();
            databaseOutputStream.close();
            databaseInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPathDatabaseSystem() {
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }


    public void insertStudent(Student student) {
        db = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null);

        // insert new data into table
        ContentValues values = new ContentValues();
        values.put("id", student.getId());
        values.put("name", student.getName());
        values.put("datebirth", student.getDatebirth());
        values.put("email", student.getEmail());
        values.put("address", student.getAddress());

        if(db.insert("Student", null, values) > 0) {
            Toast.makeText(context, "Inserted", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateStudent(Student student) {
        db = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null);

        // update new data into table
        ContentValues values = new ContentValues();
        values.put("id", student.getId());
        values.put("name", student.getName());
        values.put("datebirth", student.getDatebirth());
        values.put("email", student.getEmail());
        values.put("address", student.getAddress());

        if(db.update("Student", values, "id=" + student.getId(), null) > 0) {
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteStudent(Student student) {
        db = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null);

        if(db.delete("Student", "id=" + student.getId(), null) > 0) {
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<>();

        db = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null);

        String query = "SELECT * FROM Student";

        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String datebirth = cursor.getString(2);
            String email = cursor.getString(3);
            String address = cursor.getString(4);
            students.add(new Student(id, name, datebirth, email, address));
        }

        return students;
    }
}
