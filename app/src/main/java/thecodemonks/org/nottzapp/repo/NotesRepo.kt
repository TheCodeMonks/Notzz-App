package thecodemonks.org.nottzapp.repo

import thecodemonks.org.nottzapp.db.NotesDatabase
import thecodemonks.org.nottzapp.model.Notes


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