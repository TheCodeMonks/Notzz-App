package thecodemonks.org.nottzapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import thecodemonks.org.nottzapp.model.Notes


@Dao
interface NotesDao {

    // insert notes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: Notes)

    // update notes
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNotes(notes: Notes)


    // get all notes from db
    @Query("SELECT * FROM notes")
    fun getNotes(): LiveData<List<Notes>>


    // delete notes from db
    @Delete
    suspend fun deleteNotes(notes: Notes)
}