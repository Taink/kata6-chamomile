package fr.arolla.trainreservation.ticket_office.controllers;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerTests {

  @Test
  public void reserve_two_seats() {

    String train_id = "express_2000";
    var controller = new BookingController();
    var request = new BookingRequest(train_id, 2);
    var response = controller.reserve(request);
    assertThat(response.seats()).hasSize(2);
  }

}
