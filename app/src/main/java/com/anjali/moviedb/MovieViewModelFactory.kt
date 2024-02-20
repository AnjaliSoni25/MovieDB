package com.anjali.moviedb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anjali.moviedb.viewmodel.MovieRepository
import com.anjali.moviedb.viewmodel.MovieViewModel


class MovieViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
