package com.avaj.simulator;
import com.avaj.simulator.vehicles.AircraftFactory;
import com.avaj.weather.WeatherProvider;

import java.io.*;

public class Simulator {

    public static PrintWriter writer;
    public static int cycles;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";


    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println(ANSI_RED + "Usage: runAvaj.sh <file>\n\tPlease provide a file!" + ANSI_RESET);
            return;
        }
        String filename = args[0];

        File simulationFile = new File("simulation.txt");
        try {
            writer = new PrintWriter(simulationFile);
        } catch (FileNotFoundException fne) {
            System.out.println(ANSI_RED + "Error: " + fne.getMessage() + ANSI_RESET);
            return;
        }
        if (simulationFile.exists() && !simulationFile.isDirectory())
            writer.print("");

        AircraftFactory aircraftFactory = new AircraftFactory();
        WeatherTower weatherTower = new WeatherTower();
        try {
            FileInputStream fstream = new FileInputStream(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            int line = 1;
            int parsedLine = 1;
            String[] splitted;

            while ((strLine = br.readLine()) != null) {
                if (parsedLine == 1)
                    try {
                        splitted = strLine.split(" ");
                        if (splitted.length == 1 && splitted[0].isEmpty()) {
                            line++;
                            continue;
                        }
                        cycles = Integer.parseInt(strLine);
                        if (cycles < 0)
                        {
                            System.out.println(ANSI_RED + "Error: first line of scenario file must be a POSITIVE integer." + ANSI_RESET);
                            return;
                        }
                    } catch (NumberFormatException nfe) {
                        System.out.println(ANSI_RED + "Error: first line of scenario file must be an integer."  + ANSI_RESET);
                        return;
                    }
                else {
                    splitted = strLine.split(" ");
                    if (splitted.length == 1 && splitted[0].isEmpty()) {
                        line++;
                        continue;
                    }
                    if (splitted.length != 5)
                        throw new Exception("Error: line " + line + ": there must be 5 parameters.");

                    try {
                        aircraftFactory.newAircraft(
                                splitted[0],
                                splitted[1],
                                Integer.parseInt(splitted[2]),
                                Integer.parseInt(splitted[3]),
                                Integer.parseInt(splitted[4])
                        ).registerTower(weatherTower);
                    } catch (NumberFormatException nfe) {
                        System.out.println(ANSI_RED + "Error: line " + line + ": parameter 3 to 5 must be integers."  + ANSI_RESET);
                        return;
                    } catch (Exception ex) {
                        System.out.println(ANSI_RED + "Error: line " + line + ": "+ ex.getMessage() + ANSI_RESET);
                        return;
                    }
                }
                parsedLine++;
                line++;
            }
            br.close();
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Error: " + e.getMessage() + ANSI_RESET);
            return;
        }

        WeatherProvider weatherProvider = WeatherProvider.getProvider();
        int i = 1;
        while (i <= cycles) {
            writer.println("\n[INFO] : New cycle n°" + i );
            weatherTower.changeWeather();
            writer.println("[INFO]: End of cycle n°" + i );
            i++;
        }
        writer.close();
    }
}
