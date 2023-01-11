package es.uniovi.eii.mainantojitos.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import es.uniovi.eii.mainantojitos.R;

public class InfoFragment2 extends Fragment {

    public static final String NOMBRE="nombre";
    public static final String TELEFONO="telefono";
    public static final String WEB="web";
    public static final String IMAGEN="imagen";
    private String web;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_info, container, false);

        //Referencias componentes
        final TextView tNombre = root.findViewById(R.id.textViewNombreRestaurante);
        final TextView tTelefono =root.findViewById(R.id.textViewTelefono);
        final Button tWeb =root.findViewById(R.id.button_web);
//        ImageView imagen = root.findViewById((R.id.imagenviewfotorestaurante));

        Bundle args=getArguments();
        if (args!=null) {
            tNombre.setText(args.getString(NOMBRE));
            tTelefono.setText(args.getString(TELEFONO));
            web = args.getString(WEB);

            // BOTON DE IR A WEB TE MANDA A LA WEB DEL RESTAURANTE
            tWeb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri url = Uri.parse(web);
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, url);
                    startActivity(launchBrowser);
                }
            });
//            Picasso.get()
//                    .load(args.getString(IMAGEN)).into(imagen);
        }


        return root;
    }

}
