package org.parking.vehicles;

import org.parking.CarType;

public class Motorbike extends Automobile {

  @Override
  public CarType getType() {
    return CarType.MOTORBIKE;
  }
}
