package taw.ak.noteyourlife.Database.ToDoDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import taw.ak.noteyourlife.Database.NotesDatabase.Note
import taw.ak.noteyourlife.Database.NotesDatabase.NoteDao


@Database(entities = [ToDo::class],version = 1)
abstract class ToDoDatabase: RoomDatabase() {
    abstract fun ToDoDao() : ToDoDao
    companion object {
        @Volatile
        private var Instance : ToDoDatabase? = null

        fun getDatabase(contex: Context): ToDoDatabase {
            val tempInstance = Instance
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    contex.applicationContext,
                    ToDoDatabase::class.java,
                    "ToDo_database"
                ).build()
                Instance = instance
                return instance
            }
        }
    }

}