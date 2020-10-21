package ayslla.gomes.ceep.ui.recyclerview.helper.callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import ayslla.gomes.ceep.dao.NotaDAO;
import ayslla.gomes.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

public class NotaItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ListaNotasAdapter adapter;

    public NotaItemTouchHelperCallback(ListaNotasAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(
            @NonNull RecyclerView recyclerView,
            @NonNull RecyclerView.ViewHolder viewHolder
    ) {
        int slipMarkings = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int draggingMarks = ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(draggingMarks, slipMarkings);
    }

    @Override
    public boolean onMove(
            @NonNull RecyclerView recyclerView,
            @NonNull RecyclerView.ViewHolder viewHolder,
            @NonNull RecyclerView.ViewHolder target
    ) {
        int start = viewHolder.getAdapterPosition();
        int end = target.getAdapterPosition();
        changeNotes(start, end);
        return true;
    }

    private void changeNotes(int initialPosition, int finalPosition) {
        new NotaDAO().change(initialPosition, finalPosition);
        adapter.change(initialPosition, finalPosition);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int notePosition = viewHolder.getAdapterPosition();
        removeNote(notePosition);
    }

    private void removeNote(int position) {
        new NotaDAO().remove(position);
        adapter.remove(position);
    }

}
