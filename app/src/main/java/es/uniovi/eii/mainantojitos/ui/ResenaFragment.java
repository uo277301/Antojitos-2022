package es.uniovi.eii.mainantojitos.ui;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.content.ContextWrapper;



import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.mainantojitos.AddResenaActivity;
import es.uniovi.eii.mainantojitos.ListaRestaurantesAdapter;
import es.uniovi.eii.mainantojitos.LoginActivity;
import es.uniovi.eii.mainantojitos.PantallaPrincipal;
import es.uniovi.eii.mainantojitos.R;
import es.uniovi.eii.mainantojitos.RegistrarseActivity;
import es.uniovi.eii.mainantojitos.ReseñaAdapter;
import es.uniovi.eii.mainantojitos.ShowRestaurante;
import es.uniovi.eii.mainantojitos.db.RestaurantePojo;
import es.uniovi.eii.mainantojitos.modelo.Reseña;
import es.uniovi.eii.mainantojitos.modelo.Restaurante;

public class ResenaFragment extends Fragment {


    public static final String RESTAURANTE="restaurante";
    public static final String RESEÑAS="reseñas";
    public static final String ID="id";

    private RestaurantePojo restaurante;
    Button addResena;

    FirebaseFirestore mFirestore;
    private List<Reseña> reseñasBase = new ArrayList<>();

    private RecyclerView revReseña;

    public ResenaFragment() {
        // Required empty public constructor
    }

//    public static ResenaFragment newInstance(String param1, String param2) {
//        ResenaFragment fragment = new ResenaFragment();
//        Bundle args = new Bundle();
//        args.putString(RESTAURANTE, restaurante);
//        fragment.setArguments(args);
//        return fragment;
//    }
    

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.recylcer_view_resena, container, false);
        Bundle args=getArguments();

        revReseña = (RecyclerView) root.findViewById(R.layout.recylcer_view_resena);
        reseñasBase = new ArrayList<>();
        revReseña.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext()));

//        addResena = root.findViewById(R.id.floatingAnadirResena); BOTON AÑADIR RESEÑA
        mFirestore = FirebaseFirestore.getInstance();

        cargarResenas(); // las carga desde el firebase
        // MIRAR MATERIAL.IO TIENE ICONOS

        ReseñaAdapter adapter = new ReseñaAdapter(reseñasBase);
        revReseña.setAdapter(adapter);

        addResena.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                creaResena();
            }
        });

        return root;
    }

    /**
     * 1- abre una nueva ventana/pestaña
     * 2- en la ventana nueva, que el usuario ponga:
     *      - rating en estrellas
     *      - comentario
     * 3- guardar estos datos en firestore
     * 4- volver a enseñar el fragment con las reseñas
     */
    private void creaResena() {
//        Intent intent = new Intent(ShowRestaurante.class, AddResenaActivity.class);
//        intent.putExtra(RESTAURANTE, restaurante);
//        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
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

    /**
     * AQUI SE METE LO DEL RECYCLKER Y EL ADAPTER PA QUE SALGAN LAS RESEÑAS
     */
    private void enseñaReseña() {
//        revReseña = (RecyclerView) findViewById(R.id.recyclerReseña);
//        revReseña.setHasFixedSize(true);
//
//        //Creo un layout para el recyclerView y se lo asigno
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        revReseña.setLayoutManager(layoutManager);
//
//        //Añadimos la lista de los restaurantes con el adapter
//        ReseñaAdapter lpAdapter= new ReseñaAdapter(reseñasBase,
//                new ReseñaAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(RestaurantePojo item) {
//                        // si pulsas en la reseña no ocurre nada
//                    }
//
//                });
//        revReseña.setAdapter(lpAdapter);
    }
}
