package taw.ak.noteyourlife.Database

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDao: NoteDao){
    val readAllNotes:LiveData<List<Note>> = noteDao.readAllNotes()
    suspend fun upsertNote (note:Note){
        noteDao.upsertNote(note)
    }
    suspend fun deletNote(note:Note){
        noteDao.deletNote(note)
    }
}