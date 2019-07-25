package com.example.prototipo01;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prototipo01.entidades.Productos;
import com.example.prototipo01.utilidades.Utilidades;

import java.util.ArrayList;

public class ListarProductos extends AppCompatActivity{
    ListView listViewProductos;
    ArrayList<String> listaInformacion;
    ArrayList<Productos> listaProductos;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_listar_productos);

        conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        listViewProductos= (ListView) findViewById(R.id.listViewProductos);

        consultarListaProductos();

        ArrayAdapter adaptador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
        listViewProductos.setAdapter(adaptador);

        listViewProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

            }
        });

    }

    private void consultarListaProductos() {
        SQLiteDatabase db=conn.getReadableDatabase();

        Productos productos=null;
        listaProductos=new ArrayList<Productos>();
        //select * from usuarios
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_PRODUCTOS,null);

        //cursor.moveToFirst();
        while (cursor.moveToNext()){
            productos=new Productos();
            productos.setId_producto(cursor.getString(1));
            productos.setId_nombre_producto(cursor.getString(2));
            System.out.println("**********************: "+cursor.getString(1));
            productos.setCantidad(cursor.getInt(3));
            productos.setCantidad_minima(cursor.getInt(4));
            productos.setPrecio(cursor.getString(5));
            productos.setDetalle_producto(cursor.getString(6));
            productos.setId_usuario(cursor.getInt(7));

            listaProductos.add(productos);
        }
        obtenerListaP();
    }

    private void obtenerListaP() {
        listaInformacion=new ArrayList<String>();
        System.out.println(listaProductos.size());
        for (int i=0; i<listaProductos.size();i++){
            listaInformacion.add("Código de Barras: "+listaProductos.get(i).getId_producto()+"\nNombre de Producto: "+listaProductos.get(i).getId_nombre_producto()+"\nPrecio: "
                    +listaProductos.get(i).getPrecio()+"\nCantidad: "+listaProductos.get(i).getCantidad());
        }
    }
}
