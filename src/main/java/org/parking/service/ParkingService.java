package org.parking.service;

import static java.time.Instant.now;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.parking.model.User;
import org.parking.model.buildings.Entrance;
import org.parking.model.buildings.Parking;
import org.parking.model.buildings.Spot;
import org.parking.model.enums.SpotState;
import org.parking.model.vehicles.Automobile;

@Slf4j
public final class ParkingService {

  public static void setupParking(Parking parking) {
    parking.getEntrances().addAll(Collections.nCopies(parking.getEntranceAmount(), new Entrance(parking)));
    final List<Spot> parkingSpots = parking.getParkingSpots();
    for (int levelNumber = 0; levelNumber < parking.getParkingLevels(); levelNumber++) {
      for (int spotNumber = 0; spotNumber < parking.getGetParkingLevelSize(); spotNumber++) {
        parkingSpots.add(new Spot(parking, levelNumber, spotNumber));
      }
    }
  }

  public static Spot findEmptySpot(Parking parking, User user) {
    return parking.getParkingSpots()
        .stream()
        .filter(spot -> reserveSpot(spot, user))
        .findFirst()
        .orElse(null);
  }

  public static boolean checkIfCarAllowed(Parking parking, Automobile automobile) {
    return parking.getAcceptableAutoTypeList().stream()
        .anyMatch(t -> t.isAssignableFrom(automobile.getClass()));
  }

  public static boolean reserveSpot(Spot spot, User user) {
    if (SpotState.FREE.equals(spot.getSpotState())) {
      spot.setReservedBy(user);
      spot.setExpireTs(now().getEpochSecond() + 60);
      return true;
    }
    return false;
  }

  public static boolean takeSpot(Spot spot, Automobile automobile, User user) {
    return takeSpot(spot, automobile, user, () -> {
      spot.setReservedBy(null);
      spot.setExpireTs(null);
      spot.setAutomobile(automobile);
      return true;
    });
  }

  public static boolean takeSpotForTime(Spot spot, Automobile automobile, User user, Long timeInSeconds) {
    return takeSpot(spot, automobile, user, () -> {
      spot.setReservedBy(null);
      spot.setExpireTs(now().getEpochSecond() + timeInSeconds);
      spot.setAutomobile(automobile);
      user.setTakenSpot(spot);
      return true;
    });
  }

  public static boolean takeSpot(Spot spot, Automobile automobile, User user, Supplier<Boolean> resultSupplier) {
    if (Objects.isNull(spot) || Objects.isNull(automobile) || Objects.isNull(user)) {
      return false;
    }
    final SpotState spotState = spot.getSpotState();
    if (checkIfCarAllowed(spot.getParking(), automobile) &&
        ((SpotState.RESERVED.equals(spotState) && user.equals(spot.getReservedBy()))
            || SpotState.FREE.equals(spotState))) {
      return resultSupplier.get();
    }
    return false;
  }

  public static void outputParkingState(Parking parking) {
    parking.getParkingSpots().stream()
        .collect(Collectors.groupingBy(Spot::getLevel))
        .forEach((level, spotList) -> {
          log.info("Level: {}", level);
          spotList.forEach(spot -> log.info("(Spot number: {}, State: {})", spot.getNumber(), spot.getSpotState()));
        });
  }
}
