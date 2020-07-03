package www.groomer.nottzapp.UI.AddNotes.Notes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.notes_fragment.*
import www.groomer.nottzapp.Adapter.NotesAdapter
import www.groomer.nottzapp.MainActivity
import www.groomer.nottzapp.R
import www.groomer.nottzapp.UI.AddNotes.Notes.ViewModels.NotesViewModel

class NotesFragment : Fragment(R.layout.notes_fragment) {

    private lateinit var viewModel: NotesViewModel
    private lateinit var notesAdapter: NotesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setUpRV()

        // onclick navigate to add notes
        btn_add_notes.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_addNotesFragment)
        }

        // observer data change for saved notes
        viewModel.getSavedNotes().observe(viewLifecycleOwner, Observer { notes ->
            notesAdapter.differ.submitList(notes)
        })


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
                    notes.title.toString(),
                    notes.description.toString()
                )
                Snackbar.make(view, "Successfully deleted notes", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.insertNotes(notes.title.toString(), notes.description.toString())
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