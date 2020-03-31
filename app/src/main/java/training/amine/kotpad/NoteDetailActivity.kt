package training.amine.kotpad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_note_detail.*

class NoteDetailActivity : AppCompatActivity() {

    companion object {
        val EXTRA_NOTE = "note"
        val EXTRA_NOTE_INDEX = "noteIndex"
        val REQUEST_EDIT_NOTE = 57

        /*Comme on a 2 retours d'intent possible (pour la sauvegarde et pour la suppression) on va les distinguer avec ces 2 actions possibles
        * dans la création de nos intents L.69 et L.92*/
        val ACTION_SAVE_NOTE = "training.amine.kotpad.actions.ACTION_SAVE_NOTE"
        val ACTION_DELETE_NOTE = "training.amine.kotpad.actions.ACTION_DELETE_NOTE"

    }

    lateinit var note: Note
    var noteIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)
        /*On active/affiche notre Toolbar personnalisée*/
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

    /*On ordonne l'affiche des icônes du menu*/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_icon -> {
                saveNote()
                return true
            }
            R.id.delete_icon -> {
                showConfirmDeleteDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {

        note.title = detail_activity_title.text.toString()
        note.text = detail_activity_text.text.toString()
        intent = Intent(ACTION_SAVE_NOTE)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE, note as Parcelable)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, noteIndex)
        /*Répond à la méthode startActivityForResult dans la NoteListActivity*/
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun showConfirmDeleteDialog() {
        val confirmDeleteDialog = ConfirmDeleteNoteDialogFragment(note.title)
        confirmDeleteDialog.listener = object: ConfirmDeleteNoteDialogFragment.ConfirmDeleteDialogListener {
            override fun onDialogPositiveClick() {
                deleteNote()
            }

            override fun onDialogNegativeClick() {
                /*Nothing to do in that case*/
            }
        }
        confirmDeleteDialog.show(supportFragmentManager, "confirmDeleteNoteAlertDialog")
    }

    fun deleteNote() {
        intent = Intent(ACTION_DELETE_NOTE)
        intent.putExtra(EXTRA_NOTE_INDEX, noteIndex)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
