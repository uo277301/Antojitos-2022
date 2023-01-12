package es.uniovi.eii.mainantojitos;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.mainantojitos.adapter.ReseñaAdapter;
import es.uniovi.eii.mainantojitos.databinding.ActivityShowSelectedRestaurantBinding;
import es.uniovi.eii.mainantojitos.db.RestaurantePojo;
import es.uniovi.eii.mainantojitos.modelo.Reseña;

public class ShowSelectedRestaurantActivity extends AppCompatActivity {

    private static final String EMAIL = "email";
    private AppBarConfiguration appBarConfiguration;
    private ActivityShowSelectedRestaurantBinding binding;
    FirebaseFirestore mFirestore;

    private RestaurantePojo restaurante;
    ImageView imagenRestaurante;
    TextView nombre;
    TextView telefono;
    RatingBar rating;
    FloatingActionButton añadeReseña;
    String web;
    CollapsingToolbarLayout toolBarLayout;
    String email;
    String latitude, longitude;

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
        restaurante = intentRes.getParcelableExtra(RESTAURANTE_SELECCIONADO);
        mFirestore = FirebaseFirestore.getInstance();

        email = intentRes.getParcelableExtra(PantallaPrincipal.EMAIL);
        toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        telefono= (TextView)findViewById(R.id.textViewTelefono);
        nombre= (TextView)findViewById(R.id.textViewNombreRestaurante);
        añadeReseña = (FloatingActionButton) findViewById(R.id.floating_button_añadir_reseña);
        imagenRestaurante= (ImageView)findViewById(R.id.imagen_restaurante_info);
        rating = (RatingBar) findViewById(R.id.ratingBar_reseña);

        // CARGAR LOS DATOS DEL RESTAURANTE Y EL RECYCLER CON LAS RESEÑAS
        Picasso.get()
                .load(restaurante.getImage()).into(imagenRestaurante);
        cargarDatosRestaurante();
        cargarResenas();

        //Obtengo el recyclerView donde van a estar todas los restaurantes
        revReseña = (RecyclerView) findViewById(R.id.recycler_tarjetas_reseñas);
        revReseña.setHasFixedSize(true);

        //Creo un layout para el recyclerView y se lo asigno
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        revReseña.setLayoutManager(layoutManager);

        //Añadimos la lista de los restaurantes con el adapter
        ReseñaAdapter lpAdapter= new ReseñaAdapter(reseñasBase,
                new ReseñaAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Reseña reseña) {
                        Log.d("Reseña pulsada", "Reseña");
                    }
                });
        revReseña.setAdapter(lpAdapter);

        // En caso de que haya teléfono, se puede pulsar para ir al dial y llamar
        if(telefono.length() > 2) {
            telefono.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialPhoneNumber(restaurante.getPhone());
                }
            });
        }

        // BOTON AÑADIR
        añadeReseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiaPestañaAñadirReseña();
            }
        });

    }

    /**
     * Cuando se pulsa el boton flotante te manda a la pantalla de añadir una reseña
     */
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


    /**
     * Almacena en cada elemento los valores del restaurante
     * Luego se prepara el boton que te manda a la web del restaurante
     */
    private void cargarDatosRestaurante() {
        final Button tWeb =findViewById(R.id.button_web);
        final Button tMap =findViewById(R.id.button_googlemaps);

        nombre.setText(restaurante.getName());
        telefono.setText(restaurante.getPhone());

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

        longitude = restaurante.getLongitude();
        latitude = restaurante.getLatitude();
        tMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:"+latitude+","+longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });
    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
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
                            if(aux!=null && aux.equals(restaurante.getId())){
                                reseña.setId(query.get("id").toString());
                                if(query.get("email")!=null)
                                    reseña.setEmail(query.get("email").toString());
                                if(query.get("rating")!=null)
                                    reseña.setRating(Float.parseFloat(query.get("rating").toString()));
                                if(query.get("reseña")!=null)
                                    reseña.setReseña(query.get("reseña").toString());
                            }
                            Log.d("RESEÑA A AÑADIR",reseña.getReseña()+" --> se tiene que añadir");
                            if(reseña.getId() != null){
                                reseñasBase.add(reseña);
                            }
                            if(reseñasBase.size() != 0) {
                                Log.d("RESEÑA AÑADIDA", reseñasBase.get(0) + " --> se añade bien");
                            }
                        }
                    }
                });
        while(!collection.isComplete()){

        }
    }
}