package www.groomer.nottzapp.Notes.ViewModelProviders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import www.groomer.nottzapp.Notes.ViewModels.NotesViewModel
import www.groomer.nottzapp.Repo.NotesRepo

class NewsViewModelProviderFactory(private val notesRepo: NotesRepo) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NotesViewModel(notesRepo) as T
    }
}