# **Demo**
# Shared Refferences

## Step 1: Add project layout

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity"
    android:background="@color/white"

    >

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Nhap A:"
        android:textColor="@color/design_default_color_error"
        android:textSize="20dp" />


    <EditText
        android:id="@+id/et_a"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:ems="10"
        android:textColor="@color/black"
        android:inputType="number"
       />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Nhap B:"
        android:textColor="@color/design_default_color_error"
        android:textSize="20dp" />

    <EditText
        android:id="@+id/et_b"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:ems="10"
        android:inputType="number"
        android:textColor="@color/black"

         />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Ket qua:"
        android:textColor="@color/design_default_color_error"
        android:textSize="30dp" />

    <EditText
        android:id="@+id/et_result"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:ems="10"
        android:inputType="number"
        android:textColor="@color/black"

         />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:orientation="horizontal">

        <Button
            android:id="@+id/btn_tong"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="TINH TONG"
            android:layout_marginRight="74dp"/>

        <Button
            android:id="@+id/btn_reset"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Xoa lich su" />
    </LinearLayout>
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Lich su"
        android:textColor="@color/black"
        android:background="@color/design_default_color_secondary"
        android:textSize="30dp" />

    <TextView
        android:id="@+id/tv_history"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Review"
        android:inputType="textMultiLine"
        android:textColor="@color/black"
        android:textSize="20dp" />
</LinearLayout>
```

## Step 2: Set View 

Get  `View` using `findViewById`.

```java
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
  }
```
## Step 3: Add SharedPreferences

```java
        SharedPreferences myprefs = getSharedPreferences("mysave", MODE_PRIVATE);
        lichsu = myprefs.getString("ls","");
        txt_lichsu.setText(lichsu);
```
## Step 4: Set Action for buttons


```java
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
                txt_lichsu.setText(lichsu);
            }
        });
```
## Step 5: Setup OnPause for everytime pause the app it can save data

```java
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences myprefs = getSharedPreferences("mysave",MODE_PRIVATE);
        SharedPreferences.Editor myedit = myprefs.edit();
        myedit.putString("ls",lichsu);
        myedit.commit();
    }
```

# Using Internal File


## Step 1: add Load method

```java
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
```
## Step 2: Add Savefile method

```java
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

```
## Step 3: Where to replace

```java
//SharedPreferences myprefs = getSharedPreferences("mysave", MODE_PRIVATE);
        //lichsu = myprefs.getString("ls","");
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
                //SharedPreferences.Editor myedit = myprefs.edit();
                lichsu="";
             //   myedit.putString("ls",lichsu);
              //  myedit.commit();
                saveToInternalStorage(lichsu);
                txt_lichsu.setText(lichsu);
            }
        });
```
# Using External File


## Step 1: add in Manifest

```java
   <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```


## Step 2: add checker for read/write

```java
    public boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public boolean isExternalStorageReadable() {
        return isExternalStorageWritable() || Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState());
    }
```
## Step 3: Add Loadfile method

```java
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
                    return ""; 
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
            return allText;
        }
        Toast.makeText(this, "Bộ nhớ ngoài không thể đọc", Toast.LENGTH_SHORT).show();
```
## Step 4:Add Save file method

```java
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
```
## Step 5:Where to add new method to?

```java
  et_a = findViewById(R.id.et_a);
        et_b = findViewById(R.id.et_b);
        et_result = findViewById(R.id.et_result);
        btnTong = findViewById(R.id.btn_tong);
        btnReset = findViewById(R.id.btn_reset);
        txt_lichsu = findViewById(R.id.tv_history);
//Add here
        lichsu = loadFromExternalStorage();
        txt_lichsu.setText(lichsu);


-------------------------------------------------------------------

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

-------------------------------------------------------------------


 btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lichsu = "";
                txt_lichsu.setText(lichsu);
                saveToExternalStorage(lichsu);
            }
        });


```


