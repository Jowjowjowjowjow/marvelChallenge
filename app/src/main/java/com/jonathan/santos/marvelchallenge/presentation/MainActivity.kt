package com.jonathan.santos.marvelchallenge.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.jonathan.santos.marvelchallenge.R
import com.jonathan.santos.marvelchallenge.databinding.ActivityMainBinding
import com.jonathan.santos.marvelchallenge.presentation.characters.CharactersFragment
import com.jonathan.santos.marvelchallenge.presentation.favorities.FavoritesFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val recyclerViewLayoutViewModel by viewModel<RecyclerViewLayoutViewModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupToolbar()
        setupFirstFragment()
        setupTabLayout()
        subscribeRecyclerViewLayout()
        setupRecyclerViewLayoutButton()
    }

    private fun setupToolbar() {
        binding.toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(binding.toolbar)
        binding.title.text = binding.tabLayout.getTabAt(0)?.text
    }

    private fun setupTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, CharactersFragment()).commit()
                    }
                    1 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, FavoritesFragment()).commit()
                    }
                }
                binding.title.text = tab?.text
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun setupFirstFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, CharactersFragment()).commit()
    }
    private fun setupRecyclerViewLayoutButton() {
        binding.buttonRecyclerViewLayout.setOnClickListener {
            recyclerViewLayoutViewModel.getActualRecyclerViewLayout()
        }
    }

    private fun subscribeRecyclerViewLayout() {
        recyclerViewLayoutViewModel.recyclerViewLayoutLiveData.observe(this, { recyclerViewLayout ->
            when (recyclerViewLayout) {
                RecyclerViewLayoutEnum.GRID_LAYOUT -> {
                    recyclerViewLayoutViewModel.updateActualRecyclerViewLayout(
                        RecyclerViewLayoutEnum.LINEAR_LAYOUT
                    )
                    binding.buttonRecyclerViewLayout.setImageDrawable(
                        AppCompatResources.getDrawable(
                            this,
                            R.drawable.ic_list
                        )
                    )
                }
                RecyclerViewLayoutEnum.LINEAR_LAYOUT -> {
                    recyclerViewLayoutViewModel.updateActualRecyclerViewLayout(
                        RecyclerViewLayoutEnum.GRID_LAYOUT
                    )
                    binding.buttonRecyclerViewLayout.setImageDrawable(
                        AppCompatResources.getDrawable(
                            this,
                            R.drawable.ic_grid
                        )
                    )
                }
                else -> {
                    recyclerViewLayoutViewModel.updateActualRecyclerViewLayout(
                        RecyclerViewLayoutEnum.GRID_LAYOUT
                    )
                    binding.buttonRecyclerViewLayout.setImageDrawable(
                        AppCompatResources.getDrawable(
                            this,
                            R.drawable.ic_grid
                        )
                    )
                }
            }
        })
    }

}