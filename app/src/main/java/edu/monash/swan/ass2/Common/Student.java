package edu.monash.swan.ass2.Common;

import org.json.JSONObject;

public class Student{
    private static Integer sid = 1000;
    private Integer id;
    private String firstName;
    private String surname;
    private String dob;
    private String gender;
    private String course;
    private String studyMode;
    private String address;
    private String suburb;
    private String nationality;
    private String language;
    private String favouriteSport;
    private String favouriteMovie;
    private String favouriteUnit;
    private String currentJob;
    private String email;
    private String password;

    public Student(String firstName, String surname, String dob, String gender, String course, String studyMode, String address, String suburb, String nationality, String language, String favouriteSport, String favouriteMovie, String favouriteUnit, String currentJob, String email, String password) {
        id = ++sid;
        this.firstName = firstName;
        this.surname = surname;
        this.dob = dob;
        this.gender = gender;
        this.course = course;
        this.studyMode = studyMode;
        this.address = address;
        this.suburb = suburb;
        this.nationality = nationality;
        this.language = language;
        this.favouriteSport = favouriteSport;
        this.favouriteMovie = favouriteMovie;
        this.favouriteUnit = favouriteUnit;
        this.currentJob = currentJob;
        this.email = email;
        this.password = password;
    }

    public JSONObject convert(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstName", this.firstName);
            jsonObject.put("surname", this.surname);
            jsonObject.put("dob", this.dob);
            jsonObject.put("gender", this.gender);
            jsonObject.put("course", this.course);
            jsonObject.put("studyMode", this.studyMode);
            jsonObject.put("address", this.address);
            jsonObject.put("suburb", this.suburb);
            jsonObject.put("nationality", this.nationality);
            jsonObject.put("language", this.language);
            jsonObject.put("favouriteSport", this.favouriteSport);
            jsonObject.put("favouriteMovie", this.favouriteMovie);
            jsonObject.put("favouriteUnit", this.favouriteUnit);
            jsonObject.put("currentJob", this.currentJob);
            jsonObject.put("email", this.email);
            jsonObject.put("password", this.password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getStudyMode() {
        return studyMode;
    }

    public void setStudyMode(String studyMode) {
        this.studyMode = studyMode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFavouriteSport() {
        return favouriteSport;
    }

    public void setFavouriteSport(String favouriteSport) {
        this.favouriteSport = favouriteSport;
    }

    public String getFavouriteMovie() {
        return favouriteMovie;
    }

    public void setFavouriteMovie(String favouriteMovie) {
        this.favouriteMovie = favouriteMovie;
    }

    public String getFavouriteUnit() {
        return favouriteUnit;
    }

    public void setFavouriteUnit(String favouriteUnit) {
        this.favouriteUnit = favouriteUnit;
    }

    public String getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}