package com.jonathan.santos.marvelchallenge.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jonathan.santos.marvelchallenge.R
import com.jonathan.santos.marvelchallenge.databinding.CharactersFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersFragment() : Fragment() {

    lateinit var binding: CharactersFragmentBinding

    private val viewModel: CharactersViewModel by viewModel()

    private var isRecyclerViewAsList: Boolean = false

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
        setupRecyclerView()
        setupCharactersAdapterNewList()
        subscribeGetDataFromApi()
        subscribeErrorWhenGettingInfoFromApi()
        setupSwipeRefresh()
        setupRecyclerViewLayoutButton()
    }

    private fun setupRecyclerViewLayoutButton(){
        binding.button.setOnClickListener {
            if(isRecyclerViewAsList) {
                binding.recyclerViewCharacters.layoutManager = GridLayoutManager(context, 2)
            } else {
                binding.recyclerViewCharacters.layoutManager = LinearLayoutManager(context)
            }
            isRecyclerViewAsList = !isRecyclerViewAsList
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewCharacters.apply {
            val characterAdapter = CharactersAdapter()
            characterAdapter.setLoadNextItemsFunction {
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.getCharacters(it)
                }
                binding.progressBarLoadingMoreItems.visibility = View.VISIBLE
            }
            adapter = characterAdapter
        }
    }

    private fun subscribeGetDataFromApi() {
        viewModel.charactersRepositoryResponseLiveData.observe(
            viewLifecycleOwner,
            { charactersDataContainer ->
                (binding.recyclerViewCharacters.adapter as CharactersAdapter).mergeItemsList(
                    charactersDataContainer.results
                )
                binding.progressBarLoadingMoreItems.visibility = View.GONE
            })
    }

    private fun subscribeErrorWhenGettingInfoFromApi() {
        viewModel.errorGettingCharactersRepositoryResponseLiveData.observe(viewLifecycleOwner, {
            Toast.makeText(context, "Erro ao buscar dados - ${it.message}", Toast.LENGTH_SHORT)
                .show()
            binding.progressBarLoadingMoreItems.visibility = View.GONE
        })
    }

    private fun setupSwipeRefresh() {
        binding.pullToRefresh.setOnRefreshListener {
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getCharacters()
            }
        }
    }

    private fun setupCharactersAdapterNewList() {
        viewModel.charactersRepositoryNewResponseLiveData.observe(viewLifecycleOwner, {
            binding.pullToRefresh.isRefreshing = false
            (binding.recyclerViewCharacters.adapter as CharactersAdapter).refresh(it.results.toMutableList())
        })
    }
}
