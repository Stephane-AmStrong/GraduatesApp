package com.example.graduatesapp.ui.graduate

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.example.graduatesapp.MainViewModel
import com.example.graduatesapp.R
import com.example.graduatesapp.data.models.Graduate
import com.example.graduatesapp.data.network.MainApi
import com.example.graduatesapp.data.network.Resource
import com.example.graduatesapp.data.repositories.MainRepository
import com.example.graduatesapp.databinding.DialogFragmentGraduateBinding
import com.example.graduatesapp.ui.base.BaseBottomSheet
import com.example.graduatesapp.utils.handleApiError
import com.example.graduatesapp.utils.snackbar
import com.example.graduatesapp.utils.toast
import com.example.graduatesapp.utils.visible

class GraduateDialogFragment : BaseBottomSheet<MainViewModel, DialogFragmentGraduateBinding, MainRepository>() {
    private var currentGraduate: Graduate? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.toolbar.setTitle(R.string.menu_graduate)

        binding.btnClose.setOnClickListener {
            dismiss()
        }


        binding.toolbar.setOnMenuItemClickListener (toolbarItemClickListener)

        binding.progressBar.visible(false)
        viewModel.graduateSelected.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)

            when (it){
                is Resource.Success ->{
                    updateUIGraduate(it.value)
                }

                is Resource.Failure ->{

                }
            }
        })

        viewModel.graduateRegistered.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)

            when (it) {
                is Resource.Success -> {
                    dismiss()
                    requireView().snackbar(resources.getString(R.string.msg_registration_success))
                }

                is Resource.Failure -> {
                    handleApiError(it) { saveGraduate() }
                }
            }
        })


    }

    private fun saveGraduate(){
        with(binding){
            if (txtName.text.toString().isEmpty()){
                txtName.setError(resources.getString(R.string.error_field_required))
            }

            if (txtName.text.toString().isEmpty()){
                return
            }

            val name = binding.txtName.text.toString().trim()
            val description = binding.txtDescription.text.toString().trim()

            if(currentGraduate == null) {
//                currentGraduate = Graduate(name,description)
            }else{
                currentGraduate.apply {
//                    this?.name = name
//                    this?.description = description
                }
            }

            viewModel.registerGraduate(currentGraduate!!)
        }
    }

    private fun deleteGraduate(){
        if(currentGraduate != null && currentGraduate!!.id != "null") {
            viewModel.deleteGraduate(currentGraduate!!)
            dismiss()
        }else{
            toast(resources.getString(R.string.msg_selection_error, resources.getString(R.string.menu_graduate)))
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

    private val toolbarItemClickListener = Toolbar.OnMenuItemClickListener {
        when (it.itemId) {
            R.id.action_register -> {
                saveGraduate()
                true
            }

            R.id.action_delete -> {
                deleteGraduate()
                true
            }

            R.id.action_cancel -> {
                dismiss()
                true
            }

            else -> false
        }
    }


    private fun updateUIGraduate(graduateSelected: Graduate?) {
        this.currentGraduate = graduateSelected

        with(binding){
//            txtName.setText(currentGraduate?.name)
        }
    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        currentGraduate = null
        viewModel.releaseGraduate()
        viewModel.getGraduates()
    }

    override fun getViewModel() = MainViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = DialogFragmentGraduateBinding.inflate(inflater, container, false)


    override fun getFragmentRepository(): MainRepository {
        val api = remoteDataSource.buildApi(MainApi::class.java, "token")
        return MainRepository(api)
    }

}