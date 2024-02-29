package fr.arolla.trainreservation.ticket_office.services;

import fr.arolla.trainreservation.ticket_office.Seat;
import fr.arolla.trainreservation.ticket_office.Train;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

  public List<Seat> getNAvailableSeats(List<Seat> seats, int count) {
    // find available seats (hard-code coach 'A' for now)
    return seats.stream()
      .filter(seat -> seat.coach().equals("A") && seat.bookingReference() == null)
      .limit(count)
      .toList();
  }

  public List<Seat> getSeatsFromTrain(Train train) {
    return train.seats().values().stream()
      .map(seat -> new Seat(
        seat.seat_number(),
        seat.coach(),
        seat.booking_reference().isEmpty()
          ? null
          : seat.booking_reference()
      ))
      .toList();
  }

}
