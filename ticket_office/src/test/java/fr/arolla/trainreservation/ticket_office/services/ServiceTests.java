package fr.arolla.trainreservation.ticket_office.services;

import fr.arolla.trainreservation.ticket_office.Seat;
import fr.arolla.trainreservation.ticket_office.Train;
import fr.arolla.trainreservation.ticket_office.TrainSeat;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceTests {
  @Test
  public void train_with_available_seat() {
    String seatNumber = "1";
    String coach = "A";
    String bookingReference = "";
    TrainSeat availableSeat = new TrainSeat(seatNumber, coach, bookingReference);

    Map<String, TrainSeat> trainSeats = new HashMap<>();
    trainSeats.put(seatNumber, availableSeat);
    Train train = new Train(trainSeats);
    BookingService service = new BookingService();

    List<Seat> seats = service.getSeatsFromTrain(train);
    Seat expected = new Seat(seatNumber, coach, null);

    assertThat(seats).contains(expected);
  }

  @Test
  public void train_with_unavailable_seat() {
    String seatNumber = "1";
    String coach = "A";
    String bookingReference = "75bcd18";
    TrainSeat availableSeat = new TrainSeat(seatNumber, coach, bookingReference);
    Map<String, TrainSeat> trainSeats = new HashMap<>();
    trainSeats.put(seatNumber + coach, availableSeat);
    Train train = new Train(trainSeats);
    BookingService service = new BookingService();

    List<Seat> seats = service.getSeatsFromTrain(train);
    Seat expected = new Seat(seatNumber, coach, bookingReference);

    assertThat(seats).contains(expected);
  }
}
