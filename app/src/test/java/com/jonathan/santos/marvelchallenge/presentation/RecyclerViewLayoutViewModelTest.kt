package com.jonathan.santos.marvelchallenge.presentation

import androidx.lifecycle.Observer
import io.mockk.*
import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class RecyclerViewLayoutViewModelTest {
    private lateinit var subject: RecyclerViewLayoutViewModel

    private lateinit var recyclerViewLayoutLiveDataObserver: Observer<RecyclerViewLayoutEnum>

    @BeforeEach
    fun beforeEach() {
        recyclerViewLayoutLiveDataObserver = mockk { every { onChanged(any()) } just Runs }
        subject = RecyclerViewLayoutViewModel().apply {
            recyclerViewLayoutLiveData.observeForever(
                recyclerViewLayoutLiveDataObserver
            )
        }
    }

    @Test
    fun `When calling getActualRecyclerViewLayout Should set value for recyclerViewLayoutLiveData`() {
        val expected = RecyclerViewLayoutEnum.GRID_LAYOUT

        subject.getActualRecyclerViewLayout()

        verify { recyclerViewLayoutLiveDataObserver.onChanged(expected) }
        Assert.assertEquals(expected, subject.recyclerViewLayoutLiveData.value)
    }

    @Test
    fun `When calling updateActualRecyclerViewLayout Should update value on getActualRecyclerViewLayout`() {
        val expectedBefore = RecyclerViewLayoutEnum.GRID_LAYOUT
        val expectedAfter = RecyclerViewLayoutEnum.LINEAR_LAYOUT

        subject.getActualRecyclerViewLayout()

        verify { recyclerViewLayoutLiveDataObserver.onChanged(expectedBefore) }
        Assert.assertEquals(expectedBefore, subject.recyclerViewLayoutLiveData.value)

        subject.updateActualRecyclerViewLayout(expectedAfter)

        subject.getActualRecyclerViewLayout()
        verify { recyclerViewLayoutLiveDataObserver.onChanged(expectedAfter) }
        Assert.assertEquals(expectedAfter, subject.recyclerViewLayoutLiveData.value)
    }
}