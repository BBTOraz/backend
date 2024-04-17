package com.bbt.lawyerclientservice.model;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;


@Builder
public class LawyersProfileDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String middlename;
    private Integer age;
    private Integer experience;
    private String phone;
    private String email;
    private MultipartFile img;
    private byte[] returnedImg;
    private String city;
    private String building;
    private String street;
    private String description;
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }

    public void setReturnedImg(byte[] returnedImg) {
        this.returnedImg = returnedImg;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setBuilding(String buildings) {
        this.building = buildings;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public String getBuilding() {
        return building;
    }

    public String getStreet() {
        return street;
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getExperience() {
        return experience;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public MultipartFile getImg() {
        return img;
    }

    public byte[] getReturnedImg() {
        return returnedImg;
    }
}
