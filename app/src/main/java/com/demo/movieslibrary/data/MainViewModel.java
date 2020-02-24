package com.demo.movieslibrary.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {

    private static MovieDatabase database;
    private static LiveData<List<Movie>> movies;
    private static LiveData<List<FavouriteMovie>> favouriteMovies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = MovieDatabase.getInstance(getApplication());
        movies = database.movieDao().getAllMovies();
        favouriteMovies = database.movieDao().getAllFavouriteMovies();
    }

    public Movie getMovieById(int id) {
        try {
            return new GetMovieTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteMovies() {
        new DeleteMoviesTask().execute();
    }

    public void insertMovie(Movie movie) {
        new InsertMovieTask().execute(movie);
    }

    public void deleteMovie(Movie movie) {
        new DeleteMovieTask().execute(movie);
    }

    public void insertFavouriteMovie(FavouriteMovie movie) {
        new InsertFavouriteMovieTask().execute(movie);
    }

    public void deleteFavouriteMovie(FavouriteMovie movie) {
        new DeleteFavouriteMovieTask().execute(movie);
    }

    private static class DeleteFavouriteMovieTask extends AsyncTask<FavouriteMovie, Void, Void> {

        @Override
        protected Void doInBackground(FavouriteMovie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDao().deleteFavouriteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class InsertFavouriteMovieTask extends AsyncTask<FavouriteMovie, Void, Void> {

        @Override
        protected Void doInBackground(FavouriteMovie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDao().insertFavouriteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class DeleteMovieTask extends AsyncTask<Movie, Void, Void> {

        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDao().deleteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class InsertMovieTask extends AsyncTask<Movie, Void, Void> {

        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDao().insertMovie(movies[0]);
            }
            return null;
        }
    }

    private static class DeleteMoviesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            database.movieDao().deleteAllMovies();
            return null;
        }
    }

    private static class GetMovieTask extends AsyncTask<Integer, Void, Movie> {

        @Override
        protected Movie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.movieDao().getMovieById(integers[0]);
            }
            return null;
        }
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public static LiveData<List<FavouriteMovie>> getFavouriteMovies() {
        return favouriteMovies;
    }
}
