package com.bbt.lawyerclientservice.model;

import lombok.Data;

import java.util.List;

@Data
public class LawyerDetailsForClientDTO {
    private LawyersProfileDTO lawyerDTO;
    private List<ReviewDTO> reviewDTOs;
}
