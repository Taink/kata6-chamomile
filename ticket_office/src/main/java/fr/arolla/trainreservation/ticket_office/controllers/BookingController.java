package fr.arolla.trainreservation.ticket_office.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.arolla.trainreservation.ticket_office.Seat;
import fr.arolla.trainreservation.ticket_office.Train;
import fr.arolla.trainreservation.ticket_office.services.BookingService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@RestController
public class BookingController {

  private final RestTemplate restTemplate;

  final BookingService bookingService;

  BookingController(BookingService bookingService) {
    restTemplate = new RestTemplate();
    this.bookingService = bookingService;
  }

  @RequestMapping("/reserve")
  BookingResponse reserve(@RequestBody BookingRequest bookingRequest) {
    var seatCount = bookingRequest.count();
    var trainId = bookingRequest.train_id();

    // Step 1: Get a booking reference
    var bookingReference = restTemplate.getForObject("http://127.0.0.1:8082/booking_reference", String.class);

    // Step 2: Retrieve train data for the given train ID
    var train = restTemplate.getForObject("http://127.0.0.1:8081/data_for_train/" + trainId, Train.class);
    List<Seat> seats = bookingService.getSeatsFromTrain(train);

    // Step 3: Find available seats
    var toReserve = bookingService.getNAvailableSeats(seats, seatCount);

    // Step 4: call the '/reserve' end point
    var ids = toReserve.stream().map(seat -> seat.seatNumber() + seat.coach()).toList();

    Map<String, Object> payload = new HashMap<>();
    payload.put("train_id", trainId);
    payload.put("seats", ids);
    payload.put("booking_reference", bookingReference);
    restTemplate.postForObject("http://127.0.0.1:8081/reserve", payload, String.class);

    // Step 5: return reference and booked seats
    return new BookingResponse(trainId, bookingReference, ids);
  }
}
