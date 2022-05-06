package com.example.graduatesapp.ui.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduatesapp.MainViewModel
import com.example.graduatesapp.R
import com.example.graduatesapp.data.models.Student
import com.example.graduatesapp.data.network.MainApi
import com.example.graduatesapp.data.network.Resource
import com.example.graduatesapp.data.repositories.MainRepository
import com.example.graduatesapp.databinding.FragmentStudentBinding
import com.example.graduatesapp.ui.base.BaseFragment
import com.example.graduatesapp.utils.handleApiError
import com.example.graduatesapp.utils.visible
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.OnItemLongClickListener

class StudentFragment : BaseFragment<MainViewModel, FragmentStudentBinding, MainRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getStudents()

        binding.include.progressBar.visible(false)
        viewModel.students.observe(viewLifecycleOwner, Observer {
            binding.include.progressBar.visible(it is Resource.Loading)
            binding.include.swipeRefreshLayout.isRefreshing = it is Resource.Loading

            when (it) {
                is Resource.Success -> {
                    updateUI(it.value)
                }

                is Resource.Failure -> handleApiError(it) {

                }
            }
        })

        binding.fab.setOnClickListener(onClickListener)

        binding.include.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getStudents()
        }
    }

    private fun updateUI(students: List<Student>) {

        with(binding) {
            val mAdapter = GroupAdapter<GroupieViewHolder>().apply {
                addAll(students.toUserRow())
                setOnItemClickListener(onItemClickListener)
            }

            include.recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = mAdapter

            }
        }
    }

    override fun getViewModel() = MainViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentStudentBinding.inflate(inflater, container, false)


    override fun getFragmentRepository(): MainRepository {
        val api = remoteDataSource.buildApi(MainApi::class.java, "token")
        return MainRepository(api)
    }


    private val onItemClickListener = OnItemClickListener { row, view ->
        if (row is StudentRow && row.student.firstName.isNotEmpty()) {
            viewModel.setStudent(row.student)
        }

        parentFragment?.let { parent ->
            StudentDialogFragment().show(
                parent.childFragmentManager,
                tag
            )
        }

    }

    private val onItemLongClickListener = OnItemLongClickListener { item, _ ->

        false
    }

    private fun List<Student>.toUserRow(): List<StudentRow> {
        return this.map {
            StudentRow(it)
        }
    }


    private val onClickListener = View.OnClickListener {
        when (it.id) {
            R.id.fab -> {
                parentFragment?.let { parent ->
                    StudentDialogFragment().show(
                        parent.childFragmentManager,
                        tag
                    )
                }
                true
            }


            else -> false
        }
    }

}