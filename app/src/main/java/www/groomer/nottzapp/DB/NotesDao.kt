package www.groomer.nottzapp.DB

import androidx.lifecycle.LiveData
import androidx.room.*
import www.groomer.nottzapp.Model.Notes


@Dao
interface NotesDao {

    // insert notes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: Notes)

    // update notes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNotes(notes: Notes)


    // get all notes from db
    @Query("SELECT * FROM notes")
    fun getNotes(): LiveData<List<Notes>>


    // delete notes from db
    @Delete
    suspend fun deleteNotes(notes: Notes)
}