package thecodemonks.org.nottzapp.ui.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_notes_details.*
import thecodemonks.org.nottzapp.R
import thecodemonks.org.nottzapp.ui.notes.NotesViewModel

class NotesDetailsFragment : Fragment(R.layout.fragment_notes_details) {

    private val viewModel: NotesViewModel by activityViewModels()
    private val args: NotesDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)


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
                Toast.makeText(activity, getString(R.string.note_updated_msg), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.share_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        return when (item.itemId) {

            R.id.action_delete -> {
                viewModel.deleteNotes(args.notes.id!!, args.notes.title!!, args.notes.description!!)
                findNavController().navigateUp()
                true
            }

            R.id.action_share -> {
                val shareMsg = getString(
                    R.string.share_message,
                    args.notes.title,
                    args.notes.description
                )

                val intent = ShareCompat.IntentBuilder.from(requireActivity())
                    .setType("text/plain")
                    .setText(shareMsg)
                    .intent

                if (intent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivity(intent)
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}