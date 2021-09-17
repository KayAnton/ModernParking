package org.parking.vehicles;


import lombok.Data;
import org.parking.CarType;

@Data
public abstract class Automobile {

  public abstract CarType getType();

  public static Automobile createAutomobileByType(int typeId) {
    switch (typeId) {
    case 0:
      return new Car();
    case 1:
      return new Track();
    case 2:
      return new RV();
    case 3:
      return new Motorbike();
    default:
      return new Car();
    }
  }
}
