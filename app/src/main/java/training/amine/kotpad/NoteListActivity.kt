package training.amine.kotpad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_note_list.*
import training.amine.kotpad.utils.deleteNote
import training.amine.kotpad.utils.getNotesList
import training.amine.kotpad.utils.persistNote

/*On implémente une interface de callback afin de pouvoir placer un listener ou on veut et de réagir avec une seule méthode onClick(view)*/
class NoteListActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var notes : MutableList<Note>
    lateinit var adapter: NoteAdapter
    lateinit var coordinatorLayout: CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        /*On active notre Toolbar personnalisée*/
        setSupportActionBar(list_activity_toolbar)

        /*On place un listener sur notre floating action button*/
        list_activity_fab.setOnClickListener(this)

        /*On initialise notre liste de notes et on lui ajoute plusieurs éléments*/
        notes = getNotesList(this)

        /*On initialise l'adapter de notre RecyclerView*/
        adapter = NoteAdapter(notes, this)
        /*On récupère la vue de notre RecyclerView*/
        val recyclerView = findViewById<RecyclerView>(R.id.notes_recycler_view)
        /*On paramètre le type de RecyclerView voulue (verticale)*/
        recyclerView.layoutManager = LinearLayoutManager(this)
        /*On lui assigne l'instance de notre adapter*/
        recyclerView.adapter = adapter

        coordinatorLayout = findViewById<CoordinatorLayout>(R.id.coordinator_layout)
    }

    override fun onClick(v: View) {
        if (v.tag != null) {
            launchNoteDetail(v.tag as Int)
        }
        else {
            when (v.id) {
                R.id.list_activity_fab -> createNewNote()
            }
        }
    }

    private fun createNewNote() {
        launchNoteDetail(-1)
    }

    private fun launchNoteDetail(noteIndex: Int) {
        /*Si noteIndex <0 on crée une nouvelle Note, sinon on récupère la note située à la position noteIndex dans la liste notes*/
        val note = if (noteIndex < 0) Note() else notes[noteIndex]
        val intent = Intent(this, NoteDetailActivity::class.java)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE, note as Parcelable)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, noteIndex)
        startActivityForResult(intent, NoteDetailActivity.REQUEST_EDIT_NOTE)
    }

    /*Répond à la méthode setResult dans la NoteDetailActivity*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK || data == null) {
            return super.onActivityResult(requestCode, resultCode, data)
        }
        else {
            when (requestCode) {
                NoteDetailActivity.REQUEST_EDIT_NOTE -> updateNoteListWithResult(data)
            }
        }
    }

    private fun updateNoteListWithResult(data: Intent) {
        val index = data.getIntExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, -1)
        val note = data.getParcelableExtra<Note>(NoteDetailActivity.EXTRA_NOTE)
        when (data.action) {
            NoteDetailActivity.ACTION_SAVE_NOTE -> {
                if (index < 0) {
                    /*On ajoute la nouvelle note à la liste*/
                    notes.add(note)
                }
                else {
                    /*On remplace l'ancienne version de la note dans la liste par la nouvelle version*/
                    notes[index] = note
                }
                persistNote(this, note)
            }
            NoteDetailActivity.ACTION_DELETE_NOTE -> {
                if (index < 0) {
                    return
                }
                else {
                    val note = notes.removeAt(index)
                    deleteNote(this, note)
                    Snackbar.make(coordinatorLayout, "${note.title} supprimé", Snackbar.LENGTH_LONG).show()
                }
            }
        }
        /*On demande à l'adapter de se rafraîchir avec la nouvelle liste et de mettre à jour la RecyclerView*/
        adapter.notifyDataSetChanged()
    }
}
