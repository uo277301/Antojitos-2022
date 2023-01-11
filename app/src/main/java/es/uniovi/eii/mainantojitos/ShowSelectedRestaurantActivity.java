package es.uniovi.eii.mainantojitos;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.mainantojitos.databinding.ActivityShowSelectedRestaurantBinding;
import es.uniovi.eii.mainantojitos.db.RestaurantePojo;
import es.uniovi.eii.mainantojitos.modelo.Reseña;
import es.uniovi.eii.mainantojitos.ui.CartaFragment;
import es.uniovi.eii.mainantojitos.ui.InfoFragment2;
import es.uniovi.eii.mainantojitos.ui.ResenaFragment;

public class ShowSelectedRestaurantActivity extends AppCompatActivity {

    private static final String EMAIL = "email";
    private AppBarConfiguration appBarConfiguration;
    private ActivityShowSelectedRestaurantBinding binding;
    FirebaseFirestore mFirestore;

    private RestaurantePojo restaurante;
    ImageView imagenRestaurante;
    TextView nombre;
    TextView telefono;
    Button añadeReseña;
    String web;
    CollapsingToolbarLayout toolBarLayout;
    String email;

    private List<RestaurantePojo> restaurantesBase = new ArrayList<>();
    private List<Reseña> reseñasBase = new ArrayList<>();

    private RecyclerView revReseña;

    public static final String RESTAURANTE_SELECCIONADO ="restaurante_seleccionado";
    public static final String EMAIL_USUARIO ="email_usuario";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_selected_restaurant);

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_show_selected_restaurant);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // INICIALIZACION
        Intent intentRes= getIntent();
        restaurante = intentRes.getParcelableExtra(PantallaPrincipal.RESTAURANTE_SELECCIONADO);
        email = intentRes.getParcelableExtra(PantallaPrincipal.EMAIL);
        toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        telefono= (TextView)findViewById(R.id.textViewTelefono);
        nombre= (TextView)findViewById(R.id.textViewNombreRestaurante);
        añadeReseña = (Button) findViewById(R.id.floating_button_añadir_reseña);
        mFirestore = FirebaseFirestore.getInstance();

        int fotoRestaurante = R.id.imagen_restaurante;
        Log.i("Foto","Foto: " + fotoRestaurante);
        imagenRestaurante= (ImageView)findViewById(fotoRestaurante);

        // CARGAR LOS DATOS DEL RESTAURANTE Y EL RECYCLER CON LAS RESEÑAS
        cargarDatosRestaurante();
        cargarResenas();
        ReseñaAdapter adapter = new ReseñaAdapter(reseñasBase);
        revReseña.setAdapter(adapter);

        // BOTON AÑADIR
        añadeReseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiaPestañaAñadirReseña();
            }
        });

    }

    private void cambiaPestañaAñadirReseña() {
        Intent intent = new Intent(ShowSelectedRestaurantActivity.this, AddResenaActivity.class);
        intent.putExtra(RESTAURANTE_SELECCIONADO, restaurante);
        intent.putExtra(EMAIL, email);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_show_selected_restaurant);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
        return true;
    }


    private void cargarDatosRestaurante() {
        final TextView tNombre = findViewById(R.id.textViewNombreRestaurante);
        final TextView tTelefono =findViewById(R.id.textViewTelefono);
        final Button tWeb =findViewById(R.id.button_web);

        tNombre.setText(restaurante.getName());
        tTelefono.setText(restaurante.getPhone());

        // FUNCIONALIDAD BOTON DE IR A LA WEB
        web = restaurante.getWebUrl();
        tWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri url = Uri.parse(web);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, url);
                startActivity(launchBrowser);
            }
        });
    }

    /**
     * 1- abrir las reseñas del firestore
     * 2- crea un objeto reseña para cada reseña que haya en la base
     * 3- almacena las reseñas en el listado de reseñas para ser mostradas con el adapter
     */
    private void cargarResenas() {

        Task<QuerySnapshot> collection =
                mFirestore.collection("reseñas").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>(){
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Reseña reseña;
                        for(QueryDocumentSnapshot query: queryDocumentSnapshots) {
                            reseña = new Reseña();

                            // Comprobacion de que se enseñen solo las reseñas del restaurante que queremos
                            String aux = query.get("id").toString();
                            if(aux!=null && aux == restaurante.getId()){
                                reseña.setId(query.get("id").toString());
                                if(query.get("email")!=null)
                                    reseña.setEmail(query.get("email").toString());
                                if(query.get("rating")!=null)
                                    reseña.setRating(Float.parseFloat(query.get("rating").toString()));
                                if(query.get("reseña")!=null)
                                    reseña.setReseña(query.get("reviews").toString());
                            }

                            reseñasBase.add(reseña);
                        }
                    }
                });
        while(!collection.isComplete()){

        }
    }
}