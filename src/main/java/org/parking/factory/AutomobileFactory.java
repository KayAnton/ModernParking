package org.parking.factory;

import org.parking.model.vehicles.Automobile;
import org.parking.model.vehicles.impl.Car;
import org.parking.model.vehicles.impl.Motorbike;
import org.parking.model.vehicles.impl.RV;
import org.parking.model.vehicles.impl.Track;

public class AutomobileFactory {

  public static Automobile createAutomobileByType(int typeId) {
    return switch (typeId) {
      case 1 -> new Track();
      case 2 -> new RV();
      case 3 -> new Motorbike();
      default -> new Car();
    };
  }

}
