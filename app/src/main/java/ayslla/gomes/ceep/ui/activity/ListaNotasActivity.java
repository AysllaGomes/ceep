package ayslla.gomes.ceep.ui.activity;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import ayslla.gomes.ceep.R;
import ayslla.gomes.ceep.model.Nota;
import ayslla.gomes.ceep.dao.NotaDAO;
import ayslla.gomes.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

public class ListaNotasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        TextView createNota = findViewById(R.id.lista_notas_insere_nota);
        createNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startFormulario = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
                startActivity(startFormulario);
            }
        });
    }

    @Override
    protected void onResume() {
        NotaDAO dao = new NotaDAO();
        List<Nota> todasNotas = dao.todos();
        configuraRecyclerView(todasNotas);
        super.onResume();
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todasNotas, listaNotas);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        listaNotas.setAdapter(new ListaNotasAdapter(this, todasNotas));
    }

}
