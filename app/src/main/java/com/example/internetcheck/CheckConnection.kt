package com.example.internetcheck

import kotlinx.coroutines.flow.Flow

interface CheckConnection {

    fun observe(): Flow<Status>

    enum class Status {
        Availabe, Unavilable, Losing, Lost
    }

}

