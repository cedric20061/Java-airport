package controllers;

import models.Flight;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FlightController {

    private static final String FILE_PATH = "./data/flights.txt";

    public static List<Flight> getFlights() {
        List<Flight> flights = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                flights.add(Flight.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flights;
    }

    public static boolean addFlight(Flight flight) {
        if (getFlightById(flight.getFlightId()) != null) {
            System.out.println("Flight already exists.");
            return false;
        }
        List<Flight> flights = getFlights();
        flights.add(flight);
        return saveFlights(flights);
    }

    public static boolean updateFlight(String flightId, int newSeats) {
        List<Flight> flights = getFlights();
        for (Flight flight : flights) {
            if (flight.getFlightId().equals(flightId)) {
                flight.setSeatsNumber(newSeats);
                return saveFlights(flights);
            }
        }
        System.out.println("Flight not found.");
        return false;
    }

    public static boolean deleteFlight(String flightId) {
        List<Flight> flights = getFlights();
        boolean removed = flights.removeIf(flight -> flight.getFlightId().equals(flightId));
        if (removed) {
            return saveFlights(flights);
        }
        System.out.println("Flight not found.");
        return false;
    }

    public static Flight getFlightById(String id) {
        return getFlights()
                .stream()
                .filter(f -> f.getFlightId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static boolean saveFlights(List<Flight> flights) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Flight flight : flights) {
                bw.write(flight.toString());
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void displayFlights() {
        List<Flight> flights = getFlights();
        if (flights.isEmpty()) {
            System.out.println("No available flights.");
            return;
        }

        System.out.println("════════════════════ Flight List ════════════════════");
        System.out.println("╔════════════╦══════════════════╦═══════════════╦════════════════════╗");
        System.out.printf("║ %-10s ║ %-16s ║ %-13s ║ %-18s ║%n", "Flight ID", "Destination", "Departure", "Available Seats");
        System.out.println("╠════════════╬══════════════════╬═══════════════╬════════════════════╣");

        flights.forEach(flight -> {
            System.out.printf("║ %-10s ║ %-16s ║ %-13s ║ %-18d ║%n",
                    flight.getFlightId(),
                    flight.getDestination(),
                    flight.getDateDepart(),
                    flight.getSeatsNumber());
        });

        System.out.println("╚════════════╩══════════════════╩═══════════════╩════════════════════╝");
    }
}
