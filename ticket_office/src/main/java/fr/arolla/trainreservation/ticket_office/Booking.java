package fr.arolla.trainreservation.ticket_office;

import java.util.List;

public record Booking(String train_id, String booking_reference, List<Seat> seats) {
}
