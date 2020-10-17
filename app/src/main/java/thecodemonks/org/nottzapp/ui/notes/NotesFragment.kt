package thecodemonks.org.nottzapp.ui.notes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.notes_fragment.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import thecodemonks.org.nottzapp.R
import thecodemonks.org.nottzapp.adapter.NotesAdapter

class NotesFragment : Fragment(R.layout.notes_fragment) {

    private val viewModel: NotesViewModel by activityViewModels()
    private lateinit var notesAdapter: NotesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRV()

        // onclick navigate to add notes
        btn_add_notes.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_addNotesFragment)
        }

        // observer data change for saved notes
        lifecycleScope.launch {
            viewModel.getSavedNotes().collect { notes ->
                notesAdapter.differ.submitList(notes)
            }
        }

        // onclick navigate to add notes
        notesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("notes", it)
            }
            findNavController().navigate(
                R.id.action_notesFragment_to_notesDetailsFragment,
                bundle
            )
        }

        // init item touch callback for swipe action
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // get item position & delete notes
                val position = viewHolder.adapterPosition
                val notes = notesAdapter.differ.currentList[position]
                viewModel.deleteNotes(
                    notes.id!!.toInt(),
                    notes.title,
                    notes.description
                )
                Snackbar.make(view, getString(R.string.note_deleted_msg), Snackbar.LENGTH_LONG)
                    .apply {
                        setAction(getString(R.string.undo)) {
                            viewModel.insertNotes(
                                notes.title,
                                notes.description
                            )
                        }
                        show()
                    }
            }
        }

        // attach swipe callback to rv
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(notes_rv)
        }
    }

    private fun setUpRV() {
        notesAdapter = NotesAdapter()
        notes_rv.apply {
            adapter = notesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}