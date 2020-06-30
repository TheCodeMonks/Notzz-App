package www.groomer.nottzapp.Repo

import www.groomer.nottzapp.DB.NotesDatabase
import www.groomer.nottzapp.Model.Notes


class NotesRepo(private val db: NotesDatabase){

    // insert notes
    suspend fun insert(notes: Notes) = db.getNotesDao().insertNotes(notes)

    // update notes
    suspend fun update(notes: Notes) = db.getNotesDao().updateNotes(notes)

    // get saved notes
    fun getSavedNotes() = db.getNotesDao().getNotes()

    // delete article
    suspend fun deleteNotes(notes: Notes) = db.getNotesDao().deleteNotes(notes)

}