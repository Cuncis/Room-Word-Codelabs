package com.cuncis.roomwordexample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.word_item.view.*

class WordListAdapter internal constructor(context: Context): RecyclerView.Adapter<WordListAdapter.WordViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<Word>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(inflater.inflate(R.layout.word_item, parent, false))
    }

    override fun getItemCount(): Int = words.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = words[position]
        holder.wordItem.text = current.word
    }

    internal fun setWords(words: List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }

    inner class WordViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val wordItem: TextView = itemView.textView
    }

}