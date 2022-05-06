package com.example.graduatesapp.ui.diploma

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.example.graduatesapp.MainViewModel
import com.example.graduatesapp.R
import com.example.graduatesapp.data.models.Diploma
import com.example.graduatesapp.data.network.MainApi
import com.example.graduatesapp.data.network.Resource
import com.example.graduatesapp.data.repositories.MainRepository
import com.example.graduatesapp.databinding.DialogFragmentDimplomaBinding
import com.example.graduatesapp.ui.base.BaseBottomSheet
import com.example.graduatesapp.utils.handleApiError
import com.example.graduatesapp.utils.snackbar
import com.example.graduatesapp.utils.toast
import com.example.graduatesapp.utils.visible

class DiplomaDialogFragment : BaseBottomSheet<MainViewModel, DialogFragmentDimplomaBinding, MainRepository>() {
    private var currentDiploma: Diploma? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.toolbar.setTitle(R.string.menu_diploma)

        binding.btnClose.setOnClickListener {
            dismiss()
        }


        binding.toolbar.setOnMenuItemClickListener (toolbarItemClickListener)

        binding.progressBar.visible(false)
        viewModel.diplomaSelected.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)

            when (it){
                is Resource.Success ->{
                    updateUIDiploma(it.value)
                }

                is Resource.Failure ->{

                }
            }
        })

        viewModel.diplomaRegistered.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)

            when (it) {
                is Resource.Success -> {
                    dismiss()
                    requireView().snackbar(resources.getString(R.string.msg_registration_success))
                }

                is Resource.Failure -> {
                    handleApiError(it) { saveDiploma() }
                }
            }
        })


    }

    private fun saveDiploma(){
        with(binding){
            if (txtName.text.toString().isEmpty()){
                txtName.setError(resources.getString(R.string.error_field_required))
            }

            if (txtName.text.toString().isEmpty()){
                return
            }

            val name = binding.txtName.text.toString().trim()
            val description = binding.txtDescription.text.toString().trim()

            if(currentDiploma == null) {
                currentDiploma = Diploma(name,description)
            }else{
                currentDiploma.apply {
                    this?.name = name
                    this?.description = description
                }
            }

            viewModel.registerDiploma(currentDiploma!!)
        }
    }

    private fun deleteDiploma(){
        if(currentDiploma != null && currentDiploma!!.id != "null") {
            viewModel.deleteDiploma(currentDiploma!!)
            dismiss()
        }else{
            toast(resources.getString(R.string.msg_selection_error, resources.getString(R.string.menu_diploma)))
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
                saveDiploma()
                true
            }

            R.id.action_delete -> {
                deleteDiploma()
                true
            }

            R.id.action_cancel -> {
                dismiss()
                true
            }

            else -> false
        }
    }


    private fun updateUIDiploma(diplomaSelected: Diploma?) {
        this.currentDiploma = diplomaSelected

        with(binding){
            txtName.setText(currentDiploma?.name)
        }
    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        currentDiploma = null
        viewModel.releaseDiploma()
        viewModel.getDiplomas()
    }

    override fun getViewModel() = MainViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = DialogFragmentDimplomaBinding.inflate(inflater, container, false)


    override fun getFragmentRepository(): MainRepository {
        val api = remoteDataSource.buildApi(MainApi::class.java, "token")
        return MainRepository(api)
    }

}