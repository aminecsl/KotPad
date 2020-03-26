package training.amine.kotpad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_note_list.*
import kotlin.math.log

class NoteListActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var notes : MutableList<Note>
    lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        /*On active notre Toolbar personnalisée*/
        setSupportActionBar(list_activity_toolbar)

        /*On initialise notre liste de notes et on lui ajoute plusieurs éléments*/
        notes = mutableListOf<Note>()
        notes.add(Note("Note 1", "Blablabla"))
        notes.add(Note("Mémo Bob", "Grand joueur de basket"))
        notes.add(Note("Mémo Bobette", "Future championne de la WNBA"))
        notes.add(Note("Pourquoi Kotlin ?", "Parce que Java :P"))

        /*On initialise l'adapter de notre RecyclerView*/
        adapter = NoteAdapter(notes, this)
        /*On récupère la vue de notre RecyclerView*/
        val recyclerView = findViewById<RecyclerView>(R.id.notes_recycler_view)
        /*On paramètre le type de RecyclerView voulue (verticale)*/
        recyclerView.layoutManager = LinearLayoutManager(this)
        /*On lui assigne l'instance de notre adapter*/
        recyclerView.adapter = adapter
    }


    override fun onClick(v: View) {
        if (v.tag != null) {
            Log.i("NoteListActivity", "Clic sur une note de la RV")
            showNoteDetail(v.tag as Int)
        }
    }

    fun showNoteDetail(noteIndex: Int) {
        val note = notes[noteIndex]
        val intent = Intent(this, NoteDetailActivity::class.java)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE, note)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, noteIndex)
        startActivity(intent)
    }
}
