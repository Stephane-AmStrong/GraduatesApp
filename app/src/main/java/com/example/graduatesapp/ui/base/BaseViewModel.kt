package com.example.graduatesapp.ui.base

import androidx.lifecycle.ViewModel
import com.example.graduatesapp.data.repositories.BaseRepository

abstract class BaseViewModel(
    private val repository: BaseRepository
) : ViewModel() {

}