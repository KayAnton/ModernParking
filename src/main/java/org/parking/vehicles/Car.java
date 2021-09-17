package org.parking.vehicles;

import org.parking.CarType;

public class Car extends Automobile {
    @Override
    public CarType getType() {
        return CarType.CAR;
    }
}
