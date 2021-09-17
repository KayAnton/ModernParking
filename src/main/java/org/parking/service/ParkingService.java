package org.parking.service;

import static java.time.Instant.now;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.parking.model.buildings.Entrance;
import org.parking.model.buildings.Parking;
import org.parking.model.buildings.Spot;
import org.parking.model.vehicles.Automobile;

public class ParkingService {

  public static void setupParking(Parking parking) {
    final List<Entrance> entrances = parking.getEntrances();
    for (int i = 0; i < parking.getEntranceAmount(); i++) {
      entrances.add(new Entrance(parking));
    }
  }

  public static Spot findEmptySpot(Parking parking) {
    final ConcurrentHashMap<Spot, Automobile> parkingSpots = parking.getParkingSpots();
    if (parkingSpots.size() < parking.getParkingSize()) {
      for (int levelNumber = 0; levelNumber < parking.getParkingLevels(); levelNumber++) {
        for (int spotNumber = 0; spotNumber < parking.getGetParkingLevelSize(); spotNumber++) {
          Spot spot = new Spot(levelNumber, spotNumber, now().getEpochSecond() + Math.round(Math.random() * 10000));
          if (parkingSpots.get(spot) == null) {
            return spot;
          }
        }
      }
    }
    return null;
  }

  public static boolean checkIfCarAllowed(Parking parking, Automobile automobile) {
    return parking.getAcceptableAutoTypeList().stream()
        .anyMatch(t -> t.isAssignableFrom(automobile.getClass()));
  }
}
