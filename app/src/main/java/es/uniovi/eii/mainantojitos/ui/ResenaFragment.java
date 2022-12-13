package es.uniovi.eii.mainantojitos.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

        cargarResenas();

        addResena.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                creaResena();
            }
        });

        return root;
    }

    // Crear una ventana donde se añadan las reseñas en la base y aparezcan luego
    private void creaResena() {
    }

    // Cargar las reseñas desde el firebase
    private void cargarResenas() {

        Task<QuerySnapshot> collection =
                mFirestore.collection("restaurantes").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>(){
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        RestaurantePojo restaurante;
                        for(QueryDocumentSnapshot query: queryDocumentSnapshots) {
                            restaurante = new RestaurantePojo();
                            if(query.get("id")!=null)
                                restaurante.setId(query.get("id").toString());
                            if(query.get("name")!=null)
                                restaurante.setName(query.get("name").toString());
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

    private void enseñaReseña() {

    }
}
