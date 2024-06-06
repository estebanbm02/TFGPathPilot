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

import com.example.tfg.InformacionCocheActivity;
import com.example.tfg.R;

import Model.Coche;
import Model.Usuario;

import java.util.List;

public class CocheAdapter extends RecyclerView.Adapter<CocheAdapter.CocheViewHolder>{
    private List<Coche> coches;
    private final Context context;
    private final Usuario usuario;

    public CocheAdapter(Context context, List<Coche> coches, Usuario usuario) {
        this.context = context;
        this.coches = coches;
        this.usuario = usuario;
    }

    @NonNull
    @Override
    public CocheViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.coche_item_layout, parent, false);
        return new CocheViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CocheViewHolder holder, int position) {
        Coche coche = coches.get(position);
        holder.marcaTexView.setText(coche.getMarca());
        holder.modeloTextView.setText(coche.getModelo());
        cargarImagenDesdeRuta(holder.imageView, coche.getImagen());
        holder.itemView.setOnClickListener(v -> abrirInformacionCoche(coche));
    }

    private void abrirInformacionCoche(Coche coche) {
        Intent intent = new Intent(context, InformacionCocheActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        intent.putExtra("id_coche", coche.getId());
        intent.putExtra("marca", coche.getMarca());
        intent.putExtra("modelo", coche.getModelo());
        intent.putExtra("tipo", coche.getTipo());
        intent.putExtra("caballos", coche.getCaballos());
        intent.putExtra("ubicacion", coche.getUbicacion());
        intent.putExtra("precio_por_dia", coche.getPrecio_por_dia());
        intent.putExtra("imagen", coche.getImagen());
        context.startActivity(intent);
    }

    private void cargarImagenDesdeRuta(ImageView imageView, String nombreImagen) {
        String nombreLimpio = nombreImagen != null ? nombreImagen.replace("$", "").replaceAll("\\.jpg|\\.png", "").toLowerCase() : "";
        int idRecurso = context.getResources().getIdentifier(nombreLimpio, "drawable", context.getPackageName());
        imageView.setImageResource(idRecurso != 0 ? idRecurso : R.drawable.fotoerror);
    }

    @Override
    public int getItemCount() {
        return coches != null ? coches.size() : 0;
    }

    static class CocheViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView marcaTexView, modeloTextView;
        CocheViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewCoche);
            marcaTexView = itemView.findViewById(R.id.textViewMarcaCoche);
            modeloTextView = itemView.findViewById(R.id.textViewModeloCoche);
        }
    }

    public void updateData(List<Coche> nuevosCoches) {
        this.coches = nuevosCoches;
        notifyDataSetChanged();
    }
}