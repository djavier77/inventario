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

import com.example.prototipo01.entidades.Cargo;
import com.example.prototipo01.entidades.Productos;
import com.example.prototipo01.entidades.Usuarios;
import com.example.prototipo01.utilidades.Utilidades;

import java.util.ArrayList;

public class ActualizarProductos extends AppCompatActivity {
    EditText campoDetalleProd,campoPrecio,campoCantidadMinima,campoCantidad,campoNombreProd,campoActualizarCodBarras;
    //
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_actualizar_productos);

        conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null,1);

        campoActualizarCodBarras= (EditText) findViewById(R.id.campoActualizarCodBarras);
        campoNombreProd= (EditText) findViewById(R.id.campoNombreProd);
        campoCantidad= (EditText) findViewById(R.id.campoCantidad);
        campoCantidadMinima= (EditText) findViewById(R.id.campoCantidadMinima);
        campoPrecio= (EditText) findViewById(R.id.campoPrecio);
        campoDetalleProd= (EditText) findViewById(R.id.campoDetalleProd);
        //comboCategoria= (Spinner) findViewById(R.id.comboCategoriaAP);

    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnConsultar:
//                consultar();
                consultarSql();
                break;
            case R.id.btnActualizar: actualizarUsuario();
                break;
            case R.id.btnEliminar: eliminarUsuario();
                break;
        }

    }

    private void eliminarUsuario() {
        SQLiteDatabase db=conn.getWritableDatabase();
        String[] parametros={campoActualizarCodBarras.getText().toString()};

        db.delete(Utilidades.TABLA_PRODUCTOS,Utilidades.CAMPO_CODIGO_PRODUCTO+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Ya se Eliminó el producto",Toast.LENGTH_LONG).show();
        campoActualizarCodBarras.setText("");
        limpiar();
        db.close();
    }

    private void actualizarUsuario() {
        SQLiteDatabase db=conn.getWritableDatabase();
        String[] parametros={campoActualizarCodBarras.getText().toString()};
        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_NOMBRE_PRODUCTO,campoNombreProd.getText().toString());
        values.put(Utilidades.CAMPO_CANTIDAD,campoCantidad.getText().toString());
        values.put(Utilidades.CAMPO_CANTIDAD_MINIMA,campoCantidadMinima.getText().toString());
        values.put(Utilidades.CAMPO_PRECIO,campoPrecio.getText().toString());
        values.put(Utilidades.CAMPO_DETALLE_PRODUCTO,campoDetalleProd.getText().toString());

        db.update(Utilidades.TABLA_PRODUCTOS,values,Utilidades.CAMPO_CODIGO_PRODUCTO+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Ya se actualizó el producto",Toast.LENGTH_LONG).show();
        db.close();

    }

    private void consultarSql() {
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={campoActualizarCodBarras.getText().toString()};

        try {
            //select nombre,telefono from usuario where codigo=?
            Cursor cursor=db.rawQuery("SELECT * FROM "+Utilidades.TABLA_PRODUCTOS+" WHERE "+Utilidades.CAMPO_CODIGO_PRODUCTO+"=? ",parametros);

            cursor.moveToFirst();
            campoNombreProd.setText(cursor.getString(2));
            campoCantidad.setText(cursor.getString(3));
            campoCantidadMinima.setText(cursor.getString(4));
            campoPrecio.setText(cursor.getString(5));
            campoDetalleProd.setText(cursor.getString(6));

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El producto no existe",Toast.LENGTH_LONG).show();
            limpiar();
        }

    }

    private void consultar() {
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={campoActualizarCodBarras.getText().toString()};
        String[] campos={Utilidades.CAMPO_NOMBRES,Utilidades.CAMPO_TELEFONO};

        try {
            Cursor cursor =db.rawQuery("SELECT * FROM "+Utilidades.TABLA_PRODUCTOS+" WHERE "+Utilidades.CAMPO_CODIGO_PRODUCTO+"=? ",parametros);
            cursor.moveToFirst();
            campoNombreProd.setText(cursor.getString(1));
            campoCantidad.setText(cursor.getString(2));
            campoCantidadMinima.setText(cursor.getString(3));
            campoPrecio.setText(cursor.getString(4));
            campoDetalleProd.setText(cursor.getString(5));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El producto no existe",Toast.LENGTH_LONG).show();
            limpiar();
        }


    }

    private void limpiar() {
        campoActualizarCodBarras.setText("");
        campoNombreProd.setText("");
        campoCantidad.setText("");
        campoCantidadMinima.setText("");
        campoPrecio.setText("");
        campoDetalleProd.setText("");
    }


}
