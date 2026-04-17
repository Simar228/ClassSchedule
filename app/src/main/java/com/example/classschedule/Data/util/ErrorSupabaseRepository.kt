package com.example.classschedule.Data.util

import com.example.classschedule.Data.dto.GradeDto
import com.example.classschedule.Data.dto.ToGradeList
import com.example.classschedule.Data.dto.ToPairList
import com.example.classschedule.Domain.dao.UserDao
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject


//sealed interface ErrorSupabase{
//    enum class Auth(val error: String){
//        USER_ALREADY_EXISTS("user_already_exists"),
//        OVER_EMAIL_SEND_RATE_LIMIT("over_email_send_rate_limit"),
//        WEAK_PASSWORD("weak_password"),
//        INVALID_CREDENTIALS("invalid_credentials")
//
//    }
//    enum class
//}
//
//
//
//class ErrorSupabaseRepository (){
//
//}