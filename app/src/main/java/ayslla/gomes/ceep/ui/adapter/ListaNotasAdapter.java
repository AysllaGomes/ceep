package ayslla.gomes.ceep.ui.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;

import ayslla.gomes.ceep.R;
import ayslla.gomes.ceep.model.Nota;

public class ListaNotasAdapter extends BaseAdapter {

    private final Context context;
    private final List<Nota> notas;

    public ListaNotasAdapter(
            Context context, List<Nota> notas
    ) {
        this.context = context;
        this.notas = notas;
    }

    @Override
    public int getCount() {
        return notas.size();
    }

    @Override
    public Nota getItem(int position) {
        return notas.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_nota, viewGroup, false);
        Nota nota = notas.get(position);

        TextView titulo = viewCriada.findViewById(R.id.item_nota_titulo);
        titulo.setText(nota.getTitulo());

        TextView descricao = viewCriada.findViewById(R.id.item_nota_descricao);
        descricao.setText(nota.getDescricao());

        return viewCriada;
    }

}
