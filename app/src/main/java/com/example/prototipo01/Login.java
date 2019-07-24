package com.example.prototipo01;
//
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototipo01.utilidades.RSA;
import com.example.prototipo01.utilidades.Utilidades;

public class Login extends AppCompatActivity {

    EditText campoUsuario;
    EditText campoPass;
    TextView etiValor;
    ConexionSQLiteHelper conn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        campoUsuario = (EditText) findViewById(R.id.campoNickname);
        campoPass = (EditText) findViewById(R.id.campoPass);
        etiValor = (TextView) findViewById(R.id.etiValor);

        conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        registrarUsuarios();
    }

    public  void registrarUsuario (View view){
        Intent intent = new Intent(this, RegistroUsuariosActivity.class);
        startActivity(intent);
    }

    public void consultarloginSql(View view) {
        SQLiteDatabase db=conn.getReadableDatabase();
        String usuarioIng=campoUsuario.getText().toString();
        String passIng=campoPass.getText().toString();

        try {
            //select nombre,telefono from usuario where codigo=?
            Cursor cursor=db.rawQuery("select nickname,password from usuario where nickname='"+usuarioIng+"'",null);

            //DSCENCRIPTAR CLAVE
            //Creamos otro objeto de nuestra clase RSA
            RSA rsa2 = new RSA();

            //Le pasamos el contexto
            rsa2.setContext(getBaseContext());

            //Cargamos las claves que creamos anteriormente
            rsa2.openFromDiskPrivateKey("rsa"+campoUsuario.getText().toString()+".pri");
            rsa2.openFromDiskPublicKey("rsa"+campoUsuario.getText().toString()+".pub");

            //Mostramos el texto ya descifrado
           // txtDecode.setText(decode_text);

            if(cursor.moveToFirst()==true) {
                //capturamos los valores del cursos y lo almacenamos en variable
                String usua = cursor.getString(0);
                String pass = cursor.getString(1);

                //Desciframos
                String decode_text_pass = rsa2.Decrypt(pass);
                Toast.makeText(this,"---"+decode_text_pass,Toast.LENGTH_LONG).show();



                //preguntamos si los datos ingresados son iguales
                if (usuarioIng.equals(usua) && passIng.equals(decode_text_pass)) {
                    Toast.makeText(this,"Ingresando",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("usuario", usuarioIng);
                    startActivity(intent);
                }
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El Usuario no existe",Toast.LENGTH_LONG).show();
            //        limpiar();
        }

    }


    private void registrarUsuarios() {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);

        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_ID_CARGO,1);
        values.put(Utilidades.CAMPO_NOMBRE_CARGO,"Administrador");
        values.put(Utilidades.CAMPO_DETALLE_CARGO,"Administra la App");

        Long idResultante=db.insert(Utilidades.TABLA_CARGO,Utilidades.CAMPO_NOMBRE_CARGO,values);

        //Toast.makeText(getApplicationContext(),"Registrado correctamente: "+idResultante, Toast.LENGTH_SHORT).show();

        db.close();
    }
}

