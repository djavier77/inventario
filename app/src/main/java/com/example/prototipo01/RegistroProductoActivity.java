package com.example.prototipo01;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prototipo01.entidades.Productos;
import com.example.prototipo01.entidades.Usuarios;
import com.example.prototipo01.scaner.ScannerProducto;
import com.example.prototipo01.utilidades.Utilidades;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class RegistroProductoActivity extends AppCompatActivity {

    private Button btnScanner;
    String t;
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
        btnScanner=findViewById(R.id.buttonSCA);
        conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null,1);


        campo_codigo_barra = (EditText) findViewById(R.id.campoCodigoBarra);
        campo_id_nombre_producto = (EditText) findViewById(R.id.campo_id_nombre_producto);
        campo_cantidad = (EditText) findViewById(R.id.campo_cantidad);
        campo_cantidad_minima = (EditText) findViewById(R.id.campo_cantidad_minima);
        campo_precio = (EditText) findViewById(R.id.campo_precio);
        campo_detalle_producto = (EditText) findViewById(R.id.campo_detalle_producto);
        comboUsuarios =(Spinner) findViewById(R.id.comboUsuarioProducto);

        consultarListaUsuarios();

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

    private void consultarListaUsuarios() {
        SQLiteDatabase db=conn.getReadableDatabase();

        Usuarios usuarios=null;
        tipoListU =new ArrayList<Usuarios>();

        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_USUARIO,null);

        while (cursor.moveToNext()){
            usuarios=new Usuarios();
            usuarios.setId_usuario(cursor.getInt(0));
            usuarios.setNombres(cursor.getString(2));

            Log.i("Nombres",usuarios.getNombres());

            tipoListU.add(usuarios);
        }
        obtenerListaU();
    }

    public void onClick(View view) {


        Intent miIntent=null;

        switch (view.getId()){

            case R.id.btnRegistroProducto:
                registrarProductos();
                break;
            case R.id.buttonSCA:
                new IntentIntegrator(RegistroProductoActivity.this).initiateScan();
                break;
            /*case R.id.btnMail:
                miIntent=new Intent(MainActivity.this, MainActivityMail.class);
                //miIntent.putExtra("usuarioLogeado", userLogeado);
                break;*/
        }
        if (miIntent!=null){
            //startActivity(miIntent);
        }


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


        db.close();
        if(idResultante.toString().equals("-1")){
            Toast.makeText(getApplicationContext(),"ERROR al insertar, codigo duplicado",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante,Toast.LENGTH_SHORT).show();
        }
    }

    private void obtenerListaU() {
        listaTipoU=new ArrayList<String>();
        listaTipoU.add("Seleccione Usuario");

        for(int i=0;i<tipoListU.size();i++){
            listaTipoU.add(tipoListU.get(i).getNombres());
        }
    }
///PCODIGO PARA OCUPAR ESCANER
@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    IntentResult result= IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
    if (result !=null)
        if (result.getContents() != null){
            campo_codigo_barra.setText(result.getContents().trim());


        }else {
            campo_codigo_barra.setText("Error al escanear de barras");

        }

    }

    private View.OnClickListener mOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.button:
                    new IntentIntegrator(RegistroProductoActivity.this).initiateScan();
                    break;
            }
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle guardaEstado) {
        super.onSaveInstanceState(guardaEstado);
        //guardamos en la variable t el texto del campo EditText
        t = campo_codigo_barra.getText().toString();
        //lo "guardamos" en el Bundle
        guardaEstado.putString("text", t);
    }

    @Override
    protected void onRestoreInstanceState(Bundle recuperaEstado) {
        super.onRestoreInstanceState(recuperaEstado);
        //recuperamos el String del Bundle
        t = recuperaEstado.getString("text");
        //Seteamos el valor del EditText con el valor de nuestra cadena
        campo_codigo_barra.setText(t);
        //codigoBarra=t;


    }

}
