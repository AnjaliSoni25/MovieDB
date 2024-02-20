package com.anjali.moviedb

import android.os.Bundle
import android.widget.Spinner
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.anjali.moviedb.ui.theme.MovieDBTheme
import com.anjali.moviedb.viewmodel.MovieViewModel


class MainActivity : ComponentActivity() {

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieDBTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   // MovieGridPreview()
                    MovieGridScreen(viewModel)

                }
            }
        }
        // Fetch movies when the activity is created
        viewModel.discoverMovies(apiKey = "YOUR_API_KEY", page = 1)
    }
}

@Composable
fun MovieGridScreen(viewModel: MovieViewModel) {
    val movies by viewModel.movies

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Movie grid UI
        MovieGrid(viewModel = viewModel, movies = movies, onItemClick = { })


    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieDBTheme {
        MovieGridPreview()
    }
}

@Composable
fun MovieGridPreview() {
    val movies = listOf(
        Movie(1, "Movie 1", "https://www.everypixel.com/covers/free/photos/flower/cover.jpg", "Moview Name",4.5),
        Movie(1, "Movie 1", "https://www.everypixel.com/covers/free/photos/flower/cover.jpg", "Moview Name",4.5),
           )
    /*MovieGrid(
        movies = movies,
        onItemClick = { }
    )*/

}

@Composable
fun MovieGrid(viewModel: MovieViewModel,
              movies: List<Movie>,
              onItemClick: (Movie) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("Popular", "Rating").forEachIndexed { index, text ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                }) {
                    Text(text = text)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(movies.chunkedIntoRows(2)) { rowMovies ->
                Row {
                    rowMovies.forEach { movie ->
                        MovieItem(movie = movie, onItemClick = onItemClick)
                    }
                }
            }
        }
    }
}

// Function to chunk the movies list into rows
private fun List<Movie>.chunkedIntoRows(chunkSize: Int): List<List<Movie>> {
    return this.chunked(chunkSize)
}

@Composable
fun DropdownMenuItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(8.dp),
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) {
                onClick()
            }
            .padding(contentPadding)
    ) {
        content()
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    onItemClick: (Movie) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick(movie) }
    ) {
        Image(
            painter = rememberImagePainter(
                data = movie.poster_path,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_background)
                    error(R.drawable.ic_launcher_foreground)
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.9f)
                .clip(shape = RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = movie.title,
            style = TextStyle(fontSize = 16.sp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

