package com.bbt.lawyerclientservice.controller;

import com.bbt.lawyerclientservice.model.LawyersProfileDTO;
import com.bbt.lawyerclientservice.model.ReservationDTO;
import com.bbt.lawyerclientservice.model.ReviewDTO;
import com.bbt.lawyerclientservice.services.clientService.ClientService;
import com.bbt.lawyerclientservice.services.lawyerService.LawyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client")
public class ClientController {
    private final ClientService clientService;
    private final LawyerService lawyerService;

    @GetMapping("/lawyers")
    public ResponseEntity<?> getAllLawyers(){
        return ResponseEntity.ok(clientService.getAllLawyers());
    }

    @GetMapping("/search/{searchAttribute}")
    public ResponseEntity<?> getAllByFullName(@PathVariable("searchAttribute") String search){
        List<LawyersProfileDTO> lawyers = lawyerService.getAllByFullName(search);
        return ResponseEntity.ok(lawyers);
    }

    @GetMapping("/load/lawyers/{page}")
    public ResponseEntity<?> loadLawyersWhenScroll(@PathVariable("page")Integer page){
        List<LawyersProfileDTO> lawyers = lawyerService.getLawyersWhenScroll(page);
        return ResponseEntity.ok(lawyers);
    }

    @PostMapping("/reservation")
    public ResponseEntity<?> reservationMethod(@RequestBody ReservationDTO reservationDTO){
        boolean success = clientService.bookService(reservationDTO);
        if(success == true){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/lawyer/{id}")
    public ResponseEntity<?> getDetailsByLawyerId(@PathVariable Long id){
        return ResponseEntity.ok(clientService.getLawyerDetailsById(id));
    }

    @GetMapping("/my-reservations/{email}")
    public ResponseEntity<?> getAllReservationById(@PathVariable String email){
        return ResponseEntity.ok(clientService.getAllByUserId(email));
    }

    @PostMapping("/review")
    public ResponseEntity<?> giveReview(@RequestBody ReviewDTO reviewDTO){
        boolean success = clientService.giveReview(reviewDTO);
        if(success) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
}
