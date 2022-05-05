package com.example.graduatesapp.data.models

import java.util.*

data class Graduate(
    var graduateAt: Date,
    var diplomaId: String,
    var studentId: String,
) {
    var id: String = "null"
    var diploma: Diploma? = null
    var student: Student? = null
    var createdAt: Date? = null
    var updatedAt: Date? = null

    constructor(
        id: String,
        graduateAt: Date,
        diplomaId: String,
        studentId: String,
        createdAt: Date?,
        updatedAt: Date?,
        diploma: Diploma?,
        student: Student?
    ) : this(
        graduateAt,
        diplomaId,
        studentId
    ) {
        this.id = id
        this.diploma = diploma
        this.student = student
        this.createdAt = createdAt
        this.updatedAt = updatedAt
    }
}