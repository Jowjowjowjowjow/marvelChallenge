package com.jonathan.santos.marvelchallenge.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.jonathan.santos.marvelchallenge.R
import com.jonathan.santos.marvelchallenge.databinding.ActivityMainBinding
import com.jonathan.santos.marvelchallenge.presentation.characters.CharactersFragment
import com.jonathan.santos.marvelchallenge.presentation.favorities.FavoritesFragment
import org.koin.android.ext.android.bind

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupToolbar()
        setupFirstFragment()
        setupTabLayout()
    }

    private fun setupToolbar() {
        binding.toolbar.title = binding.tabLayout.getTabAt(0)?.text
        binding.toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(binding.toolbar)
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
                binding.toolbar.title = tab?.text
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
}