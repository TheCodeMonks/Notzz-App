package thecodemonks.org.nottzapp.ui.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_notes_details.*
import thecodemonks.org.nottzapp.R
import thecodemonks.org.nottzapp.ui.notes.NotesViewModel
import thecodemonks.org.nottzapp.app.MainActivity

class NotesDetailsFragment : Fragment(R.layout.fragment_notes_details) {

    private val viewModel: NotesViewModel by activityViewModels()
    private val args: NotesDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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