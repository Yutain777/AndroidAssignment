package com.example.mymoviememoir.dbentity;

public class Memoir {
    private Long memoirId;
    private String watchDate;
    private String movieName;
    private String movieReleaseDate;
    private String comment;
    private float rating;
    private Person personId;
    private Cinema cinemaId;

    public Memoir(){

    }
    public Memoir(Long memoirId){
        this.memoirId = memoirId;
    }
    public Memoir(Long memoirId,String watchDate, String movieName,String movieReleaseDate,String comment,float rating){
        this.memoirId = memoirId;
        this.watchDate = watchDate;
        this.movieName = movieName;
        this.movieReleaseDate = movieReleaseDate;
        this.comment = comment;
        this.rating = rating;
    }

    public Long getMemoirId() {
        return memoirId;
    }

    public void setMemoirId(Long memoirId) {
        this.memoirId = memoirId;
    }

    public String getWatchDate() {
        return watchDate;
    }

    public void setWatchDate(String watchDate) {
        this.watchDate = watchDate;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Cinema getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Long id) {
        this.cinemaId = new Cinema(id);
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Long id) {
        this.personId = new Person(id);
    }











}
