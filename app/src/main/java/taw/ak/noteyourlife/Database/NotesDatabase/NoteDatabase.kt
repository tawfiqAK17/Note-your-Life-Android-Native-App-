package taw.ak.noteyourlife.Database.NotesDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class],version = 1)
abstract class NoteDatabase:RoomDatabase() {
    abstract fun noteDao() : NoteDao
    companion object {
        @Volatile
        private var Instance : NoteDatabase? = null

        fun getDatabase(contex:Context): NoteDatabase {
            val tempInstance = Instance
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    contex.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).build()
                Instance = instance
                return instance
            }
        }
    }

}