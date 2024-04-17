package com.bbt.lawyerclientservice.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class ReviewDTO {
    private UUID id;
    private Date reviewDate;
    private String review;
    private Long rating;
    private String clientId;
    private Long lawyerId;
    private String clientName;
    private String lawyerName;
    private UUID reservationId;
}
