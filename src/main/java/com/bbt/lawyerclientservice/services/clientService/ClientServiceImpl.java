package com.bbt.lawyerclientservice.services.clientService;

import com.bbt.lawyerclientservice.model.LawyerDetailsForClientDTO;
import com.bbt.lawyerclientservice.model.LawyersProfileDTO;
import com.bbt.lawyerclientservice.model.ReservationDTO;
import com.bbt.lawyerclientservice.model.ReviewDTO;
import com.bbt.lawyerclientservice.entity.Client;
import com.bbt.lawyerclientservice.entity.Lawyer;
import com.bbt.lawyerclientservice.entity.Reservation;
import com.bbt.lawyerclientservice.entity.Review;
import com.bbt.lawyerclientservice.entity.enums.ReservationStatus;
import com.bbt.lawyerclientservice.entity.enums.ReviewStatus;
import com.bbt.lawyerclientservice.repository.ClientRepository;
import com.bbt.lawyerclientservice.repository.LawyerRepository;
import com.bbt.lawyerclientservice.repository.ReservationRepository;
import com.bbt.lawyerclientservice.repository.ReviewRepository;
import com.bbt.lawyerclientservice.services.lawyerService.LawyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {
    private final LawyerService lawyerService;
    private final LawyerRepository lawyerRepository;
    private final ClientRepository clientRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    public List<LawyersProfileDTO> getAllLawyers(){
        return lawyerService.getListOfAllLawyers();
    }

    public boolean bookService(ReservationDTO reservationDTO){
        Optional<Lawyer> optionalLawyer = lawyerRepository.findById(reservationDTO.getLawyerId());
        Optional<Client> optionalClient = clientRepository.findClientByEmail(reservationDTO.getClientEmail());
        if(optionalClient.isPresent() && optionalLawyer.isPresent()){
            Reservation reservation = new Reservation();

            reservation.setDate(reservationDTO.getDate());
            reservation.setReservationStatus(ReservationStatus.PENDING);
            reservation.setClient(optionalClient.get());

            reservation.setLawyer(optionalLawyer.get());
            reservation.setReviewStatus(ReviewStatus.FALSE);

            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }

    public LawyerDetailsForClientDTO getLawyerDetailsById(Long id){
        Optional<Lawyer> optionalLawyer = lawyerRepository.findById(id);
        if(optionalLawyer.isPresent()){
            LawyerDetailsForClientDTO lawyerDetailsForClientDTO = new LawyerDetailsForClientDTO();
            lawyerDetailsForClientDTO.setLawyerDTO(optionalLawyer.get().toLawyersInfoCardDTO());
            List<Review> reviews = reviewRepository.findAllByLawyerId(id);
            lawyerDetailsForClientDTO.setReviewDTOs(reviews.stream().map(Review::toReviewDTO).collect(Collectors.toList()));
            return lawyerDetailsForClientDTO;
        }
        return null;
    }

    @Override
    public List<ReservationDTO> getAllByUserId(String email) {
        List<Reservation> reservations = reservationRepository.findAllByClientContactEmail(email);
        return reservations.stream().map(Reservation::toReservationDTO).collect(Collectors.toList());
    }

    @Override
    public boolean giveReview(ReviewDTO reviewDTO) {
        Optional<Client> optionalClient = clientRepository.findClientByEmail(reviewDTO.getClientId());
        //Optional<Lawyer> optionalLawyer = lawyerRepository.findById(reviewDTO.getLawyerId());
        Optional<Reservation> optionalReservation = reservationRepository.findById(reviewDTO.getReservationId());
        if(optionalClient.isPresent( ) && optionalReservation.isPresent()){
            Review review = new Review();
            review.setClient(optionalClient.get());
            review.setLawyer(optionalReservation.get().getLawyer());

            review.setRating(reviewDTO.getRating());
            review.setReviewDate(new Date());
            review.setReview(reviewDTO.getReview());

            reviewRepository.save(review);

            Reservation reservation = optionalReservation.get();
            reservation.setReviewStatus(ReviewStatus.TRUE);
            reservationRepository.save(reservation);
            
            return true;
        }
        return false;
    }
}
