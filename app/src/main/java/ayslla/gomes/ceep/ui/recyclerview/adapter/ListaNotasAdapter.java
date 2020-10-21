package ayslla.gomes.ceep.ui.recyclerview.adapter;

import java.util.List;
import java.util.Collections;

import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.TextView;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ayslla.gomes.ceep.R;
import ayslla.gomes.ceep.model.Nota;
import ayslla.gomes.ceep.ui.recyclerview.adapter.listener.OnItemClickListener;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {

    private Context context;
    private List<Nota> notas;
    private OnItemClickListener onItemClickListener;

    public ListaNotasAdapter(Context context, List<Nota> notas) {
        this.context = context;
        this.notas = notas;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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
        holder.vincula(nota);
    }

    @Override
    public int getItemCount() { return notas.size(); }

    public void add(Nota nota) {
        notas.add(nota);
        notifyDataSetChanged();
    }

    public void update(int position, Nota nota) {
        notas.set(position, nota);
        notifyItemChanged(position);
    }

    public void remove(int position) {
        notas.remove(position);
        notifyItemRemoved(position);
    }

    public void change(int start, int end) {
        Collections.swap(notas, start, end);
        notifyItemMoved(start, end);
    }

    class NotaViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView desciption;
        private Nota nota;

        public NotaViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_nota_titulo);
            desciption = itemView.findViewById(R.id.item_nota_descricao);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(nota, getAdapterPosition());
                }
            });
        }

        public void vincula(Nota nota) {
            this.nota = nota;
            preencheCampos(nota);
        }

        private void preencheCampos(Nota nota) {
            title.setText(nota.getTitulo());
            desciption.setText(nota.getDescricao());
        }

    }

}
