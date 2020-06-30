package www.groomer.nottzapp.UI.AddNotes.Notes.ViewModelProviders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import www.groomer.nottzapp.Repo.NotesRepo
import www.groomer.nottzapp.UI.AddNotes.Notes.ViewModels.NotesViewModel

class NewsViewModelProviderFactory(private val notesRepo: NotesRepo) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NotesViewModel(notesRepo) as T
    }
}