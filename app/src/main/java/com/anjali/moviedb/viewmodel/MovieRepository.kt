package com.anjali.moviedb.viewmodel

import com.anjali.moviedb.Movie
import com.anjali.moviedb.MovieApi

class MovieRepository(private val api: MovieApi) {
    suspend fun discoverMovies(apiKey: String, page: Int): List<Movie> {
        return api.discoverMovies(apiKey = apiKey, page = page).results
    }
}
