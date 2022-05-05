package com.example.graduatesapp.data.repositories

import com.example.graduatesapp.data.models.Diploma
import com.example.graduatesapp.data.models.Graduate
import com.example.graduatesapp.data.models.Student
import com.example.graduatesapp.data.network.MainApi
import okhttp3.MultipartBody

class MainRepository (
    private val api: MainApi,

    ) : BaseRepository() {
    private var _diplomaSelected: Diploma? = null
    private var _graduateSelected: Graduate? = null
    private var _studentSelected: Student? = null


    //diplomas
    suspend fun registerDiploma(diploma: Diploma) = safeApiCall {
        return@safeApiCall if (diploma.id == "null") api.createDiploma(diploma) else api.updateDiploma(
            diploma.id,
            diploma
        )
    }

    suspend fun deleteDiploma(id: String) = safeApiCall {
        api.deleteDiploma(id)
    }

    suspend fun getDiplomas() = safeApiCall {
        api.getDiplomas()
    }

    suspend fun getDiploma(id: String) = safeApiCall {
        api.getDiploma(id)
    }

    suspend fun setDiploma(diploma: Diploma?) = safeApiCall {
        this._diplomaSelected = diploma
        return@safeApiCall diploma
    }


    //graduates
    suspend fun registerGraduate(graduate: Graduate) = safeApiCall {
        return@safeApiCall if (graduate.id == "null") api.createGraduate(graduate) else api.updateGraduate(
            graduate.id,
            graduate
        )
    }

    suspend fun deleteGraduate(id: String) = safeApiCall {
        api.deleteGraduate(id)
    }

    suspend fun getGraduates() = safeApiCall {
        api.getGraduates()
    }

    suspend fun getGraduate(id: String) = safeApiCall {
        api.getGraduate(id)
    }

    suspend fun setGraduate(graduate: Graduate?) = safeApiCall {
        this._graduateSelected = graduate
        return@safeApiCall graduate
    }


    //students
    suspend fun registerStudent(student: Student) = safeApiCall {
        return@safeApiCall if (student.id == "null") api.createStudent(student) else api.updateStudent(
            student.id,
            student
        )
    }

    suspend fun deleteStudent(id: String) = safeApiCall {
        api.deleteStudent(id)
    }

    suspend fun getStudents() = safeApiCall {
        api.getStudents()
    }

    suspend fun getStudent(id: String) = safeApiCall {
        api.getStudent(id)
    }

    suspend fun setStudent(student: Student?) = safeApiCall {
        this._studentSelected = student
        return@safeApiCall student
    }


}