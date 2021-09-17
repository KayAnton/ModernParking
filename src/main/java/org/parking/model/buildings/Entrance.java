package org.parking.model.buildings;

import lombok.Data;

@Data
public class Entrance {

  private volatile Boolean isOpenedTime = true;
  private final Parking parking;

  public Entrance(Parking parking) {
    this.parking = parking;
  }

}
