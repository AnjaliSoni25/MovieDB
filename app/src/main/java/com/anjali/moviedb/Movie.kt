package com.anjali.moviedb

data class Movie(
    val id: Int,
    val overview: String,
    val poster_path: String,
    val title: String,
    val vote_average: Double
)