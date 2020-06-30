package www.groomer.nottzapp.UI.AddNotes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.add_notes_fragment.*
import www.groomer.nottzapp.MainActivity
import www.groomer.nottzapp.R
import www.groomer.nottzapp.UI.AddNotes.Notes.ViewModels.NotesViewModel
import www.groomer.nottzapp.utils.toast

class AddNotesFragment : Fragment(R.layout.add_notes_fragment) {

    private lateinit var viewModel: NotesViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel



        btn_save_notes.setOnClickListener {
            val title = notes_title_et.text.toString().trim()
            val description = notes_desc_et.text.toString().trim()

            when {
                title.isEmpty() -> {
                    requireActivity().toast("Your title is empty")
                }
                description.isEmpty() -> {
                    requireActivity().toast("Your description is empty")
                }
                else -> {
                    viewModel.insertNotes(title, description).also {
                        requireActivity().toast("Note saved successfully")
                        findNavController().navigate(R.id.action_addNotesFragment_to_notesFragment)
                    }
                }
            }
        }

    }
}