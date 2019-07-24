package com.example.prototipo01;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prototipo01.entidades.Productos;
import com.example.prototipo01.entidades.Usuarios;
import com.example.prototipo01.utilidades.Utilidades;

import java.util.ArrayList;

public class RegistroProductoActivity extends AppCompatActivity {

    ConexionSQLiteHelper conn;
    EditText campo_codigo_barra,campo_id_nombre_producto,campo_cantidad,campo_cantidad_minima,campo_detalle_producto,campo_precio;
    Spinner comboUsuarios;
    ArrayList<String> listaTipoU;
    ArrayList<Usuarios> tipoListU;
    Integer U;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registro_producto);

        conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null,1);

        campo_codigo_barra = (EditText) findViewById(R.id.campoCodigoBarra);
        campo_id_nombre_producto = (EditText) findViewById(R.id.campo_id_nombre_producto);
        campo_cantidad = (EditText) findViewById(R.id.campo_cantidad);
        campo_cantidad_minima = (EditText) findViewById(R.id.campo_cantidad_minima);
        campo_precio = (EditText) findViewById(R.id.campo_precio);
        campo_detalle_producto = (EditText) findViewById(R.id.campo_detalle_producto);
        comboUsuarios =(Spinner) findViewById(R.id.comboUsuarioProducto);

        consultarListaProductos();

        ArrayAdapter<CharSequence> adaptadorP=new ArrayAdapter
                (this,android.R.layout.simple_spinner_item,listaTipoU);

        comboUsuarios.setAdapter(adaptadorP);

        comboUsuarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idl) {
                if (position!=0){
                    U = tipoListU.get(position-1).getId_usuario();
                }else{

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }

    private void consultarListaProductos() {
        SQLiteDatabase db=conn.getReadableDatabase();

        Usuarios usuarios=null;
        tipoListU =new ArrayList<Usuarios>();

        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_USUARIO,null);

        while (cursor.moveToNext()){
            usuarios=new Usuarios();
            usuarios.setId_usuario(cursor.getInt(0));
            usuarios.setNickname(cursor.getString(4));

            Log.i("Nickname",usuarios.getNickname());

            tipoListU.add(usuarios);
        }
        obtenerListaU();
    }

    public void onClick(View view) {
        registrarProductos();
    }

    private void registrarProductos() {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);

        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_CODIGO_PRODUCTO,campo_codigo_barra.getText().toString());
        values.put(Utilidades.CAMPO_NOMBRE_PRODUCTO,campo_id_nombre_producto.getText().toString());
        values.put(Utilidades.CAMPO_CANTIDAD,campo_cantidad.getText().toString());
        values.put(Utilidades.CAMPO_CANTIDAD_MINIMA,campo_cantidad_minima.getText().toString());
        values.put(Utilidades.CAMPO_PRECIO,campo_precio.getText().toString());
        values.put(Utilidades.CAMPO_DETALLE_PRODUCTO,campo_detalle_producto.getText().toString());
        values.put(Utilidades.CAMPO_ID_USUARIO,U);

        Long idResultante=db.insert(Utilidades.TABLA_PRODUCTOS,Utilidades.CAMPO_NOMBRE_PRODUCTO,values);

        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante,Toast.LENGTH_SHORT).show();
        db.close();
    }

    private void obtenerListaU() {
        listaTipoU=new ArrayList<String>();
        listaTipoU.add("Seleccione Usuario");

        for(int i=0;i<tipoListU.size();i++){
            listaTipoU.add(tipoListU.get(i).getNickname());
        }

    }

}
