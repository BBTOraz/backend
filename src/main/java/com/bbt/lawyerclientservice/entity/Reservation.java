package com.bbt.lawyerclientservice.entity;

import com.bbt.lawyerclientservice.model.ReservationDTO;
import com.bbt.lawyerclientservice.entity.enums.ReservationStatus;
import com.bbt.lawyerclientservice.entity.enums.ReviewStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(value = EnumType.STRING)
    private ReservationStatus reservationStatus;
    private ReviewStatus reviewStatus;
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lawyer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lawyer lawyer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client client;

    public ReservationDTO toReservationDTO(){
        return ReservationDTO.builder()
                .id(id)
                .lawyerName(lawyer.getFirstname())
                .date(date)
                .reservationStatus(reservationStatus)
                .reviewStatus(reviewStatus)
                .lawyerId(lawyer.getId())
                .clientEmail(client.getEmail())
                .userName(client.getFirstname()).build();
    }
}
