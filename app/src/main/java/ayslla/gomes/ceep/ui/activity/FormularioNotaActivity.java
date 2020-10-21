package ayslla.gomes.ceep.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.app.Activity;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;

import ayslla.gomes.ceep.R;
import ayslla.gomes.ceep.model.Nota;

import androidx.appcompat.app.AppCompatActivity;

import static ayslla.gomes.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static ayslla.gomes.ceep.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
import static ayslla.gomes.ceep.ui.activity.NotaActivityConstantes.POSITION_INVALID;

public class FormularioNotaActivity extends AppCompatActivity {

    private TextView title;
    private TextView description;

    private int position = POSITION_INVALID;

    public static final String TITLE_CREATE_APPBAR = "Insere nota";
    public static final String TITLE_UPDATE_APPBAR = "Altera nota";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        setTitle(TITLE_CREATE_APPBAR);
        createForm();

        Intent receivedData = getIntent();
        if ( receivedData.hasExtra(CHAVE_NOTA) ) {
            setTitle(TITLE_UPDATE_APPBAR);
            Nota noteReceived = (Nota) receivedData.getSerializableExtra(CHAVE_NOTA);
            position = receivedData.getIntExtra(CHAVE_POSICAO, POSITION_INVALID);
            populateForm(noteReceived);
        }
    }

    private void createForm() {
        title = findViewById(R.id.formulario_nota_titulo);
        description = findViewById(R.id.formulario_nota_descricao);
    }

    private void populateForm(Nota nota) {
        title.setText(nota.getTitulo());
        description.setText(nota.getDescricao());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_save_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ( isMenuSaveNote(item) ) {
            Nota createNote = createNote();
            returnNote(createNote);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isMenuSaveNote(MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_note_ic_save;
    }

    private Nota createNote() {
        return new Nota(title.getText().toString(), description.getText().toString());
    }

    private void returnNote(Nota nota) {
        Intent resultInsert = new Intent();
        resultInsert.putExtra(CHAVE_NOTA, nota);
        resultInsert.putExtra(CHAVE_POSICAO, position);
        setResult(Activity.RESULT_OK, resultInsert);
    }

}
