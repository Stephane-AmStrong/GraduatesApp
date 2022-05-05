package com.example.graduatesapp.data.models

import java.util.*

data class Graduate(
    var graduateAt: Date,
    var dimplomaId: String,
    var studentId: String,
) {
    var id: String = "null"
    var dimploma: Dimploma? = null
    var student: Student? = null
    var createdAt: Date? = null
    var updatedAt: Date? = null

    constructor(
        id: String,
        graduateAt: Date,
        dimplomaId: String,
        studentId: String,
        createdAt: Date?,
        updatedAt: Date?,
        dimploma: Dimploma?,
        student: Student?
    ) : this(
        graduateAt,
        dimplomaId,
        studentId
    ) {
        this.id = id
        this.dimploma = dimploma
        this.student = student
        this.createdAt = createdAt
        this.updatedAt = updatedAt
    }
}