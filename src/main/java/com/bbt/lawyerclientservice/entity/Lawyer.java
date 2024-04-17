package com.bbt.lawyerclientservice.entity;

import com.bbt.lawyerclientservice.model.LawyersProfileDTO;
import com.bbt.lawyerclientservice.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Lawyer extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String middlename;
    private Integer age;
    private Integer experience;
    @Enumerated(value = EnumType.STRING)
    private UserRole role;
    private String password;
    private String description;
    @Lob
    @Column(name = "lawyer_card_img")
    @JdbcTypeCode(Types.BINARY)
    private byte[] img;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;

    @OneToMany(mappedBy = "lawyer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoryOfTrials> histories = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "lawyer_address_id")
    private Address lawyers_address;

    public LawyersProfileDTO toLawyersInfoCardDTO(){
        return
                LawyersProfileDTO.builder()
                        .id(id)
                        .firstname(firstname)
                        .lastname(lastname)
                        .middlename(middlename)
                        .phone(getPhone())
                        .email(getEmail())
                        .experience(experience)
                        .age(age)
                        .returnedImg(img)
                        .street(lawyers_address.getStreet())
                        .building(lawyers_address.getBuilding())
                        .city(lawyers_address.getCity())
                        .description(description)
                        .build();
    }

    public LawyersProfileDTO toLawyerCard(){
        return LawyersProfileDTO.builder()
                .id(id)
                .firstname(firstname)
                .lastname(lastname)
                .middlename(middlename)
                .phone(getPhone())
                .email(getEmail())
                .description(description)
                .returnedImg(img)
                .build();
    }

    @Override
    public String toString(){
        return " id: " + id +
                " lastname: " + lastname +
                " firstname: " + firstname +
                " middlename: " + middlename +
                " email: " + getEmail() +
                " phones " + getPhone() +
                " issued: " + certificate.getIssued() +
                " authority: " + certificate.getIssuingAuthority() +
                " expire: " + certificate.getExpire();
    }

    public void addHistoryOfTrial(HistoryOfTrials history){
        histories.add(history);
        history.setLawyer(this);
    }

    public void removeHistoryOfTrial(HistoryOfTrials history){
        histories.remove(history);
        history.setLawyer(null);
    }

}
