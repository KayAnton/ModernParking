package org.parking.model;

import lombok.Data;
import org.parking.model.buildings.Spot;
import org.parking.model.vehicles.Automobile;

@Data
public class User {

  private final Long userId;
  private Automobile auto;
  private Spot takenSpot;

}
