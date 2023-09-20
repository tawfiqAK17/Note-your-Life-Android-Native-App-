package taw.ak.noteyourlife.Database.ToDoDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import taw.ak.noteyourlife.Database.NotesDatabase.Note
import taw.ak.noteyourlife.Database.NotesDatabase.NoteDatabase
import taw.ak.noteyourlife.Database.NotesDatabase.NoteRepository

class ToDoViewModel(application: Application): AndroidViewModel(application)  {
    val readAllToDo: LiveData<List<ToDo>>
    private val repository: ToDoRepository

    init {
        val ToDoDao = ToDoDatabase.getDatabase(application).ToDoDao()
        repository = ToDoRepository(ToDoDao)
        readAllToDo = repository.readAllToDo
    }

    fun addToDo(toDo: ToDo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToDo(toDo)
        }
    }
    fun deletToDo(toDo: ToDo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletToDo(toDo)
        }
    }
}