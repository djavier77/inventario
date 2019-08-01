package com.example.prototipo01;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prototipo01.utilidades.Utilidades;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ActualizarPais extends AppCompatActivity {
    private Button btnScanner;
    String t;
    EditText campoDetalleProd,campoPrecio,campoCantidadMinima,campoObervacion,campoNombrePais,CampoIDPais;
    //
    ConexionSQLiteHelper conn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_actualizar_pais);
        btnScanner=findViewById(R.id.buttonSCA);
        conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null,1);

        CampoIDPais= (EditText) findViewById(R.id.campoIsPais);
        campoNombrePais= (EditText) findViewById(R.id.campoNombrePais);
        campoObervacion= (EditText) findViewById(R.id.campoObservacion);

        //comboCategoria= (Spinner) findViewById(R.id.comboCategoriaAP);

    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnConsultar:
//                consultar();
                //new IntentIntegrator(ActualizarPais.this).initiateScan();
                consultarSql();
                break;
            case R.id.btnActualizarPais: actualizarUsuario();
                break;
            case R.id.btnEliminar: eliminarUsuario();
                break;
        }

    }

    private void eliminarUsuario() {
        SQLiteDatabase db=conn.getWritableDatabase();
        String[] parametros={CampoIDPais.getText().toString()};

        db.delete(Utilidades.TABLA_PRODUCTOS,Utilidades.CAMPO_CODIGO_PRODUCTO+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Ya se Eliminó el producto",Toast.LENGTH_LONG).show();
        CampoIDPais.setText("");
        limpiar();
        db.close();
    }

    private void actualizarUsuario() {
        SQLiteDatabase db=conn.getWritableDatabase();
        String[] parametros={CampoIDPais.getText().toString()};
        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_NOMBRE_PAIS,campoNombrePais.getText().toString());
        values.put(Utilidades.CAMPO_OBSERVACIONES,campoObervacion.getText().toString());

        db.update(Utilidades.TABLA_PAIS,values,Utilidades.CAMPO_ID_PAIS+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Ya se actualizó el pais",Toast.LENGTH_LONG).show();
        db.close();

    }

    private void consultarSql() {
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={CampoIDPais.getText().toString()};

        try {
            //select nombre,telefono from usuario where codigo=?
            Cursor cursor=db.rawQuery("SELECT * FROM "+Utilidades.TABLA_PAIS+" WHERE "+Utilidades.CAMPO_ID_PAIS+"=? ",parametros);

            cursor.moveToFirst();
            campoNombrePais.setText(cursor.getString(1));
            campoObervacion.setText(cursor.getString(2));


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El producto no existe",Toast.LENGTH_LONG).show();
            limpiar();
        }

    }

    private void consultar() {
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={CampoIDPais.getText().toString()};
        String[] campos={Utilidades.CAMPO_NOMBRES,Utilidades.CAMPO_TELEFONO};

        try {
            Cursor cursor =db.rawQuery("SELECT * FROM "+Utilidades.TABLA_PRODUCTOS+" WHERE "+Utilidades.CAMPO_CODIGO_PRODUCTO+"=? ",parametros);
            cursor.moveToFirst();
            campoNombrePais.setText(cursor.getString(1));
            campoObervacion.setText(cursor.getString(2));

            cursor.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El producto no existe",Toast.LENGTH_LONG).show();
            limpiar();
        }


    }

    private void limpiar() {
        CampoIDPais.setText("");
        campoNombrePais.setText("");
        campoObervacion.setText("");
        campoCantidadMinima.setText("");
        campoPrecio.setText("");
        campoDetalleProd.setText("");
    }

    ///PCODIGO PARA OCUPAR ESCANER
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result= IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result !=null)
            if (result.getContents() != null){
                CampoIDPais.setText(result.getContents().trim());


            }else {
                CampoIDPais.setText("Error al escanear de barras");

            }

    }

    private View.OnClickListener mOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.button:
                    new IntentIntegrator(ActualizarPais.this).initiateScan();
                    break;
            }
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle guardaEstado) {
        super.onSaveInstanceState(guardaEstado);
        //guardamos en la variable t el texto del campo EditText
        t = CampoIDPais.getText().toString();
        //lo "guardamos" en el Bundle
        guardaEstado.putString("text", t);
    }

    @Override
    protected void onRestoreInstanceState(Bundle recuperaEstado) {
        super.onRestoreInstanceState(recuperaEstado);
        //recuperamos el String del Bundle
        t = recuperaEstado.getString("text");
        //Seteamos el valor del EditText con el valor de nuestra cadena
        CampoIDPais.setText(t);
        consultarSql();
        //codigoBarra=t;


    }


}
