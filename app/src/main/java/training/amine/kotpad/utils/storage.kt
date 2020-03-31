package training.amine.kotpad.utils

import android.content.Context
import android.text.TextUtils
import training.amine.kotpad.Note
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

/**
 *Created by Amine K. on 31/03/20.
 */
private val TAG = "storage.kt"

/*Permet de stocker 1 note =  1 fichier unique*/
fun persistNote(context: Context, note: Note) {

    /*Si c'est une nouvelle note, on lui génère un nom de fichier aléatoire unique*/
    if (TextUtils.isEmpty(note.fileName)) {
        note.fileName = UUID.randomUUID().toString() + ".note"
    }
    /*Crée/Récupère le fichier correspondant à la note*/
    val fileOutput = context.openFileOutput(note.fileName, Context.MODE_PRIVATE)
    /*Le transforme en un objet java*/
    val outputStream = ObjectOutputStream(fileOutput)
    /*Ecrit dessus*/
    outputStream.writeObject(note)
}

/*Permet à la NoteListActivity de récupérer la liste de toutes les notes stockées dans des fichers*/
fun getNotesList(context: Context) : MutableList<Note> {
    val notes = mutableListOf<Note>()
    val notesDirectory = context.filesDir
    for (noteFileName in notesDirectory.list()){
        val note = loadNote(context, noteFileName)
        notes.add(note)
    }
    return notes
}

private fun loadNote(context: Context, noteFileName: String) : Note {
    val fileInput = context.openFileInput(noteFileName)
    val inputStream = ObjectInputStream(fileInput)
    val note = inputStream.readObject() as Note
    inputStream.close()
    return note
}

fun deleteNote(context: Context, note: Note) {
    context.deleteFile(note.fileName)
}

