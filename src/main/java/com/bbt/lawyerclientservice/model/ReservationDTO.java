package com.bbt.lawyerclientservice.model;

import com.bbt.lawyerclientservice.entity.enums.ReservationStatus;
import com.bbt.lawyerclientservice.entity.enums.ReviewStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class ReservationDTO {
    private UUID id;
    private Date date;
    private String lawyerName;
    private ReservationStatus reservationStatus;
    private ReviewStatus reviewStatus;
    private String clientEmail;
    private String userName;
    private Long lawyerId;

}
