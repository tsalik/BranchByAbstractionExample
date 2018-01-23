package com.ktsal.branchbyabstraction.domain.entity


data class Quote(val content: String,
                 val source: String) {

    val isEmpty: Boolean
        get() = content.isEmpty() || source.isEmpty()

    val isNotEmpty: Boolean
        get() = !isEmpty

}