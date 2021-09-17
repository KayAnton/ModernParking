package org.parking.runner;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.parking.factory.AutomobileFactory;
import org.parking.model.User;
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
      final Random random = new Random();
      final User bot = new User(random.nextInt(100) * 10000L);
      Automobile automobile = AutomobileFactory.createAutomobileByTypeId(bot, (int) Math.round(Math.random() * 3));
      bot.setAuto(automobile);
      if (ParkingService.checkIfCarAllowed(entranceParking, automobile)) {
        Spot spot = ParkingService.findEmptySpot(entranceParking, bot);
        if (spot != null) {
          if (ParkingService.takeSpotForTime(spot, automobile, bot, (long) random.nextInt(10) + 20)) {
//            log.debug("{} - level, {} - place is allocated for vehicle {}",
//                spot.getLevel(), spot.getSpotNumber(), automobile);
          }
          continue;
        }
      }
      try {
        Thread.sleep(2500L);
      } catch (InterruptedException e) {
        log.error("Sleep error.", e);
      }
    }
  }

}
