package com.example.classschedule.Domain.utill

import kotlinx.coroutines.flow.StateFlow

interface NetworkObserver {
    val isAvailable: StateFlow<Boolean>
}