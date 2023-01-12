package es.uniovi.eii.mainantojitos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.mainantojitos.adapter.ListaRestaurantesAdapter;
import es.uniovi.eii.mainantojitos.db.RestaurantePojo;

public class PantallaPrincipal extends AppCompatActivity {
    private RecyclerView rvPrincipal;
    private int[] imagenes = {R.drawable.terraastur,R.drawable.mesonasturcon,R.drawable.terrasgallegas
            ,R.drawable.casa_marcial,R.drawable.casa_trabanco,R.drawable.la_salgar};
    private int[] cartas = {R.drawable.terraterra,R.drawable.mesonasturcon_carta,R.drawable.terrasgallegas_carta
            ,R.drawable.casamarcial_carta,R.drawable.casatrabanco_carta,R.drawable.casamarcial_carta};
    public static final String RESTAURANTE_SELECCIONADO ="restaurante_seleccionado";
    public static final String EMAIL ="email";


    //Cambios Restaurantes-Sprint3
    FirebaseFirestore mFirestore;
    private List<RestaurantePojo> restaurantesBase = new ArrayList<>();
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        Intent intentRes= getIntent();
        email = intentRes.getParcelableExtra(LoginActivity.EMAIL);

        //Rellenamos el recyclerView con varios restaurantes
        //rellenarLista();

        //Cambios Restaurantes-Sprint3
        //cargarRestaurantes();

        initFiltros();
        mFirestore = FirebaseFirestore.getInstance();
        cargarRestaurantesFirebase();

        Log.d("Informacion:","Tamaño: "+restaurantesBase.size());
        for (int i =0;i<restaurantesBase.size();i++){
            RestaurantePojo r = restaurantesBase.get(i);
            Log.d("Informacion:","Restaurante "+i+":"+r.toString());
        }

        //Obtengo el recyclerView donde van a estar todas los restaurantes
        rvPrincipal = (RecyclerView) findViewById(R.id.recyclerViewPrincipal);
        rvPrincipal.setHasFixedSize(true);

        //Creo un layout para el recyclerView y se lo asigno
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvPrincipal.setLayoutManager(layoutManager);

