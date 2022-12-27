package es.uniovi.eii.mainantojitos.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.mainantojitos.LoginActivity;
import es.uniovi.eii.mainantojitos.R;
import es.uniovi.eii.mainantojitos.RegistrarseActivity;
import es.uniovi.eii.mainantojitos.db.RestaurantePojo;
import es.uniovi.eii.mainantojitos.modelo.Restaurante;

public class ResenaFragment extends Fragment {


    public static final String RESTAURANTE="restaurante";
    public static final String RESEÑAS="reseñas";

    private RestaurantePojo restaurante;
    Button addResena;

    FirebaseFirestore mFirestore;
    private List<RestaurantePojo> restaurantesBase = new ArrayList<>();

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
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_resena, container, false);
        Bundle args=getArguments();
        restaurante = args.getParcelable(RESTAURANTE);
        //addResena = root.findViewById(R.id.floatingAnadirResena); BOTON AÑADIR RESEÑA
        mFirestore = FirebaseFirestore.getInstance();

        cargarResenas(); // las carga desde el firebase
        enseñaReseña(); // enseñarlas (esto se hace asi para no tener dos veces lo mismo
                        // al crear y cuando el usuario meta una reseña.
        // MIRAR MATERIAL.IO TIENE ICONOS

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
    }

    /**
     * 1- abrir las reseñas del firestore
     * 2- almacenarlas
     */
    private void cargarResenas() {

        Task<QuerySnapshot> collection =
                mFirestore.collection("reseñas").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>(){
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        RestaurantePojo restaurante;
                        for(QueryDocumentSnapshot query: queryDocumentSnapshots) {
                            restaurante = new RestaurantePojo();
                            if(query.get("id")!=null)
                                restaurante.setId(query.get("id").toString());
                            if(query.get("email")!=null)
                                restaurante.setName(query.get("email").toString());
                            if(query.get("rating")!=null)
                                restaurante.setRating(Float.parseFloat(query.get("rating").toString()));
                            if(query.get("reviews")!=null)
                                restaurante.setReviews((String[]) query.get("reviews"));
                            if(query.get("reviewsCount")!=null)
                                restaurante.setNumberOfReviews(Integer.parseInt(query.get("reviewsCount").toString()));
                        }
                        enseñaReseña();
                    }
                });
        while(!collection.isComplete()){

        }
    }

    /**
     * 1- cargar las cardview
     * 2- meter las reseñas en las cardview
     */
    private void enseñaReseña() {

    }
}
