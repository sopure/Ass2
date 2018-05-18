package edu.monash.swan.ass2.Bean;


import java.util.UUID;


public class SameAttriStud {
    private UUID mId;
    // full name as title
    private Integer mid;
    private String mTitle;
    private String mFullName;
    private String mEmail;
    private String mGender;
    private String mCourse;
    private String mFavoriteMovie;

    public SameAttriStud() {
        //generate unique identifier
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public Integer getid() {
        return mid;
    }

    public String getFullName() {
        return mFullName;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getGender() {
        return mGender;
    }

    public String getCourse() {
        return mCourse;
    }

    public void setId(Integer mid) {
        this.mid = mid;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setFullName(String mFullName) {
        this.mFullName = mFullName;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public void setGender(String mGender) {
        this.mGender = mGender;
    }

    public void setCourse(String mCourse) {
        this.mCourse = mCourse;
    }

    public String getFavoriteMovie() {
        return mFavoriteMovie;
    }

    public void setFavoriteMovie(String mFavoriteMovie) {
        this.mFavoriteMovie = mFavoriteMovie;
    }
}
