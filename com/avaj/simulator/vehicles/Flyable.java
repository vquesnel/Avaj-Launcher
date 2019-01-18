package com.avaj.simulator.vehicles;

import com.avaj.simulator.WeatherTower;

public interface Flyable {
    void updateConditions();
    void registerTower(WeatherTower weatherTower);
}
