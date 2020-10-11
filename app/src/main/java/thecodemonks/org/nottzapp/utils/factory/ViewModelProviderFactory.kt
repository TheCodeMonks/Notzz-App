package thecodemonks.org.nottzapp.utils.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import thecodemonks.org.nottzapp.ui.notes.NotesViewModel
import thecodemonks.org.nottzapp.repo.NotesRepo

@Suppress("UNCHECKED_CAST")
inline fun <VM : ViewModel> viewModelFactory(crossinline function: () -> VM) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(aClass: Class<T>): T = function() as T
    }