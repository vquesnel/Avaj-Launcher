package com.avaj.simulator.vehicles;

import com.avaj.weather.Coordinates;
import java.io.*;

public class AircraftFactory {

    public Flyable newAircraft(String type, String name, int longitude, int latitude, int height) throws Exception {
        Coordinates coordinates = new Coordinates(longitude, latitude, height);

        if (type.toLowerCase().equals("baloon"))
            return new Baloon(name, coordinates);
        else if (type.toLowerCase().equals("jetplane"))
            return new JetPlane(name, coordinates);
        else if (type.toLowerCase().equals("helicopter"))
            return new Helicopter(name, coordinates);
        throw  new Exception("must be a Flyable name like :\n\t- Helicopter\n\t- JetPlane\n\t- Baloon");
    }
}
