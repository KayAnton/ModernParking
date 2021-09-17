package org.parking.building;

import lombok.extern.slf4j.Slf4j;
import org.parking.vehicles.Automobile;
import org.parking.vehicles.Car;
import org.parking.vehicles.Motorbike;

@Slf4j
public class Entrance implements Runnable {

    private volatile Boolean isOpenedTime = true;
    private final Parking parking;

    public Entrance(Parking parking) {
        this.parking = parking;
    }

    public void setOpenedTime(Boolean openedTime) {
        isOpenedTime = openedTime;
    }

    @Override
    public void run() {
        while (isOpenedTime) {
            System.out.println(String.format("Entrance is working..."));
            Automobile automobile = parking.autoComesFromTheRealWorld();
            // parking can accept only cars and motorbikes
            if (automobile instanceof Car || automobile instanceof Motorbike) {
                Spot spot = parking.findEmptySpot();
                if (spot != null) {
                    parking.pushAutomobile(automobile, spot);
                    System.out.println(String.format("%d - level, %d - place is allocated for vehicle %s", spot.getLevel(), spot.getSpotNumber(), automobile));
                    break;
                }
            }
            System.out.println(String.format("I have no more free spots or you are to big for a standard spot - number of free spots - %d", parking.getNumberOfFreeSpots()));
            parking.pushBackToPool(automobile);
        }
    }
}
