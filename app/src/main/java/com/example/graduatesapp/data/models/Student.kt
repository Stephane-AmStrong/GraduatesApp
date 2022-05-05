package com.example.graduatesapp.data.models

import java.util.*

data class Student(
    var firstName: String,
    var lastName: String,
    var birthday: Date,
) {
    var id: String = "null"
    var createdAt: Date? = null
    var updatedAt: Date? = null
    var graduates: MutableList<Graduate> = mutableListOf()

    constructor(
        id: String,
        firstName: String,
        lastName: String,
        graduates: MutableList<Graduate>,
        birthday: Date,
        createdAt: Date?,
        updatedAt: Date?,
    ) : this(
        firstName,
        lastName,
        birthday
    ) {
        this.id = id
        this.graduates = graduates
        this.createdAt = createdAt
        this.updatedAt = updatedAt
    }
}
