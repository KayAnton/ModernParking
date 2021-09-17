package org.parking.vehicles;

import org.parking.CarType;

public class Track extends Automobile {
    @Override
    public CarType getType() {
        return CarType.TRACK;
    }
}
