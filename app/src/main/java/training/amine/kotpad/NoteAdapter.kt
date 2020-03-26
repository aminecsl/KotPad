package training.amine.kotpad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

/**
 *Created by Amine K. on 24/03/20.
 */
/*Notre adapter qui fera la configuration de nos item de la RecyclerView.
 *Son constructeur requiert une liste de type Note et un listener pour récupérer le clic sur un item de la RecyclerView.*/
class NoteAdapter (
        val notes: List<Note>,
        val itemClickListener: View.OnClickListener
    ) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    /*Récupère les vues des items de notre RecyclerView*/
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardView = itemView.findViewById<CardView>(R.id.cardview)
        val noteTitle = cardView.findViewById<TextView>(R.id.list_note_title)
        val noteExcerpt = cardView.findViewById<TextView>(R.id.list_note_excerpt)
    }

    /*Transforme le layout d'un item de notre RecyclerView en un objet Kotlin*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return ViewHolder(viewItem)
    }

    /*Met à jour les vues de notre item de la RecyclerView avec les données de notre liste de notes*/
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.cardView.setOnClickListener(itemClickListener)
        holder.cardView.tag = position
        holder.noteTitle.text = note.title
        holder.noteExcerpt.text = note.text
    }

    /*Renvoie le nombre d'item de notre liste de notes*/
    override fun getItemCount(): Int {
        return notes.size
    }
}