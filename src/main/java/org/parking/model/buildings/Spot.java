package org.parking.model.buildings;

import java.util.Objects;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parking.model.User;
import org.parking.model.enums.SpotState;
import org.parking.model.vehicles.Automobile;

@Slf4j
@Data
@RequiredArgsConstructor
public class Spot {

  private final Parking parking;
  private final int level;
  private final int spotNumber;
  private Automobile automobile;
  private User reservedBy;
  private Long expireTs;


  public void clear() {
//    log.debug("{}.{} left the parking", automobile.getType(), automobile);
    automobile.getOwner().setTakenSpot(null);
    automobile = null;
    reservedBy = null;
    expireTs = null;
  }

  public SpotState getSpotState() {
    if (Objects.nonNull(automobile)) {
      return SpotState.TAKEN;
    } else if (Objects.nonNull(reservedBy)) {
      return SpotState.RESERVED;
    }
    return SpotState.FREE;
  }

  public String toString() {
    return String.format("Level: %s, Spot number: %s", level, spotNumber);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Spot spot = (Spot) o;
    return level == spot.level && spotNumber == spot.spotNumber;
  }

  @Override
  public int hashCode() {
    return Objects.hash(level, spotNumber);
  }

}
