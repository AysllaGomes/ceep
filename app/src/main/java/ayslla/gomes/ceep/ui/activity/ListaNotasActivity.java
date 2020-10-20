package ayslla.gomes.ceep.ui.activity;

import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import ayslla.gomes.ceep.R;
import ayslla.gomes.ceep.model.Nota;
import ayslla.gomes.ceep.dao.NotaDAO;
import ayslla.gomes.ceep.ui.adapter.ListaNotasAdapter;

public class ListaNotasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        ListView listaNotas = findViewById(R.id.lista_notas_listagem);

        NotaDAO dao = new NotaDAO();
        dao.insere(new Nota("Primeira nota", "Primeira descrição"));
        List<Nota> todasNotas = dao.todos();

        listaNotas.setAdapter(new ListaNotasAdapter(this, todasNotas));
    }

}
