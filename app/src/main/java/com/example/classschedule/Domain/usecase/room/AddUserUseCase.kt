package com.example.classschedule.Domain.usecase.room


import com.example.classschedule.Domain.model.User
import com.example.classschedule.Domain.repository.RoomRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    suspend operator fun invoke(user: User){
        return roomRepository.addUser(user)
    }
}