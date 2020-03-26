package training.amine.kotpad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.log

class NoteListActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var notes : MutableList<Note>
    lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        notes = mutableListOf<Note>()
        notes.add(Note("Note 1", "Blablabla"))
        notes.add(Note("Mémo Bob", "Grand joueur de basket"))
        notes.add(Note("Mémo Bobette", "Future championne de la WNBA"))
        notes.add(Note("Pourquoi Kotlin ?", "Parce que Java :P"))

        adapter = NoteAdapter(notes, this)
        val recyclerView = findViewById<RecyclerView>(R.id.notes_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onClick(v: View) {
        if (v.tag != null) {
                Log.i("NoteListActivity", "Clic sur une note de la RV")
        }
    }
}
