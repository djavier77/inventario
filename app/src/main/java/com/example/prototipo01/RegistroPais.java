package com.example.prototipo01;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prototipo01.utilidades.Utilidades;


public class RegistroPais extends AppCompatActivity {

    EditText campoNombrePais,campoObservacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registro_pais);

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);

        campoNombrePais= (EditText) findViewById(R.id.campoNombrePais);
        campoObservacion= (EditText) findViewById(R.id.campoObervacion);

    }


    public void onClick(View view) {
        registrarUsuarios();
        //registrarUsuariosSql();
    }


    private void registrarUsuarios() {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);

        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_NOMBRE_PAIS,campoNombrePais.getText().toString());
        values.put(Utilidades.CAMPO_OBSERVACIONES,campoObservacion.getText().toString());

        Long idResultante=db.insert(Utilidades.TABLA_PAIS,Utilidades.CAMPO_NOMBRE_PAIS,values);

        Toast.makeText(getApplicationContext(),"Registrado correctamente: "+idResultante, Toast.LENGTH_SHORT).show();

        db.close();
    }
}
