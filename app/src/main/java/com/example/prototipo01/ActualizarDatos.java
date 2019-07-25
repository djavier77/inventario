package com.example.prototipo01;

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
import com.example.prototipo01.entidades.Usuarios;
import com.example.prototipo01.utilidades.RSA;
import com.example.prototipo01.utilidades.Utilidades;

import java.util.ArrayList;

public class ActualizarDatos extends AppCompatActivity {
    EditText campoCedula, campoNombres, campoFechaIngreso, campoNickname, campoClave, campoTelefono, campoMail, campoCargo;

    //Cargar combo box
    Spinner comboCategoria;
    //TextView txtTipo,txtDocumento,txtTelefono;
    ArrayList<String> listaTipo;
    ArrayList<String> listaTipo2;

    ArrayList<Cargo> personasList;
    ArrayList<String> listaLogin;
    ArrayList<Usuarios> loginList;

    Integer tipo=1;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_actualizar_datos);
        conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null,1);


        campoCedula= (EditText) findViewById(R.id.campoCedula);
        campoNombres= (EditText) findViewById(R.id.campoNombres);
        campoNickname= (EditText) findViewById(R.id.campoNickname);
        campoClave= (EditText) findViewById(R.id.campoClave);
        campoFechaIngreso= (EditText) findViewById(R.id.campoFechaIngreso);
        campoTelefono= (EditText) findViewById(R.id.campoTelefono);
        campoMail= (EditText) findViewById(R.id.campoMail);


        comboCategoria= (Spinner) findViewById(R.id.comboCategoria);
        consultarComboTipo();




        ArrayAdapter<CharSequence> adaptador=new ArrayAdapter
                (this,android.R.layout.simple_spinner_item, listaTipo);

        comboCategoria.setAdapter(adaptador);

        comboCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idl) {

                if (position!=0){
                    tipo = personasList.get(position-1).getIdCargo();
                }else{

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        obtnerdatos();
    }

    public void onClick(View view) {
        registrarUsuarios();
    }

    private void registrarUsuariosSql() {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);

        SQLiteDatabase db=conn.getWritableDatabase();
        db.close();
    }

    private void registrarUsuarios() {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        String cedulab = campoCedula.getText().toString();
        String nombreb = campoNombres.getText().toString();
        String userb = campoNickname.getText().toString();
        String passb = encriptar(campoClave.getText().toString());//campoClave.getText().toString();
        String fechaIngresob = campoFechaIngreso.getText().toString();
        String telefonob = campoTelefono.getText().toString();
        String mailb = campoMail.getText().toString();
        //variable para asignar el tipo de usuario = tipo

        String update="UPDATE "+ Utilidades.TABLA_USUARIO+" SET cedula='"+cedulab+"',nombres='"+nombreb+"',fecha_ingreso='"+fechaIngresob+"',nickname='"+userb+"',password='"+passb+"',telefono='"+telefonob+"',mail='"+mailb+"'  WHERE cedula='"+cedulab+"'";
        db.execSQL(update);
        db.close();
        Toast.makeText(getApplicationContext(),"Datos Actualizados ", Toast.LENGTH_SHORT).show();
        db.close();
        finish();
    }

        ///Para cargar el combobox
    private void consultarComboTipo() {
        SQLiteDatabase db=conn.getReadableDatabase();

        Cargo persona=null;
        personasList =new ArrayList<Cargo>();
        //select * from usuarios
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_CARGO,null);

        while (cursor.moveToNext()){
            persona=new Cargo();
            persona.setIdCargo(cursor.getInt(0));
            persona.setNombreCargo(cursor.getString(1));
            persona.setDetalleCargo(cursor.getString(2));

            Log.i("id",persona.getIdCargo().toString());
            Log.i("Nombre",persona.getNombreCargo());
            Log.i("Detalle",persona.getDetalleCargo());

            personasList.add(persona);

        }
        obtenerLista();
    }


    private void obtenerLista() {
        listaTipo =new ArrayList<String>();
        listaTipo.add("Seleccione");

        for(int i=0;i<personasList.size();i++){
            listaTipo.add(personasList.get(i).getNombreCargo());
        }
    }


    private void obtnerdatos(){


        SQLiteDatabase db=conn.getReadableDatabase();

        Usuarios tipo=null;
        loginList =new ArrayList<Usuarios>();
        //select * from usuarios
        Usuarios use = new Usuarios();
        String userLogeado = getIntent().getStringExtra("usuarioLogeado");
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_USUARIO+" WHERE nickname=='"+userLogeado+"'",null);

        while (cursor.moveToNext()){
            tipo=new Usuarios();
            tipo.setCedula(cursor.getString(1));
            tipo.setNombres(cursor.getString(2));
            tipo.setFecha_ingreso(cursor.getString(3));
            tipo.setNickname(cursor.getString(4));
            tipo.setPassword(cursor.getString(5));
            tipo.setTelefono(cursor.getString(6));
            tipo.setMail(cursor.getString(7));

            Log.i("Cedula",tipo.getCedula());
            Log.i("Nombre",tipo.getNombres());
            Log.i("Fecha ingreso",tipo.getFecha_ingreso());
            Log.i("Nickname",tipo.getNickname());
            Log.i("Password",tipo.getPassword());
            Log.i("Telefono",tipo.getTelefono());
            Log.i("Mail",tipo.getMail());

            loginList.add(tipo);
        }
        obtenerUsuarios();
    }


    private void obtenerUsuarios() {
        listaTipo =new ArrayList<String>();
        //listaTipo.add("Seleccione");

        campoCedula.setText(loginList.get(0).getCedula());
        campoNombres.setText(loginList.get(0).getNombres());
        campoFechaIngreso.setText(loginList.get(0).getFecha_ingreso());
        campoNickname.setText(loginList.get(0).getNickname());
        //campoClave.setText("****");
        campoTelefono.setText(loginList.get(0).getTelefono());
        campoMail.setText(loginList.get(0).getMail());

        /*for(int i=0;i<loginList.size();i++){
            listaTipo.add(tipoList.get(i).getId_tipo()+" - "+tipoList.get(i).getDescripcion_tipo());
        }*/
    }

    private String encriptar(String pass){
        String encode_text=null;
        try {

            //Obtenemos el texto desde el cuadro de texto

            String original = pass;

            RSA rsa = new RSA();

            //le asignamos el Contexto
            rsa.setContext(getBaseContext());

            //Generamos un juego de claves
            rsa.genKeyPair(1024);

            //Guardamos en la memoria las claves
            rsa.saveToDiskPrivateKey("rsa"+campoNickname.getText().toString()+".pri");
            rsa.saveToDiskPublicKey("rsa"+campoNickname.getText().toString()+".pub");

            //Ciframos
            encode_text = rsa.Encrypt(original);

            //Mostramos el texto cifrado
            //txtEncode.setText(encode_text);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Error al encriptar la clave",Toast.LENGTH_LONG).show();
        }

        return encode_text;

    }
}
