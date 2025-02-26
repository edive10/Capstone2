package com.example.myapplication.viewmodel

// حذف `UserData` از این فایل ❌
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserViewModel(private val context: Context) : ViewModel() {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_data", Context.MODE_PRIVATE)

    private val _userData = mutableStateOf(UserData())
    val userData: State<UserData> = _userData  // این خط را از کامنت خارج کن ✅

    init {
        loadUserData()
    }

    fun updateUserData(name: String, email: String, age: Int) {
        _userData.value = _userData.value.copy(name = name, email = email, age = age)  // اینجا مقداردهی درست شد ✅
        sharedPreferences.edit().apply {
            putString("name", name)
            putString("email", email)
            putInt("age", age)
            apply()
        }
    }

    fun updateAge(age: Int) {
        _userData.value = _userData.value.copy(age = age)  // مقداردهی جدید ✅
        sharedPreferences.edit().putInt("age", age).apply()
    }

    private fun loadUserData() {
        val name = sharedPreferences.getString("name", "") ?: ""
        val email = sharedPreferences.getString("email", "") ?: ""
        val age = sharedPreferences.getInt("age", 18)  // مقدار پیش‌فرض درست شد ✅

        _userData.value = UserData(name = name, email = email, age = age)
    }
}

class UserViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
