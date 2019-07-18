package com.example.prototipo01;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prototipo01.utilidades.Utilidades;

import java.util.Calendar;


public class RegistroUsuariosActivity extends AppCompatActivity {

    ConexionSQLiteHelper conn;
    EditText campoCedula,campoNombres,campoFechaIngreso,campoNickname, campoPassword, campoTelefono, campoMail;
    Button bfecha;
    EditText efecha;
    private  int dia,mes,ano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);

        bfecha=(Button)findViewById(R.id.bfecha);
        efecha=(EditText)findViewById(R.id.efechaIngreso);

        //bfecha.setOnClickListener(this);

        //carga la fecha automaticamente
        Calendar calendario= Calendar.getInstance();
        dia=calendario.get(Calendar.DAY_OF_MONTH);
        mes=calendario.get(Calendar.MONTH);
        ano=calendario.get(Calendar.YEAR);
        mostrarFecha();

        campoCedula= (EditText) findViewById(R.id.campoCedula);
        campoNombres= (EditText) findViewById(R.id.campoNombres);
        campoFechaIngreso= (EditText) findViewById(R.id.efechaIngreso);
        campoNickname= (EditText) findViewById(R.id.campoNickname);
        campoPassword= (EditText) findViewById(R.id.campoPassword);
        campoTelefono= (EditText) findViewById(R.id.campoTelefono);
        campoMail= (EditText) findViewById(R.id.campoMail);
    }

    public void onClickfecha(View view) {
        if(view==bfecha){
            Calendar c= Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            ano=c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    efecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                }
            }
                    ,dia,mes,ano);
            datePickerDialog.show();
        }


    }

    public void mostrarFecha(){
        efecha.setText(dia+"/"+(mes+1)+"/"+ano);
    }


    public void onClick(View view) {
        registrarUsuarios();
        //registrarUsuariosSql();
    }


    private void registrarUsuarios() {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);

        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_CEDULA,campoCedula.getText().toString());
        values.put(Utilidades.CAMPO_NOMBRES,campoNombres.getText().toString());
        values.put(Utilidades.CAMPO_FECHA_INGRESO,campoFechaIngreso.getText().toString());
        values.put(Utilidades.CAMPO_NICKNAME,campoNickname.getText().toString());
        values.put(Utilidades.CAMPO_PASSWORD,campoPassword.getText().toString());
        values.put(Utilidades.CAMPO_TELEFONO,campoTelefono.getText().toString());
        values.put(Utilidades.CAMPO_MAIL,campoMail.getText().toString());

        Long idResultante=db.insert(Utilidades.TABLA_USUARIO,Utilidades.CAMPO_CEDULA,values);

        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante,Toast.LENGTH_SHORT).show();
        db.close();
    }
}
