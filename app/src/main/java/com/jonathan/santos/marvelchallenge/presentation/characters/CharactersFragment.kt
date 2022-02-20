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
import com.jonathan.santos.marvelchallenge.R
import com.jonathan.santos.marvelchallenge.databinding.CharactersFragmentBinding
import com.jonathan.santos.marvelchallenge.presentation.RecyclerViewLayoutEnum
import com.jonathan.santos.marvelchallenge.presentation.RecyclerViewLayoutViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersFragment() : Fragment() {

    lateinit var binding: CharactersFragmentBinding

    private val viewModel: CharactersViewModel by viewModel()
    private val recyclerViewLayoutViewModel: RecyclerViewLayoutViewModel by sharedViewModel()

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
        subscribeRecyclerViewLayout()
        setupSwipeRefresh()
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

    private fun subscribeRecyclerViewLayout() {
        recyclerViewLayoutViewModel.recyclerViewLayoutLiveData.observe(viewLifecycleOwner,
            { recyclerViewLayout ->
                when (recyclerViewLayout) {
                    RecyclerViewLayoutEnum.GRID_LAYOUT -> binding.recyclerViewCharacters.layoutManager =
                        GridLayoutManager(context, 2)
                    RecyclerViewLayoutEnum.LINEAR_LAYOUT -> binding.recyclerViewCharacters.layoutManager =
                        LinearLayoutManager(context)
                    else -> binding.recyclerViewCharacters.layoutManager =
                        GridLayoutManager(context, 2)
                }
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
        viewModel.charactersRepositoryFreshResponseLiveData.observe(viewLifecycleOwner, {
            binding.pullToRefresh.isRefreshing = false
            (binding.recyclerViewCharacters.adapter as CharactersAdapter).refresh(it.results.toMutableList())
        })
    }
}
