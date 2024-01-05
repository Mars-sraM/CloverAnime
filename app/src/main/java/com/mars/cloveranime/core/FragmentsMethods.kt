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
import com.mars.cloveranime.data.model.Server
import com.mars.cloveranime.ui.view.Adapters.EpisodesAdapter
import java.util.Collections

object FragmentsMethods {

    fun reverseOrder(view: Button, adapter: EpisodesAdapter,list: MutableList<Episodes>){
        view.setOnClickListener {
            Collections.reverse(list)
            adapter.notifyDataSetChanged()
        }
    }
    fun hideOption(dialog: Dialog, isVisible: Boolean){
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
        viewLoading.isVisible = isVisible
    }

    fun serverOption(size: Int, dialog: Dialog, list: MutableList<Server>, isVisible: Boolean){
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
                //hideOption(dialog)
                renameOption(dialog,list)
                option1.isVisible = !isVisible
                viewLoading.isVisible = !isVisible
            }
            2 ->{
                //hideOption(dialog)
                renameOption(dialog,list)
                option1.isVisible = !isVisible
                option2.isVisible = !isVisible
                viewLoading.isVisible = isVisible
            }
            3 -> {
                //hideOption(dialog)
                renameOption(dialog,list)
                option1.isVisible = !isVisible
                option2.isVisible = !isVisible
                option3.isVisible = !isVisible
                viewLoading.isVisible = isVisible
            }
            4 -> {
                //hideOption(dialog)
                renameOption(dialog,list)
                option1.isVisible = !isVisible
                option2.isVisible = !isVisible
                option3.isVisible = !isVisible
                option4.isVisible= !isVisible
                viewLoading.isVisible = isVisible
            }
            5 -> {
                //hideOption(dialog)
                renameOption(dialog,list)
                option1.isVisible = !isVisible
                option2.isVisible = !isVisible
                option3.isVisible = !isVisible
                option4.isVisible= !isVisible
                option5.isVisible= !isVisible
                viewLoading.isVisible = isVisible
            }
            6 -> {
                //hideOption(dialog)
                renameOption(dialog,list)
                option1.isVisible = !isVisible
                option2.isVisible = !isVisible
                option3.isVisible = !isVisible
                option4.isVisible= !isVisible
                option5.isVisible= !isVisible
                option6.isVisible= !isVisible
                viewLoading.isVisible = isVisible
            }
            7 -> {
                //hideOption(dialog)
                renameOption(dialog,list)
                option1.isVisible = !isVisible
                option2.isVisible = !isVisible
                option3.isVisible = !isVisible
                option4.isVisible= !isVisible
                option5.isVisible= !isVisible
                option6.isVisible= !isVisible
                option7.isVisible= !isVisible
                viewLoading.isVisible = isVisible
            }
            8 -> {
                //hideOption(dialog)
                renameOption(dialog,list)
                option1.isVisible = !isVisible
                option2.isVisible = !isVisible
                option3.isVisible = !isVisible
                option4.isVisible= !isVisible
                option5.isVisible= !isVisible
                option6.isVisible= !isVisible
                option7.isVisible= !isVisible
                option8.isVisible= !isVisible
                viewLoading.isVisible = isVisible
            }
            9 -> {
                //hideOption(dialog)
                renameOption(dialog,list)
                option1.isVisible = !isVisible
                option2.isVisible = !isVisible
                option3.isVisible = !isVisible
                option4.isVisible= !isVisible
                option5.isVisible= !isVisible
                option6.isVisible= !isVisible
                option7.isVisible= !isVisible
                option8.isVisible= !isVisible
                option9.isVisible= !isVisible
                viewLoading.isVisible = isVisible
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
    fun renameOption(dialog: Dialog, list: MutableList<Server>) {
        val option1: RadioButton = dialog.findViewById(R.id.rbOpcion1)
        val option2: RadioButton = dialog.findViewById(R.id.rbOpcion2)
        val option3: RadioButton = dialog.findViewById(R.id.rbOpcion3)
        val option4: RadioButton = dialog.findViewById(R.id.rbOpcion4)
        val option5: RadioButton = dialog.findViewById(R.id.rbOpcion5)
        val option6: RadioButton = dialog.findViewById(R.id.rbOpcion6)
        val option7: RadioButton = dialog.findViewById(R.id.rbOpcion7)
        val option8: RadioButton = dialog.findViewById(R.id.rbOpcion8)
        val option9: RadioButton = dialog.findViewById(R.id.rbOpcion9)
        when (list.size) {
            1 -> {
                option1.text = list[0].serverName
            }

            2 -> {
                option1.text = list[0].serverName
                option2.text = list[1].serverName
            }

            3 -> {
                option1.text = list[0].serverName
                option2.text = list[1].serverName
                option3.text = list[2].serverName
            }
            4 -> {
                option1.text = list[0].serverName
                option2.text = list[1].serverName
                option3.text = list[2].serverName
                option4.text = list[3].serverName

            }
            5 -> {
                option1.text = list[0].serverName
                option2.text = list[1].serverName
                option3.text = list[2].serverName
                option4.text = list[3].serverName
                option5.text = list[4].serverName
            }
            6 -> {
                option1.text = list[0].serverName
                option2.text = list[1].serverName
                option3.text = list[2].serverName
                option4.text = list[3].serverName
                option5.text = list[4].serverName
                option6.text = list[5].serverName
            }
            7 -> {
                option1.text = list[0].serverName
                option2.text = list[1].serverName
                option3.text = list[2].serverName
                option4.text = list[3].serverName
                option5.text = list[4].serverName
                option6.text = list[5].serverName
                option7.text = list[6].serverName
            }
            8 -> {
                option1.text = list[0].serverName
                option2.text = list[1].serverName
                option3.text = list[2].serverName
                option4.text = list[3].serverName
                option5.text = list[4].serverName
                option6.text = list[5].serverName
                option7.text = list[6].serverName
                option8.text = list[7].serverName
            }
            9 -> {
                option1.text = list[0].serverName
                option2.text = list[1].serverName
                option3.text = list[2].serverName
                option4.text = list[3].serverName
                option5.text = list[4].serverName
                option6.text = list[5].serverName
                option7.text = list[6].serverName
                option8.text = list[7].serverName
                option9.text = list[8].serverName
            }
        }
    }
}