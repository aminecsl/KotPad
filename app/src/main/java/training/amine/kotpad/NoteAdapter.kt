package training.amine.kotpad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

/**
 *Created by Amine K. on 24/03/20.
 */
class NoteAdapter (val notes: List<Note>, val itemClickListener: View.OnClickListener) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardView = itemView.findViewById<CardView>(R.id.cardview)
        val noteTitle = cardView.findViewById<TextView>(R.id.list_note_title)
        val noteExcerpt = cardView.findViewById<TextView>(R.id.list_note_excerpt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.cardView.setOnClickListener(itemClickListener)
        holder.cardView.tag = position
        holder.noteTitle.text = note.title
        holder.noteExcerpt.text = note.text
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}