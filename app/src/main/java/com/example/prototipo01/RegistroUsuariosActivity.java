package com.example.prototipo01;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prototipo01.entidades.Cargo;
import com.example.prototipo01.utilidades.RSA;
import com.example.prototipo01.utilidades.Utilidades;

import java.util.ArrayList;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class RegistroUsuariosActivity extends AppCompatActivity {

    Spinner comboCargo;
    ArrayList<String> listaPersonas;
    ArrayList<Cargo> personasList;

    ConexionSQLiteHelper conn;
    EditText campoCedula,campoNombres,campoFechaIngreso,campoNickname, campoPassword, campoTelefono, campoMail;
    Button bfecha;
    EditText efecha;
    private  int dia,mes,ano;
    private static final String AES = "AES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registro_usuarios);

        conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null,1);

        bfecha=(Button)findViewById(R.id.bfecha);
        efecha=(EditText)findViewById(R.id.efechaIngreso);

        comboCargo= (Spinner) findViewById(R.id.comboCargo);
        //bfecha.setOnClickListener(this);

        //carga la fecha automaticamente
        Calendar calendario= Calendar.getInstance();
        dia=calendario.get(Calendar.DAY_OF_MONTH);
        mes=calendario.get(Calendar.MONTH);
        ano=calendario.get(Calendar.YEAR);
        mostrarFecha();

        campoCedula= (EditText) findViewById(R.id.campoCedula);
        campoNombres= (EditText) findViewById(R.id.campoNombres);
        campoFechaIngreso= (EditText) findViewById(R.id.efechaIngreso);
        campoNickname= (EditText) findViewById(R.id.campoNickname);
        campoPassword= (EditText) findViewById(R.id.campoPassword);
        campoTelefono= (EditText) findViewById(R.id.campoTelefono);
        campoMail= (EditText) findViewById(R.id.campoMail);

        consultarListaCargo();
        ArrayAdapter<CharSequence> adaptador=new ArrayAdapter
                (this,android.R.layout.simple_spinner_item,listaPersonas);

        comboCargo.setAdapter(adaptador);

        comboCargo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idl) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void onClickfecha(View view) {
        if(view==bfecha){
            Calendar c= Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            ano=c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    efecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                }
            }
                    ,dia,mes,ano);
            datePickerDialog.show();
        }


    }

    public void mostrarFecha(){
        efecha.setText(dia+"/"+(mes+1)+"/"+ano);
    }


    public void onClick(View view) {
        registrarUsuarios();
        //registrarUsuariosSql();
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

    private void registrarUsuarios() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);

        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_CEDULA,campoCedula.getText().toString());
        values.put(Utilidades.CAMPO_NOMBRES,campoNombres.getText().toString());
        values.put(Utilidades.CAMPO_FECHA_INGRESO,campoFechaIngreso.getText().toString());
        values.put(Utilidades.CAMPO_NICKNAME,campoNickname.getText().toString());
        values.put(Utilidades.CAMPO_PASSWORD,encriptar(campoPassword.getText().toString()));
        values.put(Utilidades.CAMPO_TELEFONO,campoTelefono.getText().toString());
        values.put(Utilidades.CAMPO_MAIL,campoMail.getText().toString());
        //values.put(Utilidades.CAMPO_ID_CARGO,"1");

        int idCombo= (int) comboCargo.getSelectedItemId();
        /**
         * Valida la seleccion del combo de dueños, si el usuario elige "seleccione" entonces
         * se retorna el id 0 ya que la palabra "seleccione" se encuentra en la pos 0 del combo,
         * sinó entonces se retorna la posicion del combo para consultar el usuario almacenado en la lista
         */
        if (idCombo!=0){
            Log.i("TAMAÑO",personasList.size()+"");
            Log.i("id combo",idCombo+"");
            Log.i("id combo - 1",(idCombo-1)+"");//se resta 1 ya que se quiere obtener la posicion de la lista, no del combo
            int idCargo=personasList.get(idCombo-1).getIdCargo();
            Log.i("id Categoria",idCargo+"");

            values.put(Utilidades.CAMPO_ID_CARGO,idCargo);

            //Long idResultante=db.insert(Utilidades.TABLA_MASCOTA,Utilidades.CAMPO_ID_MASCOTA,values);
            Long idResultante=db.insert(Utilidades.TABLA_USUARIO,Utilidades.CAMPO_NOMBRES,values);


            db.close();
            if(idResultante.toString().equals("-1")){
                Toast.makeText(getApplicationContext(),"ERROR al insertar, codigo duplicado",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Se registro correctamente: "+idResultante,Toast.LENGTH_SHORT).show();
            }
            finish();
        }else{
            Toast.makeText(getApplicationContext(),"Debe seleccionar un Cargo",Toast.LENGTH_LONG).show();
        }



    }

    //Metodo llenar spiner
    private void consultarListaCargo() {
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

            //Log.i("id",persona.getIdCargo().toString());
            Log.i("Nombre",persona.getNombreCargo());
            Log.i("Detalle",persona.getDetalleCargo());

            personasList.add(persona);

        }
        obtenerLista();
    }

    private void obtenerLista() {
        listaPersonas=new ArrayList<String>();
        listaPersonas.add("Seleccione");

        for(int i=0;i<personasList.size();i++){
            listaPersonas.add(personasList.get(i).getNombreCargo());
        }

    }

}
