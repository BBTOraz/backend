package com.bbt.lawyerclientservice.entity;

import com.bbt.lawyerclientservice.model.ReviewDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Date reviewDate;
    private String review;
    private Long rating;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lawyer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lawyer lawyer;

    public ReviewDTO toReviewDTO(){
        return ReviewDTO.builder()
                .id(id)
                .review(review)
                .reviewDate(reviewDate)
                .rating(rating)
                .clientId(client.getEmail())
                .clientName(client.getFirstname())
                .lawyerId(lawyer.getId())
                .lawyerName(lawyer.getFirstname())
                .build();
    }
}
