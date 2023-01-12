package es.uniovi.eii.mainantojitos;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firestore.v1.WriteResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import es.uniovi.eii.mainantojitos.db.RestaurantePojo;
import es.uniovi.eii.mainantojitos.modelo.Reseña;

public class AddResenaActivity extends AppCompatActivity {

    private Button botonAñade;
    private Button botonCancelar;
    private TextView nombreRestaurante;
    private RatingBar ratingBar;
    private TextInputEditText editTextReseña;
    private ImageView fotoRest;

    private RestaurantePojo restaurante;
    private Reseña reseña;
    private String correoUsuario;
    private double puntuacionUsuario;

    FirebaseFirestore mFirestore;

    private static final String RESTAURANTE = "res";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resena);

        Intent intentRes = getIntent();
        restaurante = intentRes.getParcelableExtra(ShowRestaurante.RESTAURANTE_SELECCIONADO);
        correoUsuario = intentRes.getParcelableExtra(ShowSelectedRestaurantActivity.EMAIL_USUARIO);

        nombreRestaurante = (TextView) findViewById(R.id.textView_nombre_restaurante);
        botonCancelar = (Button) findViewById(R.id.button_cancelar);
        botonAñade = (Button) findViewById(R.id.button_añadir);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar_puntuacion);
        editTextReseña = (TextInputEditText) findViewById(R.id.textInputReseña);

        nombreRestaurante.setText(restaurante.getName());

        puntuacionUsuario = 0.0;

        mFirestore = FirebaseFirestore.getInstance();

        // FUNCIONALIDAD DEL BOTON AÑADIR
        botonAñade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                añadeReseña();
            }
        });

        // FUNCIONALIDAD DEL BOTON CANCELAR
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverPantallaRestaurante();
            }
        });

        // LISTENER PARA LA RATING BAR, EN CASO DE QUE SE CAMBIE EL VALOR DE LA BARRA SE TENDRA EN CUENTA
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                cambioRating(rating);
            }
        });

    }

    private void cambioRating(float rating) {

        this.puntuacionUsuario = rating;
    }

    /**
     * MANDA DE VUELTA A LA PANTALLA DEL RESTAURANTE
     */
    private void volverPantallaRestaurante() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Cancelar reseña");
        builder.setMessage("¿Seguro que quieres cancelar la reseña?");
        builder.setPositiveButton("Confirmar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    /**
     * Recoge de los apartados del layout lo introducido por el usuario,
     * Prepara los datos para añadirlos y los añade en el firebase
     */
    private void añadeReseña() {
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String textoReseña = editTextReseña.getText().toString();
        String idRestaurante = restaurante.getId();

//        Reseña reseñaAñadir = new Reseña(idRestaurante, email, textoReseña, rating);

        if(textoReseña.isEmpty()){
            editTextReseña.setError("Introduzca una reseña");
        }

        Map<String, String> res = new HashMap<>();
        res.put("email", email);
        res.put("id", idRestaurante);
        res.put("rating", String.valueOf(puntuacionUsuario));
        res.put("reseña", textoReseña);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Confirmar reseña");
        builder.setMessage("¿Quieres publicar esta reseña?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ProgressDialog dialog2 = ProgressDialog.show(AddResenaActivity.this, "Subiendo reseña",
                        "Cargando. Por favor espere...", true);

                Task<Void> collection =
                        mFirestore.collection("reseñas").document(UUID.randomUUID().toString()).set(res).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Reseña guardada con éxito!");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error añadiendo la reseña, por favor pruebe de nuevo...", e);
                            }
                        });

                onBackPressed();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

}