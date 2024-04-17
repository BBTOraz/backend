package com.bbt.lawyerclientservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity(name = "certificate")
@Data
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String issuingAuthority;
    private Date issued;
    private Date expire;

    @OneToOne(mappedBy = "certificate", fetch = FetchType.LAZY, orphanRemoval = true)
    private Lawyer lawyer;
}
