package es.uniovi.eii.mainantojitos;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.uniovi.eii.mainantojitos.modelo.Restaurante;

public class ListaRestaurantesAdapter extends RecyclerView.Adapter<ListaRestaurantesAdapter.RestauranteViewHolder>{


    public interface OnItemClickListener{
        void onItemClick(Restaurante item);
    }
    private List<Restaurante> restauranteList;
    private final OnItemClickListener listener;


    public ListaRestaurantesAdapter(List<Restaurante> restauranteList, OnItemClickListener listener) {
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
        Restaurante restaurante= restauranteList.get(position);
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
        private TextView telefono;
        private ImageView imagen;
        private ImageView imagenCarta;


        public RestauranteViewHolder(View itemView) {
            super(itemView);

            nombreRestaurante= (TextView)itemView.findViewById(R.id.nombreRestaurante);
            telefono= (TextView)itemView.findViewById(R.id.telefono);
            imagen= (ImageView)itemView.findViewById(R.id.imagen);
//            imagenCarta= (ImageView)itemView.findViewById(R.id.imageViewCartaFragment);

        }

        // asignar valores a los componentes
        public void bindUser(final Restaurante restaurante, final OnItemClickListener listener) {
            nombreRestaurante.setText(restaurante.getNombre());
            telefono.setText(restaurante.getTelefono());
            //descripcion.setText(restaurante.getDescripcion());
            imagen.setImageResource(restaurante.getDrawImg());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Log.i("Hola", "Hola");
                    listener.onItemClick(restaurante);
                }
            });

        }
    }


}
