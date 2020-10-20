package ayslla.gomes.ceep.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.EditText;

import ayslla.gomes.ceep.R;
import ayslla.gomes.ceep.model.Nota;

import androidx.appcompat.app.AppCompatActivity;

import static ayslla.gomes.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static ayslla.gomes.ceep.ui.activity.NotaActivityConstantes.RESULT_CODE_NOTE_CREATE;

public class FormularioNotaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);
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
            note(notaCriada);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isMenuSaveNote(MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_note_ic_save;
    }

    private Nota createNote() {
        EditText titulo = findViewById(R.id.formulario_nota_titulo);
        EditText descricao = findViewById(R.id.formulario_nota_descricao);
        return new Nota(titulo.getText().toString(), descricao.getText().toString());
    }

    private void note(Nota nota) {
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_NOTA, nota);
        setResult(RESULT_CODE_NOTE_CREATE, resultadoInsercao);
    }

}
