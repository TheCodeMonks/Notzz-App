package www.groomer.nottzapp.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "notes"
)
class Notes (
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val title:String?=null,
    val description:String?=null
    )
