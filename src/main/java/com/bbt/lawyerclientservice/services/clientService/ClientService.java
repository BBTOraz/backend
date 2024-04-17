package com.bbt.lawyerclientservice.services.clientService;

import com.bbt.lawyerclientservice.model.LawyerDetailsForClientDTO;
import com.bbt.lawyerclientservice.model.LawyersProfileDTO;
import com.bbt.lawyerclientservice.model.ReservationDTO;
import com.bbt.lawyerclientservice.model.ReviewDTO;

import java.util.List;

public interface ClientService {
    List<LawyersProfileDTO> getAllLawyers();
    boolean bookService(ReservationDTO reservationDTO);
    boolean giveReview(ReviewDTO reviewDTO);
    LawyerDetailsForClientDTO getLawyerDetailsById(Long id);

    List<ReservationDTO> getAllByUserId(String email);
}
