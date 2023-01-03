package es.uniovi.eii.mainantojitos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import es.uniovi.eii.mainantojitos.db.RestaurantePojo;
import es.uniovi.eii.mainantojitos.modelo.Restaurante;
import es.uniovi.eii.mainantojitos.ui.CartaFragment;
import es.uniovi.eii.mainantojitos.ui.InfoFragment2;
import es.uniovi.eii.mainantojitos.ui.ResenaFragment;

public class ShowRestaurante extends AppCompatActivity {

    private RestaurantePojo restaurante;
    ImageView imagenRestaurante;
    ImageView imagenCarta;
    TextView categoria;
    TextView nombre;
    TextView telefono;
    TextView descripcion;
    ImageView carta;
    CollapsingToolbarLayout toolBarLayout;

    InfoFragment2 firstFragment = new InfoFragment2();
    CartaFragment secondFragment = new CartaFragment();
    ResenaFragment thirdFragment = new ResenaFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_restaurante);

        //Recepción datos como activity secundaria
        Intent intentRes= getIntent();
        restaurante = intentRes.getParcelableExtra(PantallaPrincipal.RESTAURANTE_SELECCIONADO);


        // Gestión barra de la app
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detrasdefoto);
        //setSupportActionBar(toolbar);
        toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        //Gestion de la botonera
        BottomNavigationView navView = findViewById(R.id.nav_view_botonera);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (restaurante!=null) {
            toolBarLayout.setTitle("");
            //nombre.setText(restaurante.getNombre());
        }

        // Gestión de los controles que contienen los datos de restauranmte
        telefono= (TextView)findViewById(R.id.textViewTelefono);
        nombre= (TextView)findViewById(R.id.textViewNombreRestaurante);
        descripcion = (TextView) findViewById(R.id.textViewDescripcion);
        imagenCarta =(ImageView) findViewById(R.id.imageViewCartaFragment);

        int fotele = R.id.imagen_restaurante;
        Log.i("Foto","Foto: " + fotele);
        imagenRestaurante= (ImageView)findViewById(fotele);
        //categoria= (TextView) findViewById(R.id.categoria_res);

        loadFragmentInfo();

        // Si el restaurante no es null, que muestre los datos EN EL INFO FRAGMENT
        if (restaurante !=null)
            mostrarDatos(restaurante);
    }

    private void mostrarDatos(RestaurantePojo restaurante) {
        if (!restaurante.getName().isEmpty()) { //apertura en modo consulta
            //Actualizar componentes con valores de la pelicula específica
            // Imagen de fondo
            Picasso.get()
                    .load(restaurante.getImage()).into(imagenRestaurante);
//            imagenRestaurante.setImageResource(restaurante.getImage());

            InfoFragment2 info = new InfoFragment2();
            Bundle args = new Bundle();
            args.putString(InfoFragment2.NOMBRE, restaurante.getName());
            args.putString(InfoFragment2.TELEFONO, restaurante.getPhone());
            args.putString(InfoFragment2.DESCRIPCION, restaurante.getWebsite()); // NO TIENE DESCRIPCION
            info.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, info).commit();
            CartaFragment cf = new CartaFragment();
            Bundle args2 = new Bundle();
            cf.setArguments(args2);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (restaurante != null) {

                switch (item.getItemId()) {
                    case R.id.navigation_info:
                        loadFragmentInfo();
                        return true;

                    case R.id.navigation_reseñas:
                        loadFragmentReseñas();
                        return true;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }

            }
            return false;
        }
    };

    private void loadFragmentReseñas() {

        ResenaFragment info = new ResenaFragment();
        Bundle args = new Bundle();
        args.putParcelable(ResenaFragment.RESTAURANTE, restaurante);
        info.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, info);
        transaction.commit();

    }

    public void loadFragmentInfo(){
        InfoFragment2 info = new InfoFragment2();
        Bundle args = new Bundle();
        args.putString(InfoFragment2.NOMBRE, restaurante.getName());
        args.putString(InfoFragment2.TELEFONO, restaurante.getPhone());
        args.putString(InfoFragment2.IMAGEN, restaurante.getImage());
//        args.putString(InfoFragment2.DESCRIPCION, restaurante.getDescripcion());
        info.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, info);
        transaction.commit();

    }

//    public void loadFragmentCarta(Fragment fragment){
//        CartaFragment info = new CartaFragment();
//        Bundle args = new Bundle();
//        args.putInt(CartaFragment.IMAGENCARTA, restaurante.getImagenCarta());
//        args.putParcelable(CartaFragment.PRUEBA, restaurante);
//        info.setArguments(args);
////        imagenCarta.setImageResource(restaurante.getDrawImg());
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, info);
//        transaction.commit();
//    }
}