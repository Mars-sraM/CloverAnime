package com.mars.cloveranime.core

import android.app.Dialog
import android.widget.Adapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mars.cloveranime.R
import com.mars.cloveranime.data.model.Episodes
import com.mars.cloveranime.ui.view.Adapters.EpisodesAdapter
import java.util.Collections

object FragmentsMethods {

    fun reverseOrder(view: Button, adapter: EpisodesAdapter,list: MutableList<Episodes>){
        view.setOnClickListener {
            Collections.reverse(list)
            adapter.notifyDataSetChanged()
        }
    }
    fun serverOption(size: Int, dialog: Dialog){
        val option1: RadioButton = dialog.findViewById(R.id.rbOpcion1)
        val option2: RadioButton = dialog.findViewById(R.id.rbOpcion2)
        val option3: RadioButton = dialog.findViewById(R.id.rbOpcion3)
        val option4: RadioButton = dialog.findViewById(R.id.rbOpcion4)
        val option5: RadioButton = dialog.findViewById(R.id.rbOpcion5)
        val option6: RadioButton = dialog.findViewById(R.id.rbOpcion6)
        val option7: RadioButton = dialog.findViewById(R.id.rbOpcion7)
        val option8: RadioButton = dialog.findViewById(R.id.rbOpcion8)
        val option9: RadioButton = dialog.findViewById(R.id.rbOpcion9)
        val viewLoading: LinearLayout = dialog.findViewById(R.id.viewLoading)
        when(size){
            1 ->{
                hideOption(dialog)
                option1.isVisible = true
                viewLoading.isVisible = false
            }
            2 ->{
                hideOption(dialog)
                option1.isVisible = true
                option2.isVisible = true
                viewLoading.isVisible = false
            }
            3 -> {
                hideOption(dialog)
                option1.isVisible = true
                option2.isVisible = true
                option3.isVisible = true
                viewLoading.isVisible = false
            }
            4 -> {
                hideOption(dialog)
                option1.isVisible = true
                option2.isVisible = true
                option3.isVisible = true
                option4.isVisible= true
                viewLoading.isVisible = false
            }
            5 -> {
                hideOption(dialog)
                option1.isVisible = true
                option2.isVisible = true
                option3.isVisible = true
                option4.isVisible= true
                option5.isVisible= true
                viewLoading.isVisible = false
            }
            6 -> {
                hideOption(dialog)
                option1.isVisible = true
                option2.isVisible = true
                option3.isVisible = true
                option4.isVisible= true
                option5.isVisible= true
                option6.isVisible= true
                viewLoading.isVisible = false
            }
            7 -> {
                hideOption(dialog)
                option1.isVisible = true
                option2.isVisible = true
                option3.isVisible = true
                option4.isVisible= true
                option5.isVisible= true
                option6.isVisible= true
                option7.isVisible= true
                viewLoading.isVisible = false
            }
            8 -> {
                hideOption(dialog)
                option1.isVisible = true
                option2.isVisible = true
                option3.isVisible = true
                option4.isVisible= true
                option5.isVisible= true
                option6.isVisible= true
                option7.isVisible= true
                option8.isVisible= true
                viewLoading.isVisible = false
            }
            9 -> {
                hideOption(dialog)
                option1.isVisible = true
                option2.isVisible = true
                option3.isVisible = true
                option4.isVisible= true
                option5.isVisible= true
                option6.isVisible= true
                option7.isVisible= true
                option8.isVisible= true
                option9.isVisible= true
                viewLoading.isVisible = false
            }
            else -> {
                option1.isVisible = false
                option2.isVisible = false
                option3.isVisible = false
                option4.isVisible= false
                option5.isVisible= false
                option6.isVisible= false
                option7.isVisible= false
                option8.isVisible= false
                option9.isVisible= false
            }
        }
    }
    fun hideOption(dialog: Dialog){
        val viewLoading: LinearLayout = dialog.findViewById(R.id.viewLoading)
        val option1: RadioButton = dialog.findViewById(R.id.rbOpcion1)
        val option2: RadioButton = dialog.findViewById(R.id.rbOpcion2)
        val option3: RadioButton = dialog.findViewById(R.id.rbOpcion3)
        val option4: RadioButton = dialog.findViewById(R.id.rbOpcion4)
        val option5: RadioButton = dialog.findViewById(R.id.rbOpcion5)
        val option6: RadioButton = dialog.findViewById(R.id.rbOpcion6)
        val option7: RadioButton = dialog.findViewById(R.id.rbOpcion7)
        val option8: RadioButton = dialog.findViewById(R.id.rbOpcion8)
        val option9: RadioButton = dialog.findViewById(R.id.rbOpcion9)

        option1.isVisible = false
        option2.isVisible = false
        option3.isVisible = false
        option4.isVisible= false
        option5.isVisible= false
        option6.isVisible= false
        option7.isVisible= false
        option8.isVisible= false
        option9.isVisible= false
        viewLoading.isVisible = true
    }
}