package com.example.graduatesapp.ui.student

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
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

class StudentFragment : BaseFragment<MainViewModel, FragmentStudentBinding, MainRepository>(), SearchView.OnQueryTextListener {

    private lateinit var students: List<Student>
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getStudents()

        binding.include.progressBar.visible(false)
        viewModel.students.observe(viewLifecycleOwner, Observer {
            binding.include.progressBar.visible(it is Resource.Loading)
            binding.include.swipeRefreshLayout.isRefreshing = it is Resource.Loading

            when (it) {
                is Resource.Success -> {
                    students = it.value
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.with_search, menu)
        val search = menu.findItem(R.id.action_search)
        val searchView = search.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
    }


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

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            updateUI(students.filter { it.firstName.contains(query, true) || it.lastName.contains(query, true)})
        }else{
            updateUI(students)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null){
            updateUI(students.filter { it.firstName.contains(newText, true) || it.lastName.contains(newText, true)})
        }else{
            updateUI(students)
        }
        return true
    }

}