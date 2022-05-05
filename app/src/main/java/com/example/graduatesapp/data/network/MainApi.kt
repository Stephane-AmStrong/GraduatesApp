package com.example.graduatesapp.data.network

import com.example.graduatesapp.data.models.Diploma
import com.example.graduatesapp.data.models.Graduate
import com.example.graduatesapp.data.models.Student
import retrofit2.http.*

interface MainApi {

    //Diplomas
    @GET("diplomas")
    suspend fun getDiplomas(): List<Diploma>

    @GET("diplomas/{id}")
    suspend fun getDiploma(@Path("id") id: String): Diploma

    @POST("diplomas")
    suspend fun createDiploma(@Body diploma: Diploma): Diploma

    @PUT("diplomas/{id}")
    suspend fun updateDiploma(@Path("id") id:String, @Body diploma: Diploma): Diploma

    @DELETE("diplomas/{id}")
    suspend fun deleteDiploma(@Path("id") id:String)


    //Graduates
    @GET("graduates")
    suspend fun getGraduates(): List<Graduate>

    @GET("graduates/{id}")
    suspend fun getGraduate(@Path("id") id: String): Graduate

    @POST("graduates")
    suspend fun createGraduate(@Body graduate: Graduate): Graduate

    @PUT("graduates/{id}")
    suspend fun updateGraduate(@Path("id") id:String, @Body graduate: Graduate): Graduate

    @DELETE("graduates/{id}")
    suspend fun deleteGraduate(@Path("id") id:String)

    //Students
    @GET("students")
    suspend fun getStudents(): List<Student>

    @GET("students/{id}")
    suspend fun getStudent(@Path("id") id: String): Student

    @POST("students")
    suspend fun createStudent(@Body student: Student): Student

    @PUT("students/{id}")
    suspend fun updateStudent(@Path("id") id:String, @Body student: Student): Student

    @DELETE("students/{id}")
    suspend fun deleteStudent(@Path("id") id:String)
}