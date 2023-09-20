package taw.ak.noteyourlife.Database.NotesDatabase

import android.media.Image
import android.speech.tts.Voice
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "note_table")
data class Note(
    var date:String?,
    val title:String?,
    val content:String?,
    @PrimaryKey(autoGenerate = true)
    val id:Int
)