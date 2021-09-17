package org.parking.model.buildings;

import java.util.Objects;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.parking.model.User;
import org.parking.model.enums.SpotState;
import org.parking.model.vehicles.Automobile;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class Spot {

  private final Parking parking;

  @EqualsAndHashCode.Include
  private final int level;

  @EqualsAndHashCode.Include
  private final int spotNumber;

  private Automobile automobile;
  private User reservedBy;
  private Long expireTs;


  public void clear() {
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
}
