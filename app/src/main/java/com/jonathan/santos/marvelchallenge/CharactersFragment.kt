package com.jonathan.santos.marvelchallenge

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jonathan.santos.marvelchallenge.databinding.ActivityMainBinding
import com.jonathan.santos.marvelchallenge.databinding.CharactersFragmentBinding

class CharactersFragment : Fragment() {

    companion object {
        fun newInstance() = CharactersFragment()
    }

    private lateinit var viewModel: CharactersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: CharactersFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.characters_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CharactersViewModel::class.java)
        // TODO: Use the ViewMode
    }

}