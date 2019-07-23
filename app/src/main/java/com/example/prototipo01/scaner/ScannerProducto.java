package com.example.prototipo01.scaner;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prototipo01.ConexionSQLiteHelper;
import com.example.prototipo01.MainActivity;
import com.example.prototipo01.R;
import com.example.prototipo01.entidades.Productos;
import com.example.prototipo01.utilidades.Utilidades;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.example.prototipo01.correo.MainActivityMail;

import java.util.ArrayList;

public class ScannerProducto extends AppCompatActivity {

    private Button btnScanner;
    private TextView tvBarCode;
    private String codigoBarra;
    Integer stock,cantidadMinima;
    String producto;

    ListView listViewProductos;
    ArrayList<String> listaInformacion;
    ArrayList<Productos> listaProductos;
    ConexionSQLiteHelper conn;

    String t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_scaner);

        btnScanner=findViewById(R.id.button);
        tvBarCode=findViewById(R.id.textView);

        btnScanner.setOnClickListener(mOnClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result !=null)
            if (result.getContents() != null){
                tvBarCode.setText("El codigo de barras es:\n"+result.getContents());

                //********
                codigoBarra=result.getContents().toString().trim();
                consultarListaProductos();


                listViewProductos= (ListView) findViewById(R.id.listViewProductos);

                ArrayAdapter adaptador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
                listViewProductos.setAdapter(adaptador);

                listViewProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                    }
                });

            }else {
                tvBarCode.setText("Error al escanear de barras");
                //****************************************
               /* consultarListaProductos();


                listViewProductos= (ListView) findViewById(R.id.listViewProductos);

                ArrayAdapter adaptador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
                listViewProductos.setAdapter(adaptador);

                listViewProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                    }
                });*/
                //***************8
            }

    }

    private View.OnClickListener mOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.button:
                new IntentIntegrator(ScannerProducto.this).initiateScan();
                break;
            }
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle guardaEstado) {
        super.onSaveInstanceState(guardaEstado);
        //guardamos en la variable t el texto del campo EditText
        t = tvBarCode.getText().toString();
        //lo "guardamos" en el Bundle
        guardaEstado.putString("text", t);
    }

    @Override
    protected void onRestoreInstanceState(Bundle recuperaEstado) {
        super.onRestoreInstanceState(recuperaEstado);
        //recuperamos el String del Bundle
        t = recuperaEstado.getString("text");
        //Seteamos el valor del EditText con el valor de nuestra cadena
        tvBarCode.setText(t);
        codigoBarra=t;


    }

    private void consultarListaProductos() {
        conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getReadableDatabase();


        Productos productos=null;
        listaProductos=new ArrayList<Productos>();
        //select * from usuarios
        //codigoBarra="12345";
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_PRODUCTOS+" WHERE codigo_producto="+codigoBarra+"",null);

        //cursor.moveToFirst();
        while (cursor.moveToNext()){
            productos=new Productos();
            productos.setId_producto(cursor.getString(0));
            productos.setCodigo_producto(cursor.getString(1));
            productos.setId_nombre_producto(cursor.getString(2));
            System.out.println("**********************: "+cursor.getString(2));
            productos.setCantidad(cursor.getInt(3));
            productos.setCantidad_minima(cursor.getInt(4));
            productos.setPrecio(cursor.getInt(5));
            productos.setDetalle_producto(cursor.getString(6));
            productos.setId_usuario(cursor.getInt(7));

            listaProductos.add(productos);
        }
        obtenerListaP();

        //Codigo if para envio de mil
/*
        if(stock<=cantidadMinima)
        {
            MainActivityMail mail = new MainActivityMail();
            mail.Email(producto,cantidadMinima,"djcollaguazoc@uce.edu.ec");
        }
*/





        //******************
    }

    private void obtenerListaP() {
        listaInformacion=new ArrayList<String>();
        System.out.println(listaProductos.size());
        for (int i=0; i<listaProductos.size();i++){
            listaInformacion.add(listaProductos.get(i).getId_nombre_producto()+" - Precio: "
                    +listaProductos.get(i).getPrecio()+"$ - Detalle: "+listaProductos.get(i).getDetalle_producto()+" - Cantidad "+listaProductos.get(i).getCantidad());
//obtener valoras para enviar al metodo Email
            stock=listaProductos.get(i).getCantidad();
            cantidadMinima=listaProductos.get(i).getCantidad_minima();
            producto=listaProductos.get(i).getId_nombre_producto();
        }
    }


}
