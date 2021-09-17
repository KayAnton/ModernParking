package org.parking.controller;

import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import org.parking.factory.AutomobileFactory;
import org.parking.model.User;
import org.parking.model.buildings.Parking;
import org.parking.model.buildings.Spot;
import org.parking.model.enums.CarType;
import org.parking.service.ParkingService;

@Slf4j
public class ParkingController implements Runnable {

  private static final long USER_ID = 123L;

  private final Parking parking;

  public ParkingController(Parking parking) {
    this.parking = parking;
  }

  @Override
  public void run() {
    final User user = new User(USER_ID);
    AutomobileFactory.createAutomobileByType(user, CarType.CAR);
    try (Scanner in = new Scanner(System.in)) {
      while (true) {
        log.info(
            "Select action:\n1.Get proposed spot;\n2.Take proposed spot;\n3.Leave the parking;\n4.Show full parking state.");
        try {
          switch (in.nextInt()) {
            case 1 -> {
              Spot spot = ParkingService.findEmptySpot(parking, user);
              if (Objects.nonNull(spot)) {
                user.setTakenSpot(spot);
                log.info("Found and reserved an empty spot: Level: {}, Number: {}", spot.getLevel(), spot.getNumber());
              } else {
                log.error("No empty spots found, try again later.");
              }
            }
            case 2 -> {
              final Spot takenSpot = user.getTakenSpot();
              if (Objects.nonNull(takenSpot)) {
                if (ParkingService.takeSpot(takenSpot, user.getAuto(), user)) {
                  log.info("Successfully taken the spot!");
                } else {
                  log.error("Wasn't able to take the spot!");
                }
              } else {
                log.error("You haven't got a proposed spot yet!");
              }
            }
            case 3 -> {
              final Spot takenSpot = user.getTakenSpot();
              if (Objects.nonNull(takenSpot)) {
                takenSpot.clear();
                log.info("You've left the parking!");
              } else {
                log.error("You haven't taken a spot yet!");
              }
            }
            case 4 -> ParkingService.outputParkingState(parking);
            default -> log.error("Unknown value selected!");
          }
        } catch (Exception e) {
          log.error("Enter a number, please");
        }
      }
    }
  }
}
