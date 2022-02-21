package com.jonathan.santos.marvelchallenge.presentation.characters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.jonathan.santos.marvelchallenge.R
import com.jonathan.santos.marvelchallenge.databinding.CharactersItemBinding
import com.jonathan.santos.marvelchallenge.model.Character
import com.jonathan.santos.marvelchallenge.presentation.charactersDetails.CharactersDetailsActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.koin.java.KoinJavaComponent

class CharactersAdapter() : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    /*TODO: Buscar cada personagem no banco através do id, se existir, preencher o ícone de favorito,
       se não, deixar como é.
    */

    val picasso: Picasso by KoinJavaComponent.inject(Picasso::class.java)

    var items: MutableList<Character> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var actualOffset = 0

    private var loadNextItems: (Int) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterViewHolder {
        val view = CharactersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        if (position >= itemCount - 1) {
            loadNextPage()
        }
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setLoadNextItemsFunction(loadFunction: (Int) -> Unit) {
        loadNextItems = loadFunction
        loadNextPage()
    }

    fun mergeItemsList(list: List<Character>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    private fun loadNextPage() {
        actualOffset += 20
        loadNextItems.invoke(actualOffset)
    }

    fun refresh(newList: MutableList<Character>) {
        actualOffset = 0
        items = newList
        notifyDataSetChanged()
    }

    inner class CharacterViewHolder(private val binding: CharactersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            with(binding) {
                characterCard.setOnClickListener {
                    binding.root.context.startActivity(Intent(binding.root.context, CharactersDetailsActivity::class.java).apply {
                        putExtra("Character", character)
                    })
                }
                itemCharacterTitle.text = character.name
                itemCharacterTitle.isSelected = true
                itemCharacterFavorite.setImageDrawable(
                    AppCompatResources.getDrawable(
                        binding.root.context,
                        R.drawable.ic_favorite_star
                    )
                )
                itemCharacterFavorite.setOnClickListener {
                    itemCharacterFavorite.isSelected = !itemCharacterFavorite.isSelected
                }
                val pictureLink =
                    "${character.thumbnail.path}.${character.thumbnail.extension}".replace(
                        INSECURE_PROTOCOL, SECURE_PROTOCOL
                    )
                picasso
                    .load(pictureLink)
                    .resize(IMAGE_SIZE_PX, IMAGE_SIZE_PX)
                    .into(binding.imageViewCharacterPhoto, object : Callback {
                        override fun onSuccess() {
                            progressBar.visibility = View.GONE
                        }

                        override fun onError(e: Exception?) {
                            progressBar.visibility = View.GONE
                        }
                    })
            }
        }
    }

    companion object {
        const val IMAGE_SIZE_PX = 300
        const val INSECURE_PROTOCOL = "http://"
        const val SECURE_PROTOCOL = "https://"
    }
}
