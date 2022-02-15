package com.jonathan.santos.marvelchallenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.jonathan.santos.marvelchallenge.databinding.CharactersItemBinding

class CharactersAdapter() : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    var items: List<CharacterModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharactersAdapter.CharacterViewHolder {
        val view = CharactersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharactersAdapter.CharacterViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class CharacterViewHolder(private val binding: CharactersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: CharacterModel) {
            with(binding) {
                itemCharacterTitle.text = character.name
                itemCharacterFavorite.setImageDrawable(AppCompatResources.getDrawable(binding.root.context, R.drawable.ic_favorite_star))
                itemCharacterFavorite.setOnClickListener {
                    itemCharacterFavorite.isSelected = !itemCharacterFavorite.isSelected
                }
            }
        }
    }
}
