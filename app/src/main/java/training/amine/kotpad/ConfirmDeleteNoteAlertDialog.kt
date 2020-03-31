package training.amine.kotpad

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

/**
 *Created by Amine K. on 27/03/20.
 */
class ConfirmDeleteNoteDialogFragment (val noteTitle: String = "") : DialogFragment() {

    interface ConfirmDeleteDialogListener {
        fun onDialogPositiveClick ()
        fun onDialogNegativeClick()
    }

    var listener: ConfirmDeleteDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        builder.setMessage("Êtes-vous sûr de vouloir supprimer la note \"$noteTitle\" ?")
            .setPositiveButton("Supprimer",
                DialogInterface.OnClickListener { dialog, id -> listener?.onDialogPositiveClick()  }
            )
            .setNegativeButton("Annuler",
                DialogInterface.OnClickListener { dialog, id ->  listener?.onDialogNegativeClick() }
            )
        return builder.create()

    }
}