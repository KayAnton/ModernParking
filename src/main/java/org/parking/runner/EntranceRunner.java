package org.parking.runner;

import lombok.extern.slf4j.Slf4j;
import org.parking.model.buildings.Entrance;
import org.parking.model.buildings.Parking;
import org.parking.model.buildings.Spot;
import org.parking.model.vehicles.Automobile;
import org.parking.service.ParkingService;

@Slf4j
public class EntranceRunner implements Runnable {

  private final Entrance entrance;
  private final Parking entranceParking;

  public EntranceRunner(Entrance entrance) {
    this.entrance = entrance;
    this.entranceParking = entrance.getParking();
  }

  @Override
  public void run() {
    log.info("Entrance is working...");
    while (entrance.getIsOpenedTime()) {
      Automobile automobile = entranceParking.autoComesFromTheRealWorld();
      if (ParkingService.checkIfCarAllowed(entranceParking,automobile)) {
        Spot spot = ParkingService.findEmptySpot(entranceParking);
        if (spot != null) {
          entranceParking.pushAutomobile(automobile, spot);
          log.info("{} - level, {} - place is allocated for vehicle {}",
              spot.getLevel(), spot.getSpotNumber(), automobile);
          break;
        }
      }
      log.info("I have no more free spots or you are to big for a standard spot - number of free spots - {}",
          entranceParking.getNumberOfFreeSpots());
      entranceParking.pushCarToPool(automobile);
    }
  }

}
