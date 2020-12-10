package com.example.myapp;

public class Tutor {
    private String name;
    private String institution;
    private String subject;
    private String status;
    private String price;
    private String about;
    private String gender;
    private String mobile;
    private String vLink;




    public Tutor() {
    }


    public Tutor(String name, String institution, String subject, String status , String price, String about, String gender, String mobile,String vLink) {
        this.name = name;
        this.institution = institution;
        this.subject = subject;
        this.status = status;
        this.price=price;
        this.about = about;
        this.gender = gender;
        this.mobile = mobile;
        this.vLink = vLink;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getvLink() {
        return vLink;
    }

    public void setvLink(String vLink) {
        this.vLink = vLink;
    }
}
