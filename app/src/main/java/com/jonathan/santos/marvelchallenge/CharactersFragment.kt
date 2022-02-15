package com.jonathan.santos.marvelchallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jonathan.santos.marvelchallenge.databinding.CharactersFragmentBinding

class CharactersFragment : Fragment() {

    lateinit var binding: CharactersFragmentBinding

    companion object {
        fun newInstance() = CharactersFragment()
    }

    private lateinit var viewModel: CharactersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.characters_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CharactersViewModel::class.java)
        // TODO: Use the ViewMode
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewCharacters.apply {
            val charactersAdapter = CharactersAdapter()
            charactersAdapter.items = listOf(CharacterModel("a"), CharacterModel("b"), CharacterModel("c"))
            adapter = charactersAdapter
        }
    }
}
