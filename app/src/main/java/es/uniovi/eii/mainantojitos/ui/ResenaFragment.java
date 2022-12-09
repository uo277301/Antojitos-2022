package es.uniovi.eii.mainantojitos.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import es.uniovi.eii.mainantojitos.LoginActivity;
import es.uniovi.eii.mainantojitos.R;
import es.uniovi.eii.mainantojitos.RegistrarseActivity;
import es.uniovi.eii.mainantojitos.modelo.Restaurante;

public class ResenaFragment extends Fragment {


    public static final String RESTAURANTE="restaurante";
    private Restaurante restaurante;
    Button addResena;

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
        addResena = root.findViewById(R.id.floatingAnadirResena);

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
    }
}