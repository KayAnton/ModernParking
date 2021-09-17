package org.parking.factory;

import org.parking.model.User;
import org.parking.model.enums.CarType;
import org.parking.model.vehicles.Automobile;
import org.parking.model.vehicles.impl.Car;
import org.parking.model.vehicles.impl.Motorbike;
import org.parking.model.vehicles.impl.RV;
import org.parking.model.vehicles.impl.Track;

public final class AutomobileFactory {

  public static Automobile createAutomobileByTypeId(User user, int typeId) {
    return switch (typeId) {
      case 1 -> new Track(user);
      case 2 -> new RV(user);
      case 3 -> new Motorbike(user);
      default -> new Car(user);
    };
  }

  public static Automobile createAutomobileByType(User user, CarType carType) {
    return switch (carType) {
      case TRACK -> new Track(user);
      case RV -> new RV(user);
      case MOTORBIKE -> new Motorbike(user);
      default -> new Car(user);
    };
  }

}
