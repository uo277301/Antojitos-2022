package es.uniovi.eii.mainantojitos.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.RecoverySystem;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.mainantojitos.R;
import es.uniovi.eii.mainantojitos.modelo.Restaurante;

public class CartaFragment extends Fragment {
    public static final String PRODUCTO="producto";
    public static final String NOMBRE="Nombre";
    public static final String IMAGENCARTA="imagencarta";
    public static final String PRUEBA="prueba";
    private Restaurante res;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_carta, container, false);
        final ImageView imagen = root.findViewById((R.id.imageViewCartaFragment));

        Bundle args=getArguments();
        res = args.getParcelable(PRUEBA);
        if (args!=null) {
//            imagen.setImageResource(args.getInt(IMAGENCARTA));
            //Picasso.get().load(args.getInt(IMAGENCARTA)).into(imagen);
            //imagen.setImageResource(args.getInt(IMAGENCARTA));
            imagen.setImageResource(res.getImagenCarta());
        }

        return root;
    }

}
