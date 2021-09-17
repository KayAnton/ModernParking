package org.parking.building;

import lombok.extern.slf4j.Slf4j;
import org.parking.vehicles.Automobile;
import org.parking.vehicles.Car;
import org.parking.vehicles.Motorbike;

@Slf4j
public class Entrance implements Runnable {

  private volatile Boolean isOpenedTime = true;
  private final Parking parking;

  public Entrance(Parking parking) {
    this.parking = parking;
  }

  public void setOpenedTime(Boolean openedTime) {
    isOpenedTime = openedTime;
  }

  @Override
  public void run() {
    while (isOpenedTime) {
      log.info("Entrance is working...");
      Automobile automobile = parking.autoComesFromTheRealWorld();
      // parking can accept only cars and motorbikes
      if (automobile instanceof Car || automobile instanceof Motorbike) {
        Spot spot = parking.findEmptySpot();
        if (spot != null) {
          parking.pushAutomobile(automobile, spot);
          log.info("{} - level, {} - place is allocated for vehicle {}",
              spot.getLevel(), spot.getSpotNumber(), automobile);
          break;
        }
      }
      log.info("I have no more free spots or you are to big for a standard spot - number of free spots - {}",
          parking.getNumberOfFreeSpots());
      parking.pushBackToPool(automobile);
    }
  }
}
