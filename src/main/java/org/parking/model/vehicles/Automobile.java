package org.parking.model.vehicles;


import org.parking.model.User;
import org.parking.model.enums.CarType;

public interface Automobile {

  User getOwner();

  CarType getType();

}
