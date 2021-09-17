package org.parking.runner;

import static java.time.Instant.now;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.parking.model.buildings.Parking;
import org.parking.model.buildings.Spot;
import org.parking.model.vehicles.Automobile;

@Slf4j
public class ParkingRunner implements Runnable {

  private final Parking parking;

  public ParkingRunner(Parking parking) {
    this.parking = parking;
  }

  @Override
  public void run() {
    //parking check by itself paid-time and remove car when money is over
    System.out.println("Parking is working...");
    while (parking.isOpen()) {
      final ConcurrentHashMap<Spot, Automobile> parkingSpots = parking.getParkingSpots();
      parkingSpots.keySet().stream()
          .filter(spot -> spot.getTimestamp() < now().getEpochSecond())
          .forEach(spot -> {
            Automobile auto = parkingSpots.remove(spot);
            log.info("{}.{} left the parking", auto.getType(), auto);
            parking.pushCarToPool(auto);
          });
    }
  }

}
