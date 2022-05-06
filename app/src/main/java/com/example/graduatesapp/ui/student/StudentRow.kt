package com.example.graduatesapp.ui.student

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.example.graduatesapp.R
import com.example.graduatesapp.data.models.Student
import com.example.graduatesapp.databinding.RowStudentBinding
import com.example.graduatesapp.utils.toLocalDate
import com.xwray.groupie.viewbinding.BindableItem
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class StudentRow (
    val student: Student
) : BindableItem<RowStudentBinding>() {

    @RequiresApi(Build.VERSION_CODES.O)
    private var dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)

    override fun getLayout(): Int = R.layout.row_student
    override fun initializeViewBinding(view: View): RowStudentBinding = RowStudentBinding.bind(view)

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun bind(viewBinding: RowStudentBinding, position: Int) {
        viewBinding.txtFullName.text = "${student.firstName} ${student.lastName}"
        viewBinding.txtDetails.text = student.birthday.toLocalDate().format(dateFormatter)
    }
}