package taw.ak.noteyourlife.Database.ToDoDatabase

import androidx.lifecycle.LiveData

class ToDoRepository(private val toDoDao: ToDoDao) {
    val readAllToDo: LiveData<List<ToDo>> = toDoDao.readAllToDo()
    suspend fun addToDo (toDo: ToDo){
        toDoDao.addToDo(toDo)
    }
    suspend fun deletToDo(toDo: ToDo){
        toDoDao.deleteToDo(toDo)
    }
}