package org.parking;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.BasicConfigurator;
import org.parking.controller.ParkingController;
import org.parking.model.buildings.Parking;
import org.parking.runner.EntranceBotSimulator;
import org.parking.runner.ParkingRunner;
import org.parking.service.ParkingService;

public class ParkingSystem {

  private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(6);

  public static void main(String[] args) {
    BasicConfigurator.configure();

    final Parking parking = new Parking();
    ParkingService.setupParking(parking);

    final ParkingRunner parkingRunner = new ParkingRunner(parking);

    EXECUTOR.execute(parkingRunner);
    parking.getEntrances()
        .forEach(entrance -> EXECUTOR.execute(new EntranceBotSimulator(entrance)));
    EXECUTOR.execute(new ParkingController(parking));
  }

}
