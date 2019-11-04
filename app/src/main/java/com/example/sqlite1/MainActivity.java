package com.example.sqlite1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText NIM, Nama, TanggalLahir, Alamat;
    private Spinner Jurusan;
    private RadioButton MALE, FEMALE;

    //variabel untuk menyimpan input dari user
    private String setNIM, setNama, setTanggalLahir, setAlamat, setJurusan, setJenisKelamin;

    //variabel untuk inisialisasi database DBMahasiswa
    private DBMahasiswa dbMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button simpan=(Button)findViewById(R.id.save);
        NIM=(EditText)findViewById(R.id.nim);
        Nama=(EditText)findViewById(R.id.nama);
        TanggalLahir=(EditText)findViewById(R.id.date);
        MALE=(RadioButton) findViewById(R.id.male);
        FEMALE=(RadioButton) findViewById(R.id.female);
        Jurusan=(Spinner)findViewById(R.id.jurusan);
        Alamat=(EditText)findViewById(R.id.alamat);

        dbMahasiswa = new DBMahasiswa(getBaseContext());

        DBMahasiswa dbMahasiswa = new DBMahasiswa(getBaseContext());
        Toast.makeText(getApplication(),
                dbMahasiswa.getDatabaseName(), Toast.LENGTH_SHORT).show();

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
                saveData();
                Toast.makeText(getApplicationContext(), "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                clearData();
            }
        });

    }

    private void setData() {
        setNIM = NIM.getText().toString();
        setNama = Nama.getText().toString();
        setJurusan = Jurusan.getSelectedItem().toString();
        if (MALE.isChecked()) {
            setJenisKelamin = FEMALE.getText().toString();
        } else if (FEMALE.isChecked()) {
            setJenisKelamin = MALE.getText().toString();
        }
        setTanggalLahir = TanggalLahir.getText().toString();
        setAlamat = Alamat.getText().toString();
    }

    private void saveData() {
        //mendapatkan repository dengan mode menulis
        SQLiteDatabase create = dbMahasiswa.getWritableDatabase();

        //membuat map baru, yang berisi nama kolom dan data yang ingin dimasukkan
        ContentValues values = new ContentValues();
        values.put(DBMahasiswa.MyColumns.NIM, setNIM);
        values.put(DBMahasiswa.MyColumns.Nama, setNama);
        values.put(DBMahasiswa.MyColumns.Jurusan, setJurusan);
        values.put(DBMahasiswa.MyColumns.JenisKelamin, setJenisKelamin);
        values.put(DBMahasiswa.MyColumns.Alamat, setAlamat);

        //menambahkan baris baru, berupa data yang sudah diinputkan pada kolom di dalam database
        create.insert(DBMahasiswa.MyColumns.NamaTabel, null, values);
    }

    private void clearData() {
        NIM.setText("");
        Nama.setText("");
        TanggalLahir.setText("");
        Alamat.setText("");
    }
}
