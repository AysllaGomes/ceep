package ayslla.gomes.ceep.ui.activity;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import ayslla.gomes.ceep.R;
import ayslla.gomes.ceep.model.Nota;
import ayslla.gomes.ceep.dao.NotaDAO;
import ayslla.gomes.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

import static ayslla.gomes.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static ayslla.gomes.ceep.ui.activity.NotaActivityConstantes.REQUEST_CODE_INSERT_NOTE;
import static ayslla.gomes.ceep.ui.activity.NotaActivityConstantes.RESULT_CODE_NOTE_CREATE;

public class ListaNotasActivity extends AppCompatActivity {

    private ListaNotasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        List<Nota> todasNotas = pegaTodasAsNotas();

        configuraRecyclerView(todasNotas);
        configuraActionInsertNote();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if ( isResultWithNote(requestCode, resultCode, data) ) {
            Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
            adiciona(notaRecebida);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private List<Nota> pegaTodasAsNotas() {
        NotaDAO dao = new NotaDAO();
        return dao.todos();
    }

    private boolean isResultWithNote(int requestCode, int resultCode, @Nullable Intent data) {
        return isCodeRequestInsertNote(requestCode)
                && isResultCode(resultCode)
                && hasNote(data);
    }

    private boolean isCodeRequestInsertNote(int requestCode) {
        return requestCode == REQUEST_CODE_INSERT_NOTE;
    }

    private boolean isResultCode(int resultCode) {
        return resultCode == RESULT_CODE_NOTE_CREATE;
    }

    private boolean hasNote(@Nullable Intent data) {
        return data.hasExtra(CHAVE_NOTA);
    }

    private void adiciona(Nota nota) {
        new NotaDAO().insere(nota);
        adapter.adiciona(nota);
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todasNotas, listaNotas);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(this, todasNotas);
        listaNotas.setAdapter(adapter);
    }

    private void configuraActionInsertNote() {
        TextView createNota = findViewById(R.id.lista_notas_insere_nota);
        createNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaFormularioNotaActivity();
            }
        });
    }

    private void vaiParaFormularioNotaActivity() {
        Intent startFormulario = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        startActivityForResult(startFormulario, REQUEST_CODE_INSERT_NOTE);
    }

}
