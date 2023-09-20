package taw.ak.noteyourlife.Database.ToDoDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import taw.ak.noteyourlife.Database.NotesDatabase.Note

@Dao
interface ToDoDao {
    @Upsert
    suspend fun addToDo(todo:ToDo)
    @Delete
    suspend fun deleteToDo(todo: ToDo)
    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun readAllToDo(): LiveData<List<ToDo>>
}