package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Flight;
import models.Reservation;

public class AirportHandler {
    public static List<Flight> getFlights() {
        List<Flight> flights = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./data/flights.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                flights.add(Flight.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flights;
    };
    public static List<Reservation> getReservations() {
        List<Reservation> reservations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./data/reservations.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                reservations.add(Reservation.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reservations;
    }
    public static void saveReservations(List<Reservation> reservations) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./data/reservations.txt"))) {
            for (Reservation reservation : reservations) {
                bw.write(reservation.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveFlights(List<Flight> flights) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./data/flights.txt"))) {
            for (Flight flight : flights) {
                bw.write(flight.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String addFlight(Flight flight) {
        List<Flight> flights = getFlights();
        flights.add(flight);
        saveFlights(flights);
        return "Flight added successfully.";
    }

    public static String removeFlight(String flightNumber) {
        List<Flight> flights = getFlights();
        boolean removed = flights.removeIf(flight -> flight.getFlightId().equals(flightNumber));
        if (removed) {
            saveFlights(flights);
            return "Flight removed successfully.";
        } else {
            return "Flight not found.";
        }
    }

    public static String updateFlight(Flight updatedFlight) {
        List<Flight> flights = getFlights();
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getFlightId().equals(updatedFlight.getFlightId())) {
                flights.set(i, updatedFlight);
                saveFlights(flights);
                return "Flight updated successfully.";
            }
        }
        return "Flight not found.";
    }

    public static String bookFlight(Reservation reservation) {
        List<Flight> flights = getFlights();
        List<Reservation> reservations = getReservations();
        for (Flight flight : flights) {
            if (flight.getFlightId().equals(reservation.getFlightId())) {
                if (flight.getSeatsNumber() >= reservation.getSeatsReserved()) {
                    flight.setSeatsNumber(flight.getSeatsNumber() - reservation.getSeatsReserved());
                    saveFlights(flights);
                    reservations.add(reservation);
                    saveReservations(reservations);
                    return "Booking successful.";
                } else {
                    return "Not enough seats available.";
                }
            }
        }
        return "Flight not found.";
    }
}
