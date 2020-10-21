package ayslla.gomes.ceep.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;

import ayslla.gomes.ceep.R;
import ayslla.gomes.ceep.model.Nota;

import androidx.appcompat.app.AppCompatActivity;

import static ayslla.gomes.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static ayslla.gomes.ceep.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
import static ayslla.gomes.ceep.ui.activity.NotaActivityConstantes.POSITION_INVALID;
import static ayslla.gomes.ceep.ui.activity.NotaActivityConstantes.RESULT_CODE_NOTE_CREATE;

public class FormularioNotaActivity extends AppCompatActivity {

    private TextView title;
    private TextView decription;
    private int position = POSITION_INVALID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        inicializaCampos();

        Intent dadosRecebidos = getIntent();
        if ( dadosRecebidos.hasExtra(CHAVE_NOTA) ) {
            Nota notaRecebida = (Nota) dadosRecebidos.getSerializableExtra(CHAVE_NOTA);
            position = dadosRecebidos.getIntExtra(CHAVE_POSICAO, POSITION_INVALID);
            preencheCampos(notaRecebida);
        }
    }

    private void inicializaCampos() {
        title = findViewById(R.id.formulario_nota_titulo);
        decription = findViewById(R.id.formulario_nota_descricao);
    }

    private void preencheCampos(Nota nota) {
        title.setText(nota.getTitulo());
        decription.setText(nota.getDescricao());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_save_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ( isMenuSaveNote(item) ) {
            Nota notaCriada = createNote();
            returnNote(notaCriada);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isMenuSaveNote(MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_note_ic_save;
    }

    private Nota createNote() {
        return new Nota(title.getText().toString(), decription.getText().toString());
    }

    private void returnNote(Nota nota) {
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_NOTA, nota);
        resultadoInsercao.putExtra(CHAVE_POSICAO, position);
        setResult(RESULT_CODE_NOTE_CREATE, resultadoInsercao);
    }

}
