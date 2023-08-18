package entities;

import constants.MovieGenre;

import java.util.Arrays;

public class Movie extends Bookmark {

    private int releaseYear;
    private String[] cast;
    private String[] director;
    private String genre;
    private double imdbRating;

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String[] getCast() {
        return cast;
    }

    public void setCast(String[] cast) {
        this.cast = cast;
    }

    public String[] getDirector() {
        return director;
    }

    public void setDirector(String[] director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }



    @Override
    public String toString() {
        return "Movie{" +
                "releaseYear=" + releaseYear +
                ", cast=" + Arrays.toString(cast) +
                ", director=" + Arrays.toString(director) +
                ", genre='" + genre + '\'' +
                ", imdbRating=" + imdbRating +
                '}';
    }

    @Override
    public boolean isKidFriendlyEligible() {
        if(genre.equals(MovieGenre.HORROR) || genre.equals(MovieGenre.THRILLERS)){
            return false;
        }
        return true;
    }
}