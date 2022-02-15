package com.jonathan.santos.marvelchallenge.presentation

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jonathan.santos.marvelchallenge.CharactersFragment
import com.jonathan.santos.marvelchallenge.FavoritesFragment

class CharactersAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CharactersFragment()
            1 -> FavoritesFragment()
            else -> CharactersFragment()
        }
    }
}