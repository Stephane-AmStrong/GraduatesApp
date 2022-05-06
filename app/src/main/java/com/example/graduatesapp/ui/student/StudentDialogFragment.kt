package com.example.graduatesapp.ui.student


import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import com.example.graduatesapp.MainViewModel
import com.example.graduatesapp.R
import com.example.graduatesapp.data.models.Student
import com.example.graduatesapp.data.network.MainApi
import com.example.graduatesapp.data.network.Resource
import com.example.graduatesapp.data.repositories.MainRepository
import com.example.graduatesapp.databinding.DialogFragmentStudentBinding
import com.example.graduatesapp.ui.base.BaseBottomSheet
import com.example.graduatesapp.utils.*
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class StudentDialogFragment :
    BaseBottomSheet<MainViewModel, DialogFragmentStudentBinding, MainRepository>() {
    @RequiresApi(Build.VERSION_CODES.O)
    private var dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
    private val materialDateBuilder = MaterialDatePicker.Builder.datePicker()
    private lateinit var dateRangePicker: MaterialDatePicker<Long>

    private var startAt : Long = MaterialDatePicker.thisMonthInUtcMilliseconds()

    private var currentStudent: Student? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        materialDateBuilder.setSelection(
            startAt
        ).setTitleText(resources.getString(R.string.hint_birthday))

        dateRangePicker = materialDateBuilder.build()

        binding.crdBirthday.setOnClickListener(onClickListener)

        dateRangePicker.addOnPositiveButtonClickListener {
            try {
                binding.txtBirthday.text = it.toLocalDateTime().format(dateFormatter)
                startAt =  it
            }catch (ex : Exception){
                ex.message?.let { it1 -> toast(it1) }
            }
        }

        binding.txtBirthday.text = startAt.toLocalDateTime().format(dateFormatter)


























        binding.toolbar.setTitle(R.string.menu_student)

        binding.btnClose.setOnClickListener {
            dismiss()
        }


        binding.toolbar.setOnMenuItemClickListener(toolbarItemClickListener)

        binding.progressBar.visible(false)
        viewModel.studentSelected.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)

            when (it) {
                is Resource.Success -> {
                    updateUIStudent(it.value)
                }

                is Resource.Failure -> {

                }
            }
        })

        viewModel.studentRegistered.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)

            when (it) {
                is Resource.Success -> {
                    dismiss()
                    requireView().snackbar(resources.getString(R.string.msg_registration_success))
                }

                is Resource.Failure -> {
                    handleApiError(it) { saveStudent() }
                }
            }
        })


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveStudent() {
        with(binding) {
            if (txtFirstName.text.toString().isEmpty()) {
                txtFirstName.error = resources.getString(R.string.error_field_required)
            }

            if (txtLastName.text.toString().isEmpty()) {
                txtLastName.error = resources.getString(R.string.error_field_required)
            }

            if (txtFirstName.text.toString().isEmpty() || txtLastName.text.toString().isEmpty()) {
                return
            }

            val firstName = txtFirstName.text.toString().trim()
            val lastName = txtLastName.text.toString().trim()
            val birthday = startAt.toLocalDateTime()

            if (currentStudent == null) {
                currentStudent = Student(firstName, lastName, birthday.toDate())
            } else {
                currentStudent.apply {
                    this?.firstName = firstName
                    this?.lastName = lastName
                    this?.birthday = birthday.toDate()
                }
            }

            viewModel.registerStudent(currentStudent!!)
        }
    }

    private fun deleteStudent() {
        if (currentStudent != null && currentStudent!!.id != "null") {
            viewModel.deleteStudent(currentStudent!!)
            dismiss()
        } else {
            toast(
                resources.getString(
                    R.string.msg_selection_error,
                    resources.getString(R.string.menu_student)
                )
            )
        }
    }

    private fun clearToolbarMenu() {
        binding.toolbar.menu.clear()
    }

    private fun showToolbarMenu() {
        clearToolbarMenu()
        binding.toolbar.inflateMenu(
            R.menu.main
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private val toolbarItemClickListener = Toolbar.OnMenuItemClickListener {
        when (it.itemId) {
            R.id.action_register -> {
                saveStudent()
                true
            }

            R.id.action_delete -> {
                deleteStudent()
                true
            }

            R.id.action_cancel -> {
                dismiss()
                true
            }

            else -> false
        }
    }


    private val onClickListener = View.OnClickListener {
        when (it.id) {

            R.id.crd_birthday -> {
                dateRangePicker.show(childFragmentManager, "MATERIAL_DATE_PICKER")
                true
            }

            else -> false
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateUIStudent(studentSelected: Student?) {
        this.currentStudent = studentSelected

        with(binding) {
            txtFirstName.setText(currentStudent?.firstName)
            txtLastName.setText(currentStudent?.lastName)
            txtBirthday.text = currentStudent?.birthday?.toLocalDate()?.format(dateFormatter)
        }
    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        currentStudent = null
        viewModel.releaseStudent()
        viewModel.getStudents()
    }

    override fun getViewModel() = MainViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = DialogFragmentStudentBinding.inflate(inflater, container, false)


    override fun getFragmentRepository(): MainRepository {
        val api = remoteDataSource.buildApi(MainApi::class.java, "token")
        return MainRepository(api)
    }

}