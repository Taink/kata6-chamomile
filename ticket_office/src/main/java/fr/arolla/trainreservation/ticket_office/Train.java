package fr.arolla.trainreservation.ticket_office;

import java.util.Map;

public record Train(Map<String, TrainSeat> seats) {
}
