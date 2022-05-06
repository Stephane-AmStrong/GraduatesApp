package com.example.graduatesapp.ui.graduate

import android.view.View
import com.example.graduatesapp.R
import com.example.graduatesapp.data.models.Graduate
import com.example.graduatesapp.databinding.RowGraduateBinding
import com.xwray.groupie.viewbinding.BindableItem


class GraduateRow (
    val graduate: Graduate
) : BindableItem<RowGraduateBinding>() {

    override fun getLayout(): Int = R.layout.row_graduate

    override fun initializeViewBinding(view: View): RowGraduateBinding = RowGraduateBinding.bind(view)

    override fun bind(viewBinding: RowGraduateBinding, position: Int) {
//        viewBinding.txtName.text = graduate.name
//        viewBinding.txtDetails.text = graduate.description
    }
}