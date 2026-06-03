package com.example.classschedule.Domain.usecase.room

import com.example.classschedule.Domain.repository.RoomRepository
import javax.inject.Inject

class RemoveUserUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    suspend operator fun invoke(){
        roomRepository.removeUser()
    }
}