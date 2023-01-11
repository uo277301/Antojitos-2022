package es.uniovi.eii.mainantojitos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.mainantojitos.db.RestaurantePojo;
import es.uniovi.eii.mainantojitos.modelo.Categoria;
import es.uniovi.eii.mainantojitos.modelo.Restaurante;

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
        email = intentRes.getParcelableExtra(PantallaPrincipal.EMAIL);

        //Rellenamos el recyclerView con varios restaurantes
        //rellenarLista();

        //Cambios Restaurantes-Sprint3
        //cargarRestaurantes();
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
                        if(restaurantesBase.size() > 100){
                            return;
                        }
                        RestaurantePojo restaurante;
                        for(QueryDocumentSnapshot query: queryDocumentSnapshots) {
                            restaurante = new RestaurantePojo();
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
                            restaurantesBase.add(restaurante);
                            Log.d("Procesados hasta ahora:",""+restaurantesBase.size());
                        }
                    }
                });
        while(!collection.isComplete() && restaurantesBase.size() < 10){

        }
    }

}