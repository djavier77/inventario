package com.example.prototipo01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.prototipo01.correo.MainActivityMail;
import com.example.prototipo01.scaner.ScannerProducto;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);

    }


    public void onClick(View view) {
        String userLogeado = getIntent().getStringExtra("usuario");
        Intent miIntent=null;
        switch (view.getId()){
            case R.id.btnOpcionRegistro:
                miIntent=new Intent(MainActivity.this, RegistroCargoActivity.class);
                break;
            case R.id.btnRegistroUsuario:
                miIntent=new Intent(MainActivity.this,RegistroUsuariosActivity.class);
                break;
            case R.id.btnRegistroProducto:
                miIntent=new Intent(MainActivity.this,RegistroProductoActivity.class);
                break;
            case R.id.btnListarProducto:
                miIntent=new Intent(MainActivity.this,ListarProductos.class);
                break;
            case R.id.btnActualizar:
                miIntent=new Intent(MainActivity.this,ActualizarDatos.class);
                miIntent.putExtra("usuarioLogeado", userLogeado);
                break;
            case R.id.btnScanner:
                miIntent=new Intent(MainActivity.this, ScannerProducto.class);
                //miIntent.putExtra("usuarioLogeado", userLogeado);
                break;
            case R.id.btnMail:
                miIntent=new Intent(MainActivity.this, MainActivityMail.class);
                //miIntent.putExtra("usuarioLogeado", userLogeado);
                break;
        }
        if (miIntent!=null){
            startActivity(miIntent);
        }

    }
}
