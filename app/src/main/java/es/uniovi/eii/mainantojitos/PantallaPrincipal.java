package es.uniovi.eii.mainantojitos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.mainantojitos.modelo.Categoria;
import es.uniovi.eii.mainantojitos.modelo.Restaurante;

public class PantallaPrincipal extends AppCompatActivity {
    private RecyclerView rvPrincipal;
    private List<Restaurante> restaurantes;

    private int[] imagenes = {R.drawable.terraastur,R.drawable.mesonasturcon,R.drawable.terrasgallegas
            ,R.drawable.casa_marcial,R.drawable.casa_trabanco,R.drawable.la_salgar};
    private int[] cartas = {R.drawable.terraterra,R.drawable.mesonasturcon_carta,R.drawable.terrasgallegas_carta
            ,R.drawable.casamarcial_carta,R.drawable.casatrabanco_carta,R.drawable.lasalgar_carta};
    public static final String RESTAURANTE_SELECCIONADO ="restaurante_seleccionado";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        //Rellenamos el recyclerView con varios restaurantes
        //rellenarLista();

        if(cartas.equals(null)){
            Log.d("NULLLLLLLLLLLLLLLLLL","NULLLLLLLLLLLLLLLLLLLLLLL");
        }



        cargarRestaurantes();
        Log.d("Informacion:","Tamaño: "+restaurantes.size());
        for (int i =0;i<restaurantes.size();i++){
            Restaurante r = restaurantes.get(i);
            Log.d("Informacion:","Restaurante "+i+":"+r.toString());
        }

        //Obtengo el recyclerView donde van a estar todas los restaurantes
        rvPrincipal = (RecyclerView) findViewById(R.id.recyclerViewPrincipal);
        rvPrincipal.setHasFixedSize(true);

        //Creo un layout para el recyclerView y se lo asigno
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvPrincipal.setLayoutManager(layoutManager);

        //Añadimos la lista de los restaurantes con el adapter
        ListaRestaurantesAdapter lpAdapter= new ListaRestaurantesAdapter(restaurantes,
                new ListaRestaurantesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Restaurante restaurante) {
                        // Esta seria la parte de michu de que ocurre al pulsar sobre un
                        // restaurante
                        clickonItem(restaurante);
                        //Log.println(Log.INFO,"","Restaurante pulsado");
                    }
                });
        rvPrincipal.setAdapter(lpAdapter);
    }

    private void rellenarLista(){
        restaurantes = new ArrayList<Restaurante>();
        Categoria categoria = new Categoria("Cultural","Restaurante para comer " +
                "comida local para turistas.");

//        restaurantes.add(new Restaurante("Terra Astur","985648192",categoria,
//                "Uno de los restaurantes más visitados de Asturias",
//                "En nuestra carta se puede encontrar desde cachopo a una serie de platos " +
//                        "regionales"));
//
//
//        restaurantes.add(new Restaurante("Cal Xabu","11111111",categoria,
//                "Uno de los restaurantes más visitados de Asturias",
//                "Restaurante rural que te hara sentir como en casa."));
    }

    public void clickonItem (Restaurante restaurante){
        Log.i("Click adapter","Item Clicked "+restaurante.getNombre());
        //Toast.makeText(MainActivity.this, "Item Clicked "+user.getId(), Toast.LENGTH_LONG).show();

        //Seria la parte de MIGUEL
        //Paso el modo de apertura
        Intent intent=new Intent (PantallaPrincipal.this, ShowRestaurante.class);
//        intent.putExtra(RESTAURANTE_SELECCIONADO, restaurante);
        intent.putExtra(RESTAURANTE_SELECCIONADO, restaurante);
        //Añadimos una transicion entre las actvities con un barrido
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }



    /**
     * Lee lista de restaurantes desde el fichero csv en assets
     * Crea restaurantes como un ArrayList<Restaurante>
     */
    private void cargarRestaurantes() {
         /*si un restaurante le falta la descripcion, el fondo o el trailer, le pongo unos por defecto.
         De esta manera me aseguro estos campos en las películas*/
        /*
        String Caratula_por_defecto="https://image.tmdb.org/t/p/original/jnFCk7qGGWop2DgfnJXeKLZFuBq.jpg\n";
        String fondo_por_defecto="https://image.tmdb.org/t/p/original/xJWPZIYOEFIjZpBL7SVBGnzRYXp.jpg\n";
        String trailer_por_defecto="https://www.youtube.com/watch?v=lpEJVgysiWs\n";
        */
        Restaurante restaurante;
        restaurantes = new ArrayList<>();

        InputStream file = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;


        try {
            file = getAssets().open("lista_restaurantes_url_utf8.csv");

            reader = new InputStreamReader(file);
            bufferedReader = new BufferedReader(reader);


            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(";");
                if (data != null && data.length >= 5) {
                    if (data.length>=5) {
//                        restaurante = new Restaurante(data[0], data[1], new Categoria(data[2], "Hola"),"Por","defecto",0);
                        int aux = Integer.parseInt(data[6]);
                        restaurante = new Restaurante(data[0], data[1], data[4], data[5],imagenes[aux], cartas[aux]);
                    } else {
                        restaurante = null;
                        //restaurante = new Restaurante(data[0], data[1], new Categoria(data[2], data[3]), data[4], data[5],imagenes[ Integer.parseInt(data[6])], cartas[ Integer.parseInt(data[6])]);
                    }
                    Log.d("cargarRestaurantes", restaurante.toString());
                    restaurantes.add(restaurante);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}