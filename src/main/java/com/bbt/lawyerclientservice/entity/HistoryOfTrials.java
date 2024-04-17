package com.bbt.lawyerclientservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity(name = "history_of_trials")
@Data
public class HistoryOfTrials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateOfTrial;
    private String result;

    @ManyToOne(fetch = FetchType.LAZY)
    private Lawyer lawyer;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "opposite_lawyer_id")
    private Lawyer oppositeLawyer;

    @Override
    public String toString(){

        return "History["+
                "id: " + id
                + "date: " + dateOfTrial
                + "result " + result
                + "lawyer" + lawyer.getId();
    }
}
