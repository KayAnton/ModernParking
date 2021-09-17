package org.parking.runner;

import static java.time.Instant.now;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.parking.model.buildings.Parking;
import org.parking.model.buildings.Spot;

@Slf4j
public class ParkingRunner implements Runnable {

  private final Parking parking;

  public ParkingRunner(Parking parking) {
    this.parking = parking;
  }

  @Override
  public void run() {
    //parking check by itself paid-time and remove car when money is over
    log.info("Parking is working...");
    while (parking.isOpen()) {
      parking.getParkingSpots().stream()
          .filter(spot -> Objects.nonNull(spot.getExpireTs()) && spot.getExpireTs() < now().getEpochSecond())
          .forEach(Spot::clear);
    }
  }

}
