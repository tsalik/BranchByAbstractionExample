package com.ktsal.branchbyabstraction.domain.entity


data class Quote(val content: String,
                 val source: String) {

    private val isEmpty: Boolean
        get() = content.isEmpty() && source.isEmpty()

    val notEmpty: Boolean
        get() = !isEmpty

}