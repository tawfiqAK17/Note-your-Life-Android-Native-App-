package taw.ak.noteyourlife.Database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application):AndroidViewModel(application) {

    val readAllNotes:LiveData<List<Note>>
    private val repository:NoteRepository

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
    fun deletNote(note:Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletNote(note)
        }
    }
}