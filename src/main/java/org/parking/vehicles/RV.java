package org.parking.vehicles;

import org.parking.CarType;

public class RV extends Automobile {
    @Override
    public CarType getType() {
        return CarType.RV;
    }
}
