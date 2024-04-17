package com.bbt.lawyerclientservice.controller;


import com.bbt.lawyerclientservice.model.LawyersProfileDTO;
import com.bbt.lawyerclientservice.model.ReservationDTO;
import com.bbt.lawyerclientservice.services.lawyerService.LawyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lawyer")
@RequiredArgsConstructor
public class LawyerController {

    private final LawyerService service;

    @GetMapping("/get")
    public ResponseEntity<String> getLawyer(){
        service.createLawyerTemp();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/get")
    public ResponseEntity<?> createLawyer(){
        service.createLawyers();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/my-profile/{id}")
    public ResponseEntity<?> postAd(@PathVariable("id") Long id, @ModelAttribute LawyersProfileDTO infoCardDTO) throws IOException {
        boolean success = service.isInfoCardChanged(id, infoCardDTO);
        if(success){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/edit/my-profile/{id}")
    public ResponseEntity<?> getLawyerById(@PathVariable("id") Long id){
        LawyersProfileDTO profile = service.getLawyerById(id);
        if(profile != null){
            return ResponseEntity.ok(profile);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/my-profile/{email}")
    public ResponseEntity<?> getProfile(@PathVariable("email") String email){
        return ResponseEntity.ok(service.getLawyerByEmail(email));
    }

    @GetMapping("/get/search")
    public ResponseEntity<?> getAllByName(@RequestParam(required = false) String firstname,
                                                     @RequestParam(required = false) String lastname,
                                                     @RequestParam(required = false) String middlename){
        List<LawyersProfileDTO> lawyers = service.getAllByName(firstname, lastname, middlename);
        return ResponseEntity.ok(lawyers);
    }

    @GetMapping("/search/{searchAttribute}")
    public ResponseEntity<?> getAllByFullName(@PathVariable("searchAttribute") String search){
        List<LawyersProfileDTO> lawyers = service.getAllByFullName(search);
        return ResponseEntity.ok(lawyers);
    }
    @GetMapping("/reservation/{email}")
    public ResponseEntity<List<ReservationDTO>> getAllReservations(@PathVariable String email){
        return ResponseEntity.ok(service.getAllReservations(email));
    }

    @PostMapping("reservation/{reservationId}/{status}")
    public ResponseEntity<?> changeReservationStatus(@PathVariable UUID reservationId, @PathVariable String status){
        boolean success = service.changeReservationStatus(reservationId, status);
        if (success) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
}
