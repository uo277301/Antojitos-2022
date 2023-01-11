package es.uniovi.eii.mainantojitos;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import es.uniovi.eii.mainantojitos.db.RestaurantePojo;
import es.uniovi.eii.mainantojitos.modelo.Reseña;

public class ReseñaAdapter extends RecyclerView.Adapter<ReseñaAdapter.ReseñaViewHolder> {




    private List<Reseña> listaReseña;
    private final ReseñaAdapter.OnItemClickListener listener;

    public ReseñaAdapter(List<Reseña> listaReseña, ReseñaAdapter.OnItemClickListener listener) {
        this.listaReseña = listaReseña;
        this.listener = listener;
    }

    public ReseñaAdapter(List<Reseña> reseñasBase) {
        this.listaReseña = reseñasBase;
        this.listener = new OnItemClickListener() {
            @Override
            public void onItemClick(Reseña item) {

            }
        };
    }

    public interface OnItemClickListener{
        void onItemClick(Reseña item);

    }

    @NonNull
    @Override
    public ReseñaAdapter.ReseñaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recylcer_view_resena, parent, false);
        return new ReseñaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReseñaAdapter.ReseñaViewHolder holder, int position) {
        Reseña res= listaReseña.get(position);
        holder.bindUser(res, listener);
    }

    @Override
    public int getItemCount() {
        return listaReseña.size();
    }

    public static class ReseñaViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreUsuario;
        private TextView reseñaUser;
        private RatingBar rating;


        public ReseñaViewHolder(View itemView) {
            super(itemView);

            nombreUsuario= (TextView)itemView.findViewById(R.id.txt_nombre_usuario);
            reseñaUser= (TextView)itemView.findViewById(R.id.textoReseñaCliente);
            rating=(RatingBar)itemView.findViewById(R.id.ratingBar_reseña);

        }

        // asignar valores a los componentes
        public void bindUser(final Reseña reseña, final OnItemClickListener listener) {
            nombreUsuario.setText(reseña.getEmail());
            reseñaUser.setText(reseña.getReseña());
            rating.setRating((float) reseña.getRating());

        }
    }
}
