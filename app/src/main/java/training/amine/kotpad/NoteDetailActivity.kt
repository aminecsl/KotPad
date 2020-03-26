package training.amine.kotpad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_note_detail.*

class NoteDetailActivity : AppCompatActivity() {

    companion object {
        val EXTRA_NOTE = "note"
        val EXTRA_NOTE_INDEX = "noteIndex"
    }

    lateinit var note: Note
    var noteIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)
        /*On active notre Toolbar personnalisée*/
        setSupportActionBar(detail_activity_toolbar)
        /*On affiche la flèche de retour à l'activity précédente*/
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /*On recupère les datas transmises via l'intent depuis la NoteListActivity*/
        note = intent.getParcelableExtra<Note>(EXTRA_NOTE)
        noteIndex = intent.getIntExtra(EXTRA_NOTE_INDEX, -1)

        /*Nos vues étant du type Editable, on peut pas faire un .text = "string"*/
        detail_activity_title.setText(note.title)
        detail_activity_text.setText(note.text)
    }
}
