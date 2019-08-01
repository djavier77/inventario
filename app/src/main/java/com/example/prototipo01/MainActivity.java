package com.example.prototipo01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.prototipo01.scaner.ScannerProducto;
import com.github.clans.fab.FloatingActionMenu;

public class MainActivity extends AppCompatActivity {
    FloatingActionMenu actionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //actionMenu = (FloatingActionMenu)findViewById(R.id.fabPrincipal);
        //actionMenu.setClosedOnTouchOutside(true);

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);

    }


    public void onClick(View view) {
        String userLogeado = getIntent().getStringExtra("usuario");
        Intent miIntent=null;
        switch (view.getId()){

            case R.id.btnActualizarProducto:
                miIntent=new Intent(MainActivity.this,ActualizarProductos.class);
                miIntent.putExtra("usuarioLogeado", userLogeado);
                break;
            case R.id.btnRegistroProducto:
                miIntent=new Intent(MainActivity.this,RegistroProductoActivity.class);
                break;
            case R.id.btnListarProducto:
                miIntent=new Intent(MainActivity.this,ListarProductos.class);
                break;
            case R.id.btnActualizarPais:
                miIntent=new Intent(MainActivity.this,ActualizarDatos.class);
                miIntent.putExtra("usuarioLogeado", userLogeado);
                break;
            //case R.id.fabScanner:
              //  miIntent=new Intent(MainActivity.this, ScannerProducto.class);
                //miIntent.putExtra("usuarioLogeado", userLogeado);
                //break;
            case R.id.btnScanner:
                miIntent=new Intent(MainActivity.this, ScannerProducto.class);
                //miIntent.putExtra("usuarioLogeado", userLogeado);
                break;
            /*case R.id.btnMail:
                miIntent=new Intent(MainActivity.this, MainActivityMail.class);
                //miIntent.putExtra("usuarioLogeado", userLogeado);
                break;*/
        }
        if (miIntent!=null){
            startActivity(miIntent);
        }

    }
}
