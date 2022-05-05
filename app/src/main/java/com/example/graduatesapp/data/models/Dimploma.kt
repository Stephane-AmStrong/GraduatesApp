package com.example.graduatesapp.data.models

import java.util.*

data class Dimploma(
    var name: String,
    var description: String?,
) {
    var id: String = "null"
    var createdAt: Date? = null
    var updatedAt: Date? = null
    var students: MutableList<Student> = mutableListOf()

    constructor(
        id: String,
        name: String,
        description: String?,
        students: MutableList<Student>,
        createdAt: Date?,
        updatedAt: Date?,
    ) : this(
        name,
        description
    ) {
        this.id = id
        this.students = students
        this.createdAt = createdAt
        this.updatedAt = updatedAt
    }
}