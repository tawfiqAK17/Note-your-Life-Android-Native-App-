package taw.ak.noteyourlife.Database.ToDoDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class ToDo(
    val content:String,
    val When : String,
    var isDone : Boolean,
    @PrimaryKey(autoGenerate = true)
    val id : Int
)
