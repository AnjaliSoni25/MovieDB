package com.anjali.moviedb.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjali.moviedb.Movie
import kotlinx.coroutines.launch


class MovieViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _movies = mutableStateOf<List<Movie>>(emptyList())
    val movies: State<List<Movie>> = _movies

    // Function to fetch movies based on sort type and page
    fun discoverMovies(apiKey: String, page: Int) {
        viewModelScope.launch {
            _movies.value = repository.discoverMovies(apiKey, page)
        }
    }
}

