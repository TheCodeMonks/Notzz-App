package thecodemonks.org.nottzapp.UI.AddNotes.Notes.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import thecodemonks.org.nottzapp.Model.Notes
import thecodemonks.org.nottzapp.Repo.NotesRepo

class NotesViewModel(private val notesRepo: NotesRepo) : ViewModel() {


    // save notes
    fun insertNotes(taskName: String, taskDesc: String) = viewModelScope.launch {
        val notes = Notes(
            title = taskName,
            description = taskDesc
        )
        notesRepo.insert(notes)
    }

    // save notes
    fun updateNotes(id: Int, taskName: String, taskDesc: String) = viewModelScope.launch {
        val notes = Notes(
            id = id,
            title = taskName,
            description = taskDesc
        )
        notesRepo.update(notes)
    }

    // get saved notes
    fun getSavedNotes() = notesRepo.getSavedNotes()


    // delete notes
    fun deleteNotes(taskID: Int, taskName: String, taskDesc: String) = viewModelScope.launch {
        val notes = Notes(
            id = taskID,
            title = taskName,
            description = taskDesc
        )
        notesRepo.deleteNotes(notes)
    }

}