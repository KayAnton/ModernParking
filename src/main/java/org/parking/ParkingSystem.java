package org.parking;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.BasicConfigurator;
import org.parking.controller.ParkingController;
import org.parking.model.buildings.Parking;
import org.parking.runner.EntranceRunner;
import org.parking.runner.ParkingRunner;
import org.parking.service.ParkingService;

public class ParkingSystem {

  public static void main(String[] args) {
    BasicConfigurator.configure();
    final Parking parking = new Parking();
    final ParkingRunner parkingRunner = new ParkingRunner(parking);
    ParkingService.setupParking(parking);
    ExecutorService executor = Executors.newFixedThreadPool(6);
    parking.getEntrances()
        .forEach(entrance -> executor.execute(new EntranceRunner(entrance)));
    executor.execute(parkingRunner);
    executor.execute(new ParkingController(parking));
  }

}
