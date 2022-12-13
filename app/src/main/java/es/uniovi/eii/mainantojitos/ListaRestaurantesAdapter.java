package es.uniovi.eii.mainantojitos;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import es.uniovi.eii.mainantojitos.db.RestaurantePojo;

public class ListaRestaurantesAdapter extends RecyclerView.Adapter<ListaRestaurantesAdapter.RestauranteViewHolder>{


    public interface OnItemClickListener{
        void onItemClick(RestaurantePojo item);
    }
    private List<RestaurantePojo> restauranteList;
    private final OnItemClickListener listener;


    public ListaRestaurantesAdapter(List<RestaurantePojo> restauranteList, OnItemClickListener listener) {
        this.restauranteList = restauranteList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RestauranteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creamos la vista con el layout para un elemento
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linea_recycler_view_restaurant, parent, false);
        return new RestauranteViewHolder(itemView);
    }


    /** Asocia el contenido a los componentes de la vista,
     * concretamente con nuestro PeliculaViewHolder que recibimos como parámetro
     */
    @Override
    public void onBindViewHolder(@NonNull RestauranteViewHolder holder, int position) {
        // Extrae de la lista el elemento indicado por posición
        RestaurantePojo restaurante= restauranteList.get(position);
        Log.i("Lista","Visualiza elemento: "+restaurante);
        // llama al método de nuestro holder para asignar valores a los componentes
        // además, pasamos el listener del evento onClick
        holder.bindUser(restaurante, listener);
    }

    @Override
    public int getItemCount() {
        return restauranteList.size();
    }

    public static class RestauranteViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreRestaurante;
        private TextView direccion;
        private ImageView imagen;
        private ImageView imagenCarta;
        private RatingBar rating;


        public RestauranteViewHolder(View itemView) {
            super(itemView);

            nombreRestaurante= (TextView)itemView.findViewById(R.id.txt_nombre_usuario);
            direccion= (TextView)itemView.findViewById(R.id.txt_direccion_restaurante);
            imagen= (ImageView)itemView.findViewById(R.id.img_restaurante);
            rating=(RatingBar)itemView.findViewById(R.id.ratingBar_reseña);
//            imagenCarta= (ImageView)itemView.findViewById(R.id.imageViewCartaFragment);

        }

        // asignar valores a los componentes
        public void bindUser(final RestaurantePojo restaurante, final OnItemClickListener listener) {
            nombreRestaurante.setText(restaurante.getName());
            //cambiar a getDireccion
            direccion.setText(restaurante.getAddress());
            //Lo mismo con rating
            //descripcion.setText(restaurante.getDescripcion());
            if(restaurante.getImage()!=null || restaurante.getImage()==""){
                Picasso.get()
                        .load(restaurante.getImage()).into(imagen);
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Log.i("Hola", "Hola");
                    listener.onItemClick(restaurante);
                }
            });

        }
    }


}
