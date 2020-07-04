package thecodemonks.org.nottzapp.UI.AddNotes.Notes.ViewModelProviders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import thecodemonks.org.nottzapp.Repo.NotesRepo
import thecodemonks.org.nottzapp.UI.AddNotes.Notes.ViewModels.NotesViewModel

class NewsViewModelProviderFactory(private val notesRepo: NotesRepo) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NotesViewModel(notesRepo) as T
    }
}