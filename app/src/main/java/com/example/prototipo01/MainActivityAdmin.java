package com.example.prototipo01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prototipo01.scaner.ScannerProducto;
import com.github.clans.fab.FloatingActionMenu;

public class MainActivityAdmin extends AppCompatActivity {
    FloatingActionMenu actionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_admin);

        //actionMenu = (FloatingActionMenu)findViewById(R.id.fabPrincipal);
        //actionMenu.setClosedOnTouchOutside(true);

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);

    }


    public void onClick(View view) {
        String userLogeado = getIntent().getStringExtra("usuario");
        Intent miIntent=null;
        switch (view.getId()){
            case R.id.btnActualizarProducto:
                miIntent=new Intent(MainActivityAdmin.this,ActualizarProductos.class);
                break;
            case R.id.btnOpcionRegistro:
                miIntent=new Intent(MainActivityAdmin.this, RegistroCargoActivity.class);
                break;
            case R.id.btnPais:
                miIntent=new Intent(MainActivityAdmin.this,RegistroPais.class);
                break;
            case R.id.btnRegistroProducto:
                miIntent=new Intent(MainActivityAdmin.this,RegistroProductoActivity.class);
                break;
            case R.id.btnListarProducto:
                miIntent=new Intent(MainActivityAdmin.this,ListarProductos.class);
                break;
            case R.id.btnActualizarPais:
                miIntent=new Intent(MainActivityAdmin.this,ActualizarPais.class);
                miIntent.putExtra("usuarioLogeado", userLogeado);
                break;
            //case R.id.fabScanner:
              //  miIntent=new Intent(MainActivityAdmin.this, ScannerProducto.class);
                //miIntent.putExtra("usuarioLogeado", userLogeado);
                //break;
            case R.id.btnScanner:
                miIntent=new Intent(MainActivityAdmin.this, ScannerProducto.class);
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
