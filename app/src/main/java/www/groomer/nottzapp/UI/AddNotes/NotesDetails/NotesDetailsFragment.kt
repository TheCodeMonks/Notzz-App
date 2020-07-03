package www.groomer.nottzapp.UI.AddNotes.NotesDetails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_notes_details.*
import www.groomer.nottzapp.MainActivity
import www.groomer.nottzapp.R
import www.groomer.nottzapp.UI.AddNotes.Notes.ViewModels.NotesViewModel

class NotesDetailsFragment : Fragment(R.layout.fragment_notes_details) {

    private lateinit var viewModel: NotesViewModel
    val args: NotesDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init view model here
        viewModel = (activity as MainActivity).viewModel

        // receiving bundles here
        val notes = args.notes
        val id = notes.id!!.toInt()
        edit_notes_title_et.setText(notes.title)
        edit_notes_desc_et.setText(notes.description)

        // update notes on click
        update_btn_save_notes.setOnClickListener {

            val title = edit_notes_title_et.text.toString().trim()
            val description = edit_notes_desc_et.text.toString().trim()

            viewModel.updateNotes(id, title, description).also {
                findNavController().navigate(R.id.action_notesDetailsFragment_to_notesFragment)
                Toast.makeText(activity, "Note updated successfully!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}