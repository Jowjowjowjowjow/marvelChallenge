package com.jonathan.santos.marvelchallenge.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecyclerViewLayoutViewModel :
    ViewModel() {

    private val _recyclerViewLayoutLiveData = MutableLiveData<RecyclerViewLayoutEnum>()
    val recyclerViewLayoutLiveData: LiveData<RecyclerViewLayoutEnum> = _recyclerViewLayoutLiveData

    private var actualRecyclerViewLayout: RecyclerViewLayoutEnum =
        RecyclerViewLayoutEnum.GRID_LAYOUT

    fun getActualRecyclerViewLayout() {
        _recyclerViewLayoutLiveData.postValue(actualRecyclerViewLayout)
    }

    fun updateActualRecyclerViewLayout(recyclerViewLayout: RecyclerViewLayoutEnum) {
        actualRecyclerViewLayout = recyclerViewLayout
    }
}

enum class RecyclerViewLayoutEnum {
    GRID_LAYOUT, LINEAR_LAYOUT
}