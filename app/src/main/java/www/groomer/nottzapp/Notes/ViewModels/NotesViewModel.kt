package www.groomer.nottzapp.Notes.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import www.groomer.nottzapp.Model.Notes
import www.groomer.nottzapp.Repo.NotesRepo

class NotesViewModel(private val notesRepo: NotesRepo) : ViewModel() {


    // save notes
    fun saveNotes(notes: Notes) = viewModelScope.launch {
        notesRepo.insert(notes)
    }

    // get saved notes
    fun getSavedNotes() = notesRepo.getSavedNotes()


    // delete notes
    fun deleteNotes(notes: Notes) = viewModelScope.launch {
        notesRepo.deleteNotes(notes)
    }



    fun inserNotes(taskName: String, taskDesc: String) {
        viewModelScope.launch {
            val notes = Notes(
                title = taskName,
                description = taskDesc
            )
            notesRepo.insert(notes)
        }
    }

}