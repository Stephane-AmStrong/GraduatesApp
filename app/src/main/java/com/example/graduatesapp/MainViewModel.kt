package com.example.graduatesapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.graduatesapp.data.models.Diploma
import com.example.graduatesapp.data.models.Graduate
import com.example.graduatesapp.data.models.Student
import com.example.graduatesapp.data.network.Resource
import com.example.graduatesapp.data.repositories.MainRepository
import com.example.graduatesapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class MainViewModel (
    private val repository: MainRepository,
) : BaseViewModel(repository) {


    //Diplomas
    private val _diploma: MutableLiveData<Resource<Diploma>> = MutableLiveData()
    private val _diplomas: MutableLiveData<Resource<List<Diploma>>> = MutableLiveData()
    private val _diplomaSelected: MutableLiveData<Resource<Diploma?>> = MutableLiveData()
    private val _diplomaRegistered: MutableLiveData<Resource<Diploma?>> = MutableLiveData()

    //Graduates
    private val _graduate: MutableLiveData<Resource<Graduate>> = MutableLiveData()
    private val _graduates: MutableLiveData<Resource<List<Graduate>>> = MutableLiveData()
    private val _graduateSelected: MutableLiveData<Resource<Graduate?>> = MutableLiveData()
    private val _graduateRegistered: MutableLiveData<Resource<Graduate?>> = MutableLiveData()

    //Students
    private val _student: MutableLiveData<Resource<Student>> = MutableLiveData()
    private val _students: MutableLiveData<Resource<List<Student>>> = MutableLiveData()
    private val _studentSelected: MutableLiveData<Resource<Student?>> = MutableLiveData()
    private val _studentRegistered: MutableLiveData<Resource<Student?>> = MutableLiveData()





    //Diploma
    val diploma: LiveData<Resource<Diploma>>
        get() = _diploma

    val diplomas: LiveData<Resource<List<Diploma>>>
        get() = _diplomas

    val diplomaSelected: LiveData<Resource<Diploma?>>
        get() = _diplomaSelected

    val diplomaRegistered: LiveData<Resource<Diploma?>>
        get() = _diplomaRegistered
    
    

    //Graduate
    val graduate: LiveData<Resource<Graduate>>
        get() = _graduate

    val graduates: LiveData<Resource<List<Graduate>>>
        get() = _graduates

    val graduateSelected: LiveData<Resource<Graduate?>>
        get() = _graduateSelected

    val graduateRegistered: LiveData<Resource<Graduate?>>
        get() = _graduateRegistered



    //Student
    val student: LiveData<Resource<Student>>
        get() = _student

    val students: LiveData<Resource<List<Student>>>
        get() = _students

    val studentSelected: LiveData<Resource<Student?>>
        get() = _studentSelected

    val studentRegistered: LiveData<Resource<Student?>>
        get() = _studentRegistered








    //Diplomas
    fun setDiploma(diploma:Diploma?) = viewModelScope.launch {
        _diplomaSelected.value = Resource.Loading
        _diplomaSelected.value = repository.setDiploma(diploma)
    }

    fun getDiploma(id: String) = viewModelScope.launch {
        _diploma.value = Resource.Loading
        _diploma.value = repository.getDiploma(id)
    }

    fun getDiplomas() = viewModelScope.launch {
        _diplomas.value = Resource.Loading
        _diplomas.value = repository.getDiplomas()
    }

    fun registerDiploma(diploma: Diploma) = viewModelScope.launch {
        _diplomaRegistered.value = Resource.Loading
        _diplomaRegistered.value = repository.registerDiploma(diploma)
        _diplomas.value = repository.getDiplomas()
        _diplomas.value = repository.getDiplomas()
    }

    fun deleteDiploma(diploma: Diploma) = viewModelScope.launch {
        _diplomas.value = Resource.Loading
        repository.deleteDiploma(diploma.id)
        _diplomas.value = repository.getDiplomas()
    }

    fun releaseDiploma() = viewModelScope.launch {
        //_diplomaRegistered.value = null
        //_diplomaSelected.value = null
    }





    //Graduates
    fun setGraduate(graduate:Graduate?) = viewModelScope.launch {
        _graduateSelected.value = Resource.Loading
        _graduateSelected.value = repository.setGraduate(graduate)
    }

    fun getGraduate(id: String) = viewModelScope.launch {
        _graduate.value = Resource.Loading
        _graduate.value = repository.getGraduate(id)
    }

    fun getGraduates() = viewModelScope.launch {
        _graduates.value = Resource.Loading
        _graduates.value = repository.getGraduates()
    }

    fun registerGraduate(graduate: Graduate) = viewModelScope.launch {
        _graduateRegistered.value = Resource.Loading
        _graduateRegistered.value = repository.registerGraduate(graduate)
        _graduates.value = repository.getGraduates()
        _graduates.value = repository.getGraduates()
    }

    fun deleteGraduate(graduate: Graduate) = viewModelScope.launch {
        _graduates.value = Resource.Loading
        repository.deleteGraduate(graduate.id)
        _graduates.value = repository.getGraduates()
    }

    fun releaseGraduate() = viewModelScope.launch {
//        _graduateRegistered.value = null
//        _graduateSelected.value = null
    }





    //Students
    fun setStudent(student:Student?) = viewModelScope.launch {
        _studentSelected.value = Resource.Loading
        _studentSelected.value = repository.setStudent(student)
    }

    fun getStudent(id: String) = viewModelScope.launch {
        _student.value = Resource.Loading
        _student.value = repository.getStudent(id)
    }

    fun getStudents() = viewModelScope.launch {
        _students.value = Resource.Loading
        _students.value = repository.getStudents()
    }

    fun registerStudent(student: Student) = viewModelScope.launch {
        _studentRegistered.value = Resource.Loading
        _studentRegistered.value = repository.registerStudent(student)
        _students.value = repository.getStudents()
        _students.value = repository.getStudents()
    }

    fun deleteStudent(student: Student) = viewModelScope.launch {
        _students.value = Resource.Loading
        repository.deleteStudent(student.id)
        _students.value = repository.getStudents()
    }

    fun releaseStudent() = viewModelScope.launch {
//        _studentRegistered.value = null
//        _studentSelected.value = null
    }
}