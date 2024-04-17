package com.bbt.lawyerclientservice.services.lawyerService;

import com.bbt.lawyerclientservice.model.LawyersProfileDTO;
import com.bbt.lawyerclientservice.model.ReservationDTO;
import com.bbt.lawyerclientservice.entity.*;
import com.bbt.lawyerclientservice.entity.enums.ReservationStatus;
import com.bbt.lawyerclientservice.entity.enums.UserRole;
import com.bbt.lawyerclientservice.repository.LawyerRepository;
import com.bbt.lawyerclientservice.repository.ReservationRepository;
import com.bbt.lawyerclientservice.services.CustomGenerator;
import com.bbt.lawyerclientservice.specification.LawyerSpecification;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LawyerService {
    private final LawyerRepository lawyerRepository;
    private final PasswordEncoder encoder;
    private final CustomGenerator customGenerator;
    private final ReservationRepository reservationRepository;

    //TODO Удалить метод getAllByName()
    public List<LawyersProfileDTO> getAllByName(String firstname, String lastname, String middlename){
        List<Lawyer> lawyers = lawyerRepository.findAll(LawyerSpecification.withName(lastname,firstname, middlename));
        if(!lawyers.isEmpty()){
            return lawyers.stream()
                    .map(Lawyer::toLawyersInfoCardDTO)
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<LawyersProfileDTO> getAllByFullName(String searchAttribute){
        List<Lawyer> lawyers = lawyerRepository.findAll(LawyerSpecification.fullNameContainsIgnoreCase(searchAttribute));
        if(!lawyers.isEmpty()){
            return lawyers.stream().map(Lawyer::toLawyersInfoCardDTO).collect(Collectors.toList());
        }
        return null;
    }

    @Transactional
    public LawyersProfileDTO getLawyerByEmail(String email){
        return lawyerRepository.findLawyerByEmail(email)
                .map(lawyer -> lawyer.toLawyersInfoCardDTO())
                .orElse(null);
    }

    public List<LawyersProfileDTO> getLawyersWhenScroll(int page){
        final int SIZE = 10;
        Pageable pageable = PageRequest.of(page, SIZE);
        return lawyerRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(Lawyer::toLawyersInfoCardDTO)
                .collect(Collectors.toList());
    }

    //TODO Удалить метод getListOfAllLawyers()
    public List<LawyersProfileDTO> getListOfAllLawyers(){
        return lawyerRepository.findAll().stream().map(Lawyer::toLawyersInfoCardDTO).collect(Collectors.toList());
    }

    public boolean isInfoCardChanged(Long id, LawyersProfileDTO infoCardDTO) throws IOException {
        Optional<Lawyer> optionalLawyer = lawyerRepository.findById(id);
        if(optionalLawyer.isPresent()){
            Lawyer lawyer = optionalLawyer.get();
            Address address = optionalLawyer.get().getLawyers_address();
            Contact contact = optionalLawyer.get().getContact();

            lawyer.setDescription(checkDTOParamNull(infoCardDTO.getDescription(), lawyer.getDescription()));
            address.setCity(checkDTOParamNull(infoCardDTO.getCity(), address.getCity()));
            address.setStreet(checkDTOParamNull(infoCardDTO.getStreet(), address.getStreet()));
            address.setBuilding(checkDTOParamNull(infoCardDTO.getBuilding(), address.getBuilding()));
            if(!infoCardDTO.getEmail().equals("null") && !infoCardDTO.getEmail().isEmpty()){
                contact.setEmail(infoCardDTO.getEmail());
            }
            contact.setPhone(checkDTOParamNull(infoCardDTO.getPhone(), contact.getPhone()));
            if(infoCardDTO.getImg() != null){
                lawyer.setImg(infoCardDTO.getImg().getBytes());
            }

            lawyerRepository.save(lawyer);

            return true;
        }

        return false;
    }

    //TODO Удалить метод getLawyerById()
    public LawyersProfileDTO getLawyerById(Long id){
        Optional<Lawyer> optionalLawyer = lawyerRepository.findById(id);
        if (optionalLawyer.isPresent()){
            return optionalLawyer.get().toLawyersInfoCardDTO();
        }
        return null;
    }
    private String checkDTOParamNull(String infoCardParam, String lawyerParam){
        return !"null".equals(infoCardParam) && !infoCardParam.isEmpty() ? infoCardParam : lawyerParam;
    }

    public boolean changeReservationStatus(UUID reservationId, String status){
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if(reservationOptional.isPresent()){
            Reservation reservation = reservationOptional.get();
            if(Objects.equals(status, "Approve")){
                reservation.setReservationStatus(ReservationStatus.APPROVED);
            }else {
                reservation.setReservationStatus(ReservationStatus.REJECTED);
            }

            reservationRepository.save(reservation);
            return true;
        }

        return false;
    }

    //TODO Удалить метод createLawyerTemp()
    public void createLawyerTemp(){
       Lawyer lawyer = new Lawyer();
       lawyer.setFirstname("Бахтияр");
       lawyer.setLastname("Байнбет");
       lawyer.setMiddlename("Саятович");
       lawyer.setAge(20);
       lawyer.setExperience(1);
       lawyer.setRole(UserRole.LAWYER);
       lawyer.setPassword(encoder.encode("12345"));
       lawyer.setDescription("Специализируюсь в криминальных делах");

       Certificate certificate = new Certificate();
       certificate.setExpire(Date.valueOf(LocalDate.now()));
       certificate.setIssued(Date.valueOf(LocalDate.now().plusYears(4)));
       certificate.setIssuingAuthority("Министерством юстиции Республики Казахстан");
       certificate.setLawyer(lawyer);

       Contact contact = new Contact();
       contact.setEmail("lawyer@gmail.com");
       contact.setPhone("+77077200674");

       Address address = new Address();
       address.setStreet("Толе би");
       address.setBuilding("59");

       lawyer.setCertificate(certificate);
       lawyer.setLawyers_address(address);
       lawyer.setContact(contact);

       lawyerRepository.save(lawyer);
    }

    public List<ReservationDTO> getAllReservations(String email){
        return reservationRepository.findAllByLawyerContactEmail(email).stream().map(Reservation::toReservationDTO).collect(Collectors.toList());
    }
    @Transactional
    public void createLawyers(){
        Faker faker = new Faker(new Locale("ru"));
        List<Lawyer> lawyers = new ArrayList<>();
        for(int i=0; i<1000; i++){
             Lawyer lawyer = new Lawyer();
             lawyer.setFirstname(faker.name().firstName());
             lawyer.setLastname(faker.name().lastName());
             lawyer.setAge(faker.number().numberBetween(24, 65));
             lawyer.setExperience(customGenerator.generateExperience(lawyer.getAge()));
             lawyer.setRole(UserRole.LAWYER);
             lawyer.setPassword(encoder.encode("12345"));

             Contact contact = new Contact();
             contact.setEmail(customGenerator.generateEmail(lawyer.getFirstname(), lawyer.getLastname()));
             contact.setPhone(customGenerator.generatePhoneNumber());

             Address address = new Address();
             address.setCity(customGenerator.getRandomCity());

             Certificate certificate = new Certificate();
             certificate.setExpire(Date.valueOf(LocalDate.now()));
             certificate.setIssued(Date.valueOf(LocalDate.now().plusYears(4)));
             certificate.setIssuingAuthority("Министерством юстиции Республики Казахстан");
             certificate.setLawyer(lawyer);

             lawyer.setLawyers_address(address);
             lawyer.setContact(contact);
             lawyer.setCertificate(certificate);
             lawyers.add(lawyer);
        }
        lawyerRepository.saveAll(lawyers);
    }
}
