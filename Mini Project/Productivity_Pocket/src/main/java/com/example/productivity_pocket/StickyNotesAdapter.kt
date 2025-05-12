package com.example.productivity_pocket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.productivity_pocket.R

class StickyNotesAdapter(private val stickyNotesList: MutableList<String>) : RecyclerView.Adapter<StickyNotesAdapter.StickyNoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickyNoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sticky_note_item, parent, false)
        return StickyNoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: StickyNoteViewHolder, position: Int) {
        holder.bind(stickyNotesList[position])
    }

    override fun getItemCount(): Int {
        return stickyNotesList.size
    }

    fun addNote(note: String) {
        stickyNotesList.add(note)
        notifyItemInserted(stickyNotesList.size - 1)
    }

    class StickyNoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val noteTextView: TextView = itemView.findViewById(R.id.noteTextView)

        fun bind(note: String) {
            noteTextView.text = note
        }
    }
}
