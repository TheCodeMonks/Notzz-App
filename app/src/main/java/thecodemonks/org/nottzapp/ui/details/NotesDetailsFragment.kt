/*
 *
 *  *
 *  *  * MIT License
 *  *  *
 *  *  * Copyright (c) 2020 Spikey Sanju
 *  *  *
 *  *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  *  * of this software and associated documentation files (the "Software"), to deal
 *  *  * in the Software without restriction, including without limitation the rights
 *  *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  *  * copies of the Software, and to permit persons to whom the Software is
 *  *  * furnished to do so, subject to the following conditions:
 *  *  *
 *  *  * The above copyright notice and this permission notice shall be included in all
 *  *  * copies or substantial portions of the Software.
 *  *  *
 *  *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  *  * SOFTWARE.
 *  *
 *
 *
 */

package thecodemonks.org.nottzapp.ui.details

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import thecodemonks.org.nottzapp.R
import thecodemonks.org.nottzapp.databinding.FragmentNotesDetailsBinding
import thecodemonks.org.nottzapp.ui.notes.NotesViewModel
import thecodemonks.org.nottzapp.utils.saveBitmap
import thecodemonks.org.nottzapp.utils.showOrHide

@AndroidEntryPoint
class NotesDetailsFragment : Fragment(R.layout.fragment_notes_details) {

    private val viewModel: NotesViewModel by activityViewModels()
    private val args: NotesDetailsFragmentArgs by navArgs()

    private lateinit var _binding: FragmentNotesDetailsBinding
    private val binding get() = _binding

    // handle permission dialog
    private val requestLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) shareImage() else showErrorDialog()
        }

    private fun showErrorDialog() = findNavController().navigate(
        NotesDetailsFragmentDirections.actionNotesDetailsFragmentToErrorDialog(
            "Image share failed!",
            "You have to enable storage permission to share transaction as Image"
        )
    )

    private fun shareImage() {

        if (!isStoragePermissionGranted()) {
            requestLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            return
        }

        // unHide watermark
        showLogo(true)
        val imageURI = binding.noteLayout.noteDetailLayout.drawToBitmap().let { bitmap ->
            // hide watermark
            showLogo(false)
            saveBitmap(requireActivity(), bitmap)
        } ?: run {
            Toast.makeText(requireContext(), "Error Occured", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = ShareCompat.IntentBuilder(requireActivity())
            .setType("image/jpeg")
            .setStream(imageURI)
            .intent

        startActivity(Intent.createChooser(intent, null))
    }

    private fun showLogo(isVisible: Boolean) = with(binding) {
        noteLayout.logo.showOrHide(isVisible)
    }

    private fun isStoragePermissionGranted(): Boolean = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNotesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        // receiving bundles here
        val notes = args.notes
        val id = notes.id

        with(binding) {
            noteLayout.titleET.setText(notes.title)
            noteLayout.noteET.setText(notes.description)

            // update notes on click
            updateBtnSaveNotes.setOnClickListener {
                val (title, note) = getNoteContent()

                viewModel.updateNotes(id, title, note).also {
                    findNavController().navigate(R.id.action_notesDetailsFragment_to_notesFragment)
                    Toast.makeText(
                        activity,
                        getString(R.string.note_updated_msg),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.share_menu, menu)
    }

    @SuppressLint("QueryPermissionsNeeded")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        return when (item.itemId) {

            R.id.action_delete -> {
                viewModel.deleteNotes(args.notes.id, args.notes.title, args.notes.description)
                findNavController().navigateUp()
                true
            }

            R.id.action_share_text -> {
                shareText()
                true
            }

            R.id.action_share_image -> {
                shareImage()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getNoteContent() = binding.noteLayout.let {
        Pair(
            it.titleET.text.toString(),
            it.noteET.text.toString()
        )
    }

    @SuppressLint("StringFormatMatches")
    private fun shareText() = with(binding) {
        val shareMsg = getString(
            R.string.share_message,
            args.notes.title,
            args.notes.description
        )

        val intent = ShareCompat.IntentBuilder(requireActivity())
            .setType("text/plain")
            .setText(shareMsg)
            .intent

        startActivity(Intent.createChooser(intent, null))
    }
}
