package es.uniovi.eii.mainantojitos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    private static final String EMAIL = "email";
    TextView crearCuenta;
    Button inicioiSes;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    EditText inputEmail, inputPassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog pd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        crearCuenta=findViewById(R.id.textViewCreaCuenta);
        inicioiSes=findViewById(R.id.buttonLogin);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        inputEmail = findViewById(R.id.editTextCorreo);
        inputPassword = findViewById(R.id.editTextTextContraseña);
        pd = new ProgressDialog(this);


        inicioiSes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                perforLogin();
            }
        });

        crearCuenta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                perforLogin();
                startActivity(new Intent(LoginActivity.this, RegistrarseActivity.class));
            }
        });
    }

    private void perforLogin() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if(!email.matches(emailPattern)){
            inputEmail.setError("Introduzca un correo válido");
        }
        else if(password.isEmpty() || password.length()<6){
            inputPassword.setError("Introduzca una contraseña válida");
        }
        else{
            pd.setMessage("Espere mientras accedemos a su cuenta...");
            pd.setTitle("Accediendo");
            pd.setCanceledOnTouchOutside(false);
            pd.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        pd.dismiss();
                        sendUserToNextActivity(email);
                        Toast.makeText(LoginActivity.this, "Bienvenido!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this, "Usuario no reconocido", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity(String email) {
        Intent intent = new Intent(LoginActivity.this, PantallaPrincipal.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EMAIL, email);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

}