        //Añadimos la lista de los restaurantes con el adapter
        ListaRestaurantesAdapter lpAdapter= new ListaRestaurantesAdapter(restaurantesBase,
                new ListaRestaurantesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RestaurantePojo restaurante) {
                        // Esta seria la parte de michu de que ocurre al pulsar sobre un
                        // restaurante
                        clickonItem(restaurante);
                        //Log.println(Log.INFO,"","Restaurante pulsado");
                    }
                });
        rvPrincipal.setAdapter(lpAdapter);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent (PantallaPrincipal.this, LoginActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickonItem (RestaurantePojo restaurante){
        Log.i("Click adapter","Item Clicked "+restaurante.getName());
        //Toast.makeText(MainActivity.this, "Item Clicked "+user.getId(), Toast.LENGTH_LONG).show();

        //Paso el modo de apertura
        Intent intent=new Intent (PantallaPrincipal.this, ShowSelectedRestaurantActivity.class);
//        intent.putExtra(RESTAURANTE_SELECCIONADO, restaurante);
        intent.putExtra(RESTAURANTE_SELECCIONADO, restaurante);
        intent.putExtra(EMAIL, email);
        //Añadimos una transicion entre las actvities con un barrido
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }



    /**
     * Lee lista de restaurantes desde el fichero csv en assets
     * Crea restaurantes como un ArrayList<Restaurante>
     */
    //Cambios Restaurantes-Sprint3
    private void cargarRestaurantesFirebase(){

        Task<QuerySnapshot> collection =
                mFirestore.collection("restaurantes").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>(){
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        RestaurantePojo restaurante;
                        for(QueryDocumentSnapshot query: queryDocumentSnapshots) {
                            restaurante = new RestaurantePojo();
                            ArrayList<String> res= (ArrayList<String>) query.get("cuisine");
                            String[] array=new String[res.size()];
                            array=res.toArray(array);

//                            System.out.println(res);
                            if(query.get("id")!=null)
                                restaurante.setId(query.get("id").toString());
                            if(query.get("name")!=null)
                                restaurante.setName(query.get("name").toString());
                            if(query.get("address")!=null)
                                restaurante.setAddress(query.get("address").toString());
                            if(query.get("email")!=null)
                                restaurante.setEmail(query.get("email").toString());
                            if(query.get("image")!=null)
                                restaurante.setImage(query.get("image").toString());
                            if(query.get("latitude")!=null)
                                restaurante.setLatitude(query.get("latitude").toString());
                            if(query.get("longitude")!=null)
                                restaurante.setLongitude(query.get("longitude").toString());
                            if(query.get("phone")!=null)
                                restaurante.setPhone(query.get("phone").toString());
                            if(query.get("priceLevel")!=null)
                                restaurante.setPriceLevel(query.get("priceLevel").toString());
                            if(query.get("rating")!=null)
                                restaurante.setRating(Float.parseFloat(query.get("rating").toString()));
                            if(query.get("webUrl")!=null)
                                restaurante.setWebUrl(query.get("webUrl").toString());
                            if(query.get("website")!=null)
                                restaurante.setWebsite(query.get("website").toString());
                            if(query.get("cuisine")!=null)
                                restaurante.setCuisine(array);

                            restaurantesBase.add(restaurante);
                            Log.d("Procesados hasta ahora:",""+restaurantesBase.size());
                        }
                    }
                });
        while(!collection.isComplete() && restaurantesBase.size()<151){

        }


    }

    private void initFiltros(){
        EditText seleccionFiltro=findViewById(R.id.editTextSeleccionaFiltro);
        Button botonFiltrar=findViewById(R.id.buttonFiltrar);

        seleccionFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PantallaPrincipal.this);
                builder.setTitle("Selecciona la opcion de filtrado")
                        .setItems(R.array.Filtros, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //empieza en 0;
                                if(which==0){
                                    seleccionFiltro.setText("Restaurante de carnes");
//                                    filtrarCocinaCarnes();
                                }else if(which==1){
                                    seleccionFiltro.setText("Cocina Mediterránea");
//                                    filtrarCocinaMediterranea();
                                }else if(which==2){
                                    seleccionFiltro.setText("Cocina Italiana");
//                                    filtrarCocinaItaliana();
                                }else if(which==3){
                                    seleccionFiltro.setText("Vinoteca");
//                                    filtrarCocinaJaponesa();
                                }else if(which==4){
                                    seleccionFiltro.setText("Cocina Mexicana");
//                                    filtrarCocinaMexicana();
                                }else if(which==5){
                                    seleccionFiltro.setText("Por precio (descendente)");
//                                    filtrarValoracion();
                                }else if(which==6){
                                    seleccionFiltro.setText("Por precio (ascendente)");
//                                  filtrarOpcionesVegetarianas();
                                }else if(which==7){
                                    seleccionFiltro.setText("Opciones vegetarianas");
                                }else
                                    seleccionFiltro.setText("Selecciona la opcion de filtrado");

                            }
                        });

                builder.show();

            }
        });
        botonFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seleccionFiltro.getText().toString().equals("Restaurante de carnes")){
                    filtrarCocinaCarnes();
                }else if (seleccionFiltro.getText().toString().equals("Cocina Mediterránea")){
                    filtrarCocinaMediterranea();
                }else if(seleccionFiltro.getText().toString().equals("Cocina Italiana")){
                    filtrarCocinaItaliana();
                }else if(seleccionFiltro.getText().toString().equals("Vinoteca")){
                    filtrarVinoteca();
                }else if(seleccionFiltro.getText().toString().equals("Cocina Mexicana")){
                    filtrarCocinaMexicana();
                }else if(seleccionFiltro.getText().toString().equals("Por precio (descendente)")){
                    filtrarPrecioDescendente();
                }else if(seleccionFiltro.getText().toString().equals("Opciones vegetarianas")){
                    filtrarOpcionesVegetarianas();
                }else if(seleccionFiltro.getText().toString().equals("Por precio (ascendente)"))
                    filtrarPrecioAscendente();
                else{
                    eliminarFiltros();
                }

            }
        });
    }

    private void filtrarCocinaCarnes(){
        List<RestaurantePojo> filtrados = new ArrayList<>();
        for(RestaurantePojo res: restaurantesBase){
            for(String cocina:res.getCuisine()){
                if(cocina.equals("Restaurante de carne")){
                    filtrados.add(res);
                }
            }
        }
        actualizarPantalla(filtrados);
    }
    private void filtrarCocinaMediterranea(){
        List<RestaurantePojo> filtrados = new ArrayList<>();
        for(RestaurantePojo res: restaurantesBase){
            for(String cocina:res.getCuisine()){
                if(cocina.equals("Mediterránea") || cocina.equals("Marisco")){
                    filtrados.add(res);
                }
            }
        }
        actualizarPantalla(filtrados);
    }
    private void filtrarCocinaItaliana(){
        List<RestaurantePojo> filtrados = new ArrayList<>();
        for(RestaurantePojo res: restaurantesBase){
            for(String cocina:res.getCuisine()){
                if(cocina.equals("Italiana")){
                    filtrados.add(res);
                }
            }
        }
        actualizarPantalla(filtrados);
    }
    private void filtrarVinoteca(){
        List<RestaurantePojo> filtrados = new ArrayList<>();
        for(RestaurantePojo res: restaurantesBase){
            for(String cocina:res.getCuisine()){
                if(cocina.equals("Vinoteca")|| cocina.equals("Bar")){
                    filtrados.add(res);
                }
            }
        }
        actualizarPantalla(filtrados);
    }
    private void filtrarCocinaMexicana(){
        List<RestaurantePojo> filtrados = new ArrayList<>();
        for(RestaurantePojo res: restaurantesBase){
            for(String cocina:res.getCuisine()){
                if(cocina.equals("Mexicana")|| cocina.equals("Latina")){
                    filtrados.add(res);
                }
            }
        }
        actualizarPantalla(filtrados);
    }


    private void filtrarPrecioAscendente(){
        List<RestaurantePojo> filtrados=new ArrayList<>();
        String[] prices = {"€","€ - €€", "€€ - €€€","€€€ - €€€€","€€€€"};
        for(String price:prices){
            for(RestaurantePojo rest: restaurantesBase){
                if(rest.getPriceLevel()!=null && rest.getPriceLevel().equals(price)){
                    filtrados.add(rest);
                }
            }
        }
        actualizarPantalla(filtrados);
    }

    private void filtrarPrecioDescendente(){
        List<RestaurantePojo> filtrados=new ArrayList<>();
        String[] prices = { "€€€€","€€€ - €€€€","€€ - €€€","€ - €€","€"};
        for(String price:prices){
            for(RestaurantePojo rest: restaurantesBase){
                if(rest.getPriceLevel()!=null && rest.getPriceLevel().equals(price)){
                    filtrados.add(rest);
                }
            }
        }
        actualizarPantalla(filtrados);
    }
    private void filtrarOpcionesVegetarianas(){
        List<RestaurantePojo> filtrados = new ArrayList<>();
        for(RestaurantePojo res: restaurantesBase){
            for(String cocina:res.getCuisine()){
                if(cocina.equals("Opciones vegetarianas")){
                    filtrados.add(res);
                }
            }
        }
        actualizarPantalla(filtrados);
    }
    private void eliminarFiltros(){
        actualizarPantalla(restaurantesBase);
    }

    private void actualizarPantalla(List<RestaurantePojo> restaurantes) {
        ListaRestaurantesAdapter lpAdapter= new ListaRestaurantesAdapter(restaurantes,
                new ListaRestaurantesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RestaurantePojo restaurante) {
                        // Esta seria la parte de michu de que ocurre al pulsar sobre un
                        // restaurante
                        clickonItem(restaurante);
                        //Log.println(Log.INFO,"","Restaurante pulsado");
                    }
                });
        rvPrincipal.setAdapter(lpAdapter);
    }
}