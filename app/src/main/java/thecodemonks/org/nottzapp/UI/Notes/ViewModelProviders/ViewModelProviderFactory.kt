package thecodemonks.org.nottzapp.UI.ui.Notes.ViewModelProviders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import thecodemonks.org.nottzapp.UI.ui.Notes.ViewModels.NotesViewModel
import thecodemonks.org.nottzapp.repo.NotesRepo

@Suppress("UNCHECKED_CAST")
class TodoViewModelProviderFactory(private val notesRepo: NotesRepo) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NotesViewModel(notesRepo) as T
    }
}