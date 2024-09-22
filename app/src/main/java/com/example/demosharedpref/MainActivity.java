/*
package com.example.demosharedpref;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    EditText et_a,et_b,et_result;
    Button btnTong, btnReset;
    TextView txt_lichsu;
    String lichsu = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        et_a = findViewById(R.id.et_a);
        et_b = findViewById(R.id.et_b);
        et_result = findViewById(R.id.et_result);
        btnTong = findViewById(R.id.btn_tong);
        btnReset = findViewById(R.id.btn_reset);
        txt_lichsu = findViewById(R.id.tv_history);



        SharedPreferences myprefs = getSharedPreferences("mysave", MODE_PRIVATE);
        */
/*lichsu = myprefs.getString("ls","");*//*

        lichsu = loadFromInternalStorage();
        txt_lichsu.setText(lichsu);


        btnTong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int a = Integer.parseInt(et_a.getText().toString());
                    int b = Integer.parseInt(et_b.getText().toString());
                    int kq = a+b;
                    et_result.setText(kq+"");
                    lichsu+= a + " + " + b + " = " + kq+"\n";
                    txt_lichsu.setText(lichsu);
                    saveToInternalStorage(lichsu);
                }
                catch (Exception e){
                }

            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor myedit = myprefs.edit();
                lichsu="";
                myedit.putString("ls",lichsu);
                myedit.commit();
                saveToInternalStorage(lichsu);
                txt_lichsu.setText(lichsu);
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences myprefs = getSharedPreferences("mysave",MODE_PRIVATE);
        SharedPreferences.Editor myedit = myprefs.edit();
        myedit.putString("ls",lichsu);
        myedit.commit();
    }


    private void saveToInternalStorage(String data) {
        try {
            PrintStream ps = new PrintStream(openFileOutput("history.txt", MODE_PRIVATE));
            ps.print(data);
            ps.close();
            Toast.makeText(this, "Lưu vào bộ nhớ trong thành công", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String loadFromInternalStorage() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder allText = null;
        try {
            Scanner scanner = new Scanner(openFileInput("history.txt"));
            allText = new StringBuilder();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                allText.append(line).append("\n");
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allText.toString();
    }
}*/
package com.example.demosharedpref;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    EditText et_a, et_b, et_result;
    Button btnTong, btnReset;
    TextView txt_lichsu;
    String lichsu = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_a = findViewById(R.id.et_a);
        et_b = findViewById(R.id.et_b);
        et_result = findViewById(R.id.et_result);
        btnTong = findViewById(R.id.btn_tong);
        btnReset = findViewById(R.id.btn_reset);
        txt_lichsu = findViewById(R.id.tv_history);


        // Tải lịch sử từ bộ nhớ ngoài
        lichsu = loadFromExternalStorage();
        txt_lichsu.setText(lichsu);

        // Xử lý nút Cộng
        btnTong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int a = Integer.parseInt(et_a.getText().toString());
                    int b = Integer.parseInt(et_b.getText().toString());
                    int kq = a + b;
                    et_result.setText(String.valueOf(kq));
                    lichsu += a + " + " + b + " = " + kq + "\n";
                    txt_lichsu.setText(lichsu);
                    // Lưu vào bộ nhớ ngoài
                    saveToExternalStorage(lichsu);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Nhập số hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xử lý nút Reset
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lichsu = "";
                txt_lichsu.setText(lichsu);
                saveToExternalStorage(lichsu);
            }
        });
    }

    private void saveToExternalStorage(String data) {
        if (isExternalStorageWritable()) {
            File outDir = getExternalFilesDir(null);
            File outFile = new File(outDir, "history.txt");

            try {
                PrintStream output = new PrintStream(outFile);
                output.print(data);
                output.close();
                Toast.makeText(this, "Lưu thành công vào bộ nhớ ngoài", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Bộ nhớ ngoài không thể ghi", Toast.LENGTH_SHORT).show();
        }
    }

    private String loadFromExternalStorage() {
        if (isExternalStorageReadable()){
            String allText ="";
            File outDir = getExternalFilesDir(null);
            File outFile = new File(outDir, "history.txt");
            try {
                if (outFile.exists()) {
                    Scanner scanner = new Scanner(outFile);
                    while (scanner.hasNextLine()) {
                        allText +=scanner.nextLine()+"\n";
                    }
                    scanner.close();
                } else {
                    return ""; // Nếu tệp không tồn tại, trả về chuỗi rỗng
                }
            } catch (IOException e) {
                e.printStackTrace();
                return ""; // Trả về chuỗi rỗng nếu có lỗi
            }
            return allText;
        }
        Toast.makeText(this, "Bộ nhớ ngoài không thể đọc", Toast.LENGTH_SHORT).show();

return null;
    }

    public boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public boolean isExternalStorageReadable() {
        return isExternalStorageWritable() || Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState());
    }

}

