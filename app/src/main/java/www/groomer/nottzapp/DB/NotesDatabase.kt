package www.groomer.nottzapp.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import www.groomer.nottzapp.Model.Notes

@Database(
    entities = [Notes::class],
    version = 1,
    exportSchema = false
)

abstract class NotesDatabase: RoomDatabase()  {

    abstract fun getNotesDao(): NotesDao

    companion object {
        @Volatile
        private var instance: NotesDatabase? = null
        private val LOCK = Any()

        // Check for DB instance if not null then get or insert or else create new DB Instance
        operator fun invoke(context: Context) = instance?: synchronized(LOCK){

            instance?: createDatabase(context).also{ instance = it }

        }

        // create db instance
        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NotesDatabase::class.java,
            "notes_db.db"
        ).build()


    }

}