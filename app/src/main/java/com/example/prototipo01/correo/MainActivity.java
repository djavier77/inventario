package com.example.prototipo01.correo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

//import android.support.v7.app.AppCompatActivity;
import com.example.prototipo01.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;

    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
       // editTextSubject=(EditText)findViewById(R.id.editTextSubject);
        editTextMessage=(EditText)findViewById(R.id.editTextMessage);

        buttonSend=(Button)findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(this);
    }

    private void sendEmail(){
        String email=editTextEmail.getText().toString().trim();
       // String subject=editTextSubject.getText().toString().trim();
        //************************se coloca de forma predeterminada el asunto, en este caso Stock Bajo
        String subject="Stock bajo";
        String message=editTextMessage.getText().toString().trim();

        SendMail sm=new SendMail(this,email,subject,message);

        sm.execute();
    }

    @Override
    public void onClick(View view) {
        sendEmail();
    }
}
