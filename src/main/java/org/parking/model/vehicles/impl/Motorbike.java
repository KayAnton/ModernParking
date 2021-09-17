package org.parking.model.vehicles.impl;

import org.parking.model.enums.CarType;
import org.parking.model.vehicles.Automobile;

public class Motorbike implements Automobile {

  @Override
  public CarType getType() {
    return CarType.MOTORBIKE;
  }
}
