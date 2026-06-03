# ============================================
# Базовые правила для продакшена
# ============================================

# Сохраняем номера строк для краш-репортов (очень рекомендуется)
-keepattributes SourceFile,LineNumberTable

# ============================================
# Hilt
# ============================================
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }

# ============================================
# Compose
# ============================================
-keep class androidx.compose.** { *; }
-keep class androidx.compose.runtime.** { *; }

# ============================================
# Kotlin Serialization + Supabase Postgrest
# ============================================
# Сохраняем все data class'ы, которые используются с Supabase (очень важно)
-keep class com.example.classschedule.Data.dto.** { *; }
-keep class com.example.classschedule.Data.database.** { *; }

# Сохраняем kotlinx.serialization
-keep class kotlinx.serialization.** { *; }
-keepclassmembers class * {
    @kotlinx.serialization.SerialName <fields>;
}

# ============================================
# Ktor + Supabase
# ============================================
-keep class io.ktor.** { *; }
-keep class io.github.jan.supabase.** { *; }

# ============================================
# Room
# ============================================
-keep class * extends androidx.room.RoomDatabase { *; }
-keep class * extends androidx.room.Entity { *; }

# ============================================
# Russhwolf Multiplatform Settings (если будешь использовать)
# ============================================
-keep class com.russhwolf.settings.** { *; }

# ============================================
# Общие правила
# ============================================
# Сохраняем ViewModel'ы (на всякий случай)
-keep class * extends androidx.lifecycle.ViewModel { *; }

# Не обфусцируем имена enum'ов (SubjectEnum и т.д.)
-keepclassmembers enum * { *; }