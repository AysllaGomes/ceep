package ayslla.gomes.ceep.ui.activity;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import ayslla.gomes.ceep.R;
import ayslla.gomes.ceep.model.Nota;
import ayslla.gomes.ceep.dao.NotaDAO;
import ayslla.gomes.ceep.ui.recyclerview.adapter.ListaNotasAdapter;
import ayslla.gomes.ceep.ui.recyclerview.adapter.listener.OnItemClickListener;

import static ayslla.gomes.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static ayslla.gomes.ceep.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
import static ayslla.gomes.ceep.ui.activity.NotaActivityConstantes.POSITION_INVALID;
import static ayslla.gomes.ceep.ui.activity.NotaActivityConstantes.EXCEPTION_UPDATE_NOTE;
import static ayslla.gomes.ceep.ui.activity.NotaActivityConstantes.REQUEST_CODE_NOTE_EDIT;
import static ayslla.gomes.ceep.ui.activity.NotaActivityConstantes.RESULT_CODE_NOTE_CREATE;
import static ayslla.gomes.ceep.ui.activity.NotaActivityConstantes.REQUEST_CODE_INSERT_NOTE;

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

        super.onActivityResult(requestCode, resultCode, data);

        if ( isResultWithNote(requestCode, resultCode, data) ) {
            Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
            create(notaRecebida);
        }

        if ( canEditNote(requestCode, resultCode, data) ) {
            Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
            int posicao = data.getIntExtra(CHAVE_POSICAO, POSITION_INVALID);

            if ( isPositionValid(posicao) ) {
                update(notaRecebida, posicao);
            } else {
                Toast.makeText(this,
                        EXCEPTION_UPDATE_NOTE,
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void update(Nota nota, int posicao) {
        new NotaDAO().altera(posicao, nota);
        adapter.altera(posicao, nota);
    }

    private boolean isPositionValid(int posicao) {
        return posicao > POSITION_INVALID;
    }

    private boolean canEditNote(int requestCode, int resultCode, @Nullable Intent data) {
        return isCodeRequestEdit(requestCode) && isResultCode(resultCode) && hasNote(data);
    }

    private boolean isCodeRequestEdit(int requestCode) {
        return requestCode == REQUEST_CODE_NOTE_EDIT;
    }

    private List<Nota> pegaTodasAsNotas() {
        NotaDAO dao = new NotaDAO();

        for (int i = 0; i < 10; i++) {
            dao.insere( new Nota("Título " + (i+1),
                    "Descrição " + (i + 1) ));
        }

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

    private void create(Nota nota) {
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
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota, int posicao) {
                vaiParaFormularioNotaActivityEdit(nota, posicao);
            }
        });
    }

    private void vaiParaFormularioNotaActivityEdit(Nota nota, int posicao) {
        Intent openFormWithNote = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        openFormWithNote.putExtra(CHAVE_NOTA, nota);
        openFormWithNote.putExtra(CHAVE_POSICAO, posicao);
        startActivityForResult(openFormWithNote, REQUEST_CODE_NOTE_EDIT);
    }

    private void configuraActionInsertNote() {
        TextView createNota = findViewById(R.id.lista_notas_insere_nota);
        createNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaFormularioNotaActivityInsert();
            }
        });
    }

    private void vaiParaFormularioNotaActivityInsert() {
        Intent startFormulario = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        startActivityForResult(startFormulario, REQUEST_CODE_INSERT_NOTE);
    }

}
