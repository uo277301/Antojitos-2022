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

public class RegistrarseActivity extends AppCompatActivity {

    TextView textViewYaHayCuenta;
    Button creaCuenta;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditText inputEmail, inputPassword, inputConfirmPassword;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        textViewYaHayCuenta=findViewById(R.id.textViewYaHayCuenta);
        creaCuenta=findViewById(R.id.buttonRegister);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        inputEmail = findViewById(R.id.editTextCorreo);
        inputPassword = findViewById(R.id.editTextTextContraseña);
        inputConfirmPassword = findViewById(R.id.editTextTextContraseñaConfimacion);
        pd = new ProgressDialog(this);

        creaCuenta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                PerforAuth();
//                startActivity(new Intent(RegistrarseActivity.this,LoginActivity.class));

            }
        });

        textViewYaHayCuenta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(RegistrarseActivity.this,LoginActivity.class));
            }
        });
    }

    private void PerforAuth() {
        // CHEQUEOS JAIME CABRON
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String passwordConfirm = inputConfirmPassword.getText().toString();

        if(!email.matches(emailPattern)){
            inputEmail.setError("Introduzca un correo válido");
        }
        else if(password.isEmpty() || password.length()<6){
            inputPassword.setError("Introduzca una contraseña válida");
        }
        else if(!password.equals(passwordConfirm)){
            inputConfirmPassword.setError("Las contraseñas no coinciden");
        }
        else{
            pd.setMessage("Registrando...");
            pd.setTitle("Reigstrando");
            pd.setCanceledOnTouchOutside(false);
            pd.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        pd.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(RegistrarseActivity.this, "Registrado con éxito", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        pd.dismiss();
                        Toast.makeText(RegistrarseActivity.this, "ERROR, vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(RegistrarseActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}