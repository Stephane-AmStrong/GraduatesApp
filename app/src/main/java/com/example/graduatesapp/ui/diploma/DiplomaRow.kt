package com.example.graduatesapp.ui.diploma

import android.view.View
import com.example.graduatesapp.R
import com.example.graduatesapp.data.models.Diploma
import com.example.graduatesapp.databinding.RowDiplomaBinding
import com.xwray.groupie.viewbinding.BindableItem


class DiplomaRow (
    val diploma: Diploma
) : BindableItem<RowDiplomaBinding>() {

//    init {
//        extras[INSET_TYPE_KEY] = INSET
//    }

    override fun getLayout(): Int = R.layout.row_diploma

    override fun initializeViewBinding(view: View): RowDiplomaBinding = RowDiplomaBinding.bind(view)

    override fun bind(viewBinding: RowDiplomaBinding, position: Int) {
        viewBinding.txtName.text = diploma.name
    }
}