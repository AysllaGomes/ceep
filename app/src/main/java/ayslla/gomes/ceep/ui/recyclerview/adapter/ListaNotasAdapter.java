package ayslla.gomes.ceep.ui.recyclerview.adapter;

import java.util.List;

import ayslla.gomes.ceep.R;
import ayslla.gomes.ceep.model.Nota;

import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.TextView;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {

    private Context context;
    private List<Nota> notas;

    public ListaNotasAdapter(Context context, List<Nota> notas) {
        this.context = context;
        this.notas = notas;
    }

    @NonNull
    @Override
    public ListaNotasAdapter.NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_nota, parent, false);
        return new NotaViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaNotasAdapter.NotaViewHolder holder, int position) {
        Nota nota = notas.get(position);

        NotaViewHolder notaViewHolder = holder;
        notaViewHolder.vincula(nota);
    }

    @Override
    public int getItemCount() { return notas.size(); }

    class NotaViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView desciption;

        public NotaViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_nota_titulo);
            desciption = itemView.findViewById(R.id.item_nota_descricao);
        }

        public void vincula(Nota nota) {
            title.setText(nota.getTitulo());
            desciption.setText(nota.getDescricao());
        }

    }

}
