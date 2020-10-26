package thecodemonks.org.nottzapp.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import thecodemonks.org.nottzapp.R
import thecodemonks.org.nottzapp.databinding.AddNotesFragmentBinding
import thecodemonks.org.nottzapp.ui.notes.NotesViewModel
import thecodemonks.org.nottzapp.utils.toast

class AddNotesFragment : Fragment(R.layout.add_notes_fragment) {

    private val viewModel: NotesViewModel by activityViewModels()
    private lateinit var _binding: AddNotesFragmentBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = AddNotesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // save notes to db
        binding.btnSaveNotes.setOnClickListener {
            val title = binding.notesTitleEt.text.toString().trim()
            val description = binding.notesDescEt.text.toString().trim()

            // check whether both title & desc is not empty
            when {
                title.isEmpty() -> {
                    requireActivity().toast(getString(R.string.empty_title_msg))
                }
                description.isEmpty() -> {
                    requireActivity().toast(getString(R.string.empty_desc_msg))
                }
                else -> {
                    viewModel.insertNotes(title, description).also {
                        requireActivity().toast(getString(R.string.note_saved_msg)).also {
                            findNavController().navigate(R.id.action_addNotesFragment_to_notesFragment)
                        }
                    }
                }
            }
        }
    }
}