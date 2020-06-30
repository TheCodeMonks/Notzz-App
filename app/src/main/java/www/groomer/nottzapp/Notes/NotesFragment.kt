package www.groomer.nottzapp.Notes

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.notes_fragment.*
import www.groomer.nottzapp.Adapter.NotesAdapter
import www.groomer.nottzapp.MainActivity
import www.groomer.nottzapp.Notes.ViewModels.NotesViewModel
import www.groomer.nottzapp.R

class NotesFragment : Fragment(R.layout.notes_fragment) {

    private lateinit var viewModel: NotesViewModel
    private lateinit var notesAdapter: NotesAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setUpRV()

        btn_add_notes.setOnClickListener {

            viewModel.inserNotes("The Code Monk","We provide most valuable explainer blogs & bideos as FOSS").also {
                Toast.makeText(activity, "Notes added successfully", Toast.LENGTH_SHORT).show()
            }
        }


        // observer data change for saved notes
        viewModel.getSavedNotes().observe(viewLifecycleOwner, Observer { notes ->
            notesAdapter.differ.submitList(notes)
                Toast.makeText(activity, "Toast ${notes[1].title}", Toast.LENGTH_SHORT).show()

        })



    }

    private fun setUpRV() {
        notesAdapter = NotesAdapter()
        notes_rv.apply {
            adapter = notesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


}