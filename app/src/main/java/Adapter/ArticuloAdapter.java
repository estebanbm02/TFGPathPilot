package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg.InformacionArticuloActivity;
import com.example.tfg.R;
import Model.Articulo;
import Model.Usuario;

import java.util.List;

public class ArticuloAdapter extends RecyclerView.Adapter<ArticuloAdapter.ArticuloViewHolder> {
    private List<Articulo> articulos;
    private final Context context;
    private final Usuario usuario;

    public ArticuloAdapter(Context context, List<Articulo> articulos, Usuario usuario) {
        this.context = context;
        this.articulos = articulos;
        this.usuario = usuario;
    }

    @NonNull
    @Override
    public ArticuloViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.articulo_item_layout, parent, false);
        return new ArticuloViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticuloViewHolder holder, int position) {
        Articulo articulo = articulos.get(position);
        holder.nombreTextView.setText(articulo.getNombre());
        holder.precioTextView.setText(String.format("€%.2f", articulo.getPrecio()));
        cargarImagenDesdeRuta(holder.imageView, articulo.getImagen());
        holder.itemView.setOnClickListener(v -> abrirInformacionArticulo(articulo));
    }

    private void abrirInformacionArticulo(Articulo articulo) {
        Intent intent = new Intent(context, InformacionArticuloActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        intent.putExtra("nombre", articulo.getNombre());
        intent.putExtra("tipo", articulo.getTipo());
        intent.putExtra("precio", articulo.getPrecio());
        intent.putExtra("imagen", articulo.getImagen());
        context.startActivity(intent);
    }

    private void cargarImagenDesdeRuta(ImageView imageView, String nombreImagen) {
        String nombreLimpio = nombreImagen != null ? nombreImagen.replace("$", "").replaceAll("\\.jpg|\\.png", "").toLowerCase() : "";
        int idRecurso = context.getResources().getIdentifier(nombreLimpio, "drawable", context.getPackageName());
        imageView.setImageResource(idRecurso != 0 ? idRecurso : R.drawable.fotoerror);
    }

    @Override
    public int getItemCount() {
        return articulos != null ? articulos.size() : 0;
    }

    static class ArticuloViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nombreTextView, precioTextView;
        ArticuloViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewArticulo);
            nombreTextView = itemView.findViewById(R.id.textViewNombreArticulo);
            precioTextView = itemView.findViewById(R.id.textViewPrecio);
        }
    }

    public void updateData(List<Articulo> nuevosArticulos) {
        this.articulos = nuevosArticulos;
        notifyDataSetChanged();
    }
}