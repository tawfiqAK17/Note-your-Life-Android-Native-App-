package taw.ak.noteyourlife.Database.NotesDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application):AndroidViewModel(application) {

    var copyOfNote : Note? = null
    val readAllNotes:LiveData<List<Note>>
    private val repository: NoteRepository

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
        readAllNotes = repository.readAllNotes
    }

    fun upsertNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.upsertNote(note)
        }
    }
    fun deletNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletNote(note)
        }
    }
    fun copyNote(note: Note){
        copyOfNote = note.copy(id = 0)
    }
    fun deletCopy(){
        copyOfNote = null
    }
}