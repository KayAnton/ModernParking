package org.parking.model.buildings;

import lombok.Getter;

@Getter
public class Entrance {

  private final Boolean isOpenedTime = true;
  private final Parking parking;

  public Entrance(Parking parking) {
    this.parking = parking;
  }

}
