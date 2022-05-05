package com.example.graduatesapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.graduatesapp.MainViewModel
import com.example.graduatesapp.data.repositories.BaseRepository
import com.example.graduatesapp.data.repositories.MainRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val repository: BaseRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(repository as MainRepository) as T
            else -> throw IllegalArgumentException("MainViewModel Not Found")
        }
    }
}