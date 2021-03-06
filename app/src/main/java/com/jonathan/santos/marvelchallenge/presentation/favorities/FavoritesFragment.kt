package com.jonathan.santos.marvelchallenge.presentation.favorities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jonathan.santos.marvelchallenge.R
import com.jonathan.santos.marvelchallenge.databinding.FavoritesFragmentBinding

class FavoritesFragment : Fragment() {

    /*TODO: Montar um mapeamento do objeto Character no banco, escolher entre armazenar a imagem como blob ou apenas o path e também salvar a imagem localmente
      Daria pra reaproveitar até o mesmo item de layout
     */
    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private lateinit var viewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FavoritesFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.favorites_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}