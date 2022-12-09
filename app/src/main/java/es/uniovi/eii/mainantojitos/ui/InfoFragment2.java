package es.uniovi.eii.mainantojitos.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import es.uniovi.eii.mainantojitos.R;

public class InfoFragment2 extends Fragment {

    public static final String NOMBRE="nombre";
    public static final String TELEFONO="telefono";
    public static final String DESCRIPCION="descripcion";
    public static final String IMAGEN="imagen";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_info, container, false);

        //Referencias componentes
        final TextView tNombre = root.findViewById(R.id.textViewNombreRestaurante);
        final TextView tDescripcion = root.findViewById(R.id.textViewDescripcion);
        final TextView tTelefono =root.findViewById(R.id.textViewTelefono);
//        ImageView imagen = root.findViewById((R.id.imagenviewfotorestaurante));

        Bundle args=getArguments();
        if (args!=null) {
            tNombre.setText(args.getString(NOMBRE));
            tTelefono.setText(args.getString(TELEFONO));
            tDescripcion.setText((args.getString(DESCRIPCION)));
//            Picasso.get()
//                    .load(args.getString(IMAGEN)).into(imagen);
        }

        return root;
    }
}
