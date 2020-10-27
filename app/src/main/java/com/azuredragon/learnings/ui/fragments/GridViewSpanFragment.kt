package com.azuredragon.learnings.ui.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.azuredragon.learnings.R
import com.azuredragon.learnings.databinding.FragmentGridViewSpanBinding
import com.azuredragon.learnings.ktxextensions.dataBindingsLazy
import com.azuredragon.learnings.models.GridItem

class GridViewSpanFragment: Fragment(R.layout.fragment_grid_view_span) {

    private val gridViewSpanBinding: FragmentGridViewSpanBinding by dataBindingsLazy()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val columnCount = 3

        val unitWidthAndHeight = resources.displayMetrics.widthPixels / columnCount

        gridViewSpanBinding.gridLayout.columnCount = columnCount

        for (gridItem in GridItem.DATA_LIST) {
            val cardView = CardView(requireContext()).apply {
                useCompatPadding = true
                setBackgroundColor(ContextCompat.getColor(requireContext(), gridItem.color))
            }

            val layoutParams = GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, gridItem.rowSpan), GridLayout.spec(GridLayout.UNDEFINED, gridItem.columnSpan))
            layoutParams.width = unitWidthAndHeight * gridItem.columnSpan
            layoutParams.height = unitWidthAndHeight * gridItem.rowSpan
            layoutParams.setGravity(Gravity.CENTER)
            cardView.layoutParams = layoutParams

            gridViewSpanBinding.gridLayout.addView(cardView)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = BlankFragment()
    }
}