package thecodemonks.org.nottzapp.ui.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.add_notes_fragment.*
import thecodemonks.org.nottzapp.R
import thecodemonks.org.nottzapp.ui.notes.NotesViewModel
import thecodemonks.org.nottzapp.utils.toast

class AddNotesFragment : Fragment(R.layout.add_notes_fragment) {

    private val viewModel: NotesViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // save notes to db
        btn_save_notes.setOnClickListener {
            val title = notes_title_et.text.toString().trim()
            val description = notes_desc_et.text.toString().trim()

            // check whether both title & desc is not empty
            when {
                title.isEmpty() -> {
                    requireActivity().toast("Your title is empty")
                }
                description.isEmpty() -> {
                    requireActivity().toast("Your description is empty")
                }
                else -> {
                    viewModel.insertNotes(title, description).also {
                        requireActivity().toast("Note saved successfully").also {
                            findNavController().navigate(R.id.action_addNotesFragment_to_notesFragment)
                        }
                    }
                }
            }
        }
    }
}