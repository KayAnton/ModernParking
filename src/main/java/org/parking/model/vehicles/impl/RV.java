package org.parking.model.vehicles.impl;

import org.parking.model.User;
import org.parking.model.enums.CarType;
import org.parking.model.vehicles.AbstractAutomobile;

public class RV extends AbstractAutomobile {

  public RV(User user) {
    super(user);
  }

  public CarType getType() {
    return CarType.RV;
  }
}
