package controllers;

import models.Flight;
import models.Reservation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationController {

    private static final String FILE_PATH = "./data/reservations.txt";

    public static int bookFlight(int clientId, String clientName, String flightId, int seats) {
        List<Flight> flights = FlightController.getFlights();
        List<Reservation> reservations = getReservations();

        for (Flight flight : flights) {
            if (flight.getFlightId().equals(flightId)) {
                if (flight.getSeatsNumber() >= seats) {
                    flight.setSeatsNumber(flight.getSeatsNumber() - seats);
                    FlightController.saveFlights(flights);

                    int newId = getLastReservationId() + 1;
                    Reservation reservation = new Reservation(newId, clientId, clientName, flightId, seats);
                    reservations.add(reservation);
                    saveReservations(reservations);
                    return newId;
                } else {
                    System.out.println("Not enough seats available.");
                    return -1;
                }
            }
        }
        System.out.println("Flight not found.");
        return -2;
    }

    public static List<Reservation> getReservations() {
        List<Reservation> reservations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                reservations.add(Reservation.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public static List<Reservation> getUserReservations(int userId) {
        List<Reservation> userReservations = new ArrayList<>();
        for (Reservation res : getReservations()) {
            if (res.getClientId() == userId) {
                userReservations.add(res);
            }
        }
        return userReservations;
    }

    public static boolean updateReservation(int reservationId, int newSeats) {
        List<Reservation> reservations = getReservations();
        List<Flight> flights = FlightController.getFlights();

        Reservation targetRes = null;
        for (Reservation r : reservations) {
            if (r.getId() == reservationId) {
                targetRes = r;
                break;
            }
        }

        if (targetRes == null) {
            System.out.println("Reservation not found.");
            return false;
        }

        Flight flight = FlightController.getFlightById(targetRes.getFlightId());
        if (flight == null) {
            System.out.println("Associated flight not found.");
            return false;
        }

        int difference = newSeats - targetRes.getSeatsReserved();
        if (difference > 0 && flight.getSeatsNumber() < difference) {
            System.out.println("Not enough seats available.");
            return false;
        }

        // Update seat availability
        flight.setSeatsNumber(flight.getSeatsNumber() - difference);
        targetRes.setSeatsReserved(newSeats);

        // Save changes
        saveReservations(reservations);
        FlightController.saveFlights(flights);

        System.out.println("Reservation updated successfully.");
        return true;
    }


    public static boolean cancelReservation(String clientName, String flightId) {
        List<Flight> flights = FlightController.getFlights();
        List<Reservation> reservations = getReservations();

        for (Reservation reservation : reservations) {
            if (reservation.getClientName().equals(clientName) && reservation.getFlightId().equals(flightId)) {
                reservations.remove(reservation);
                saveReservations(reservations);
                for (Flight flight : flights) {
                    if (flight.getFlightId().equals(flightId)) {
                        flight.setSeatsNumber(flight.getSeatsNumber() + reservation.getSeatsReserved());
                        FlightController.saveFlights(flights);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void saveReservations(List<Reservation> reservations) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Reservation res : reservations) {
                bw.write(res.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getLastReservationId() {
        int lastId = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    lastId = Integer.parseInt(parts[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastId;
    }

    public static void displayReservations(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        System.out.println("════════════════════ Reservation List ════════════════════");
        System.out.println("╔════════════╦══════════════════╦═══════════════╦════════════════════╗");
        System.out.printf("║ %-10s ║ %-16s ║ %-13s ║ %-18s ║%n", "Flight ID", "Destination", "Departure", "Seats Reserved");
        System.out.println("╠════════════╬══════════════════╬═══════════════╬════════════════════╣");

        for (Reservation res : reservations) {
            Flight flight = FlightController.getFlightById(res.getFlightId());
            if (flight != null) {
                System.out.printf("║ %-10s ║ %-16s ║ %-13s ║ %-18d ║%n",
                        flight.getFlightId(),
                        flight.getDestination(),
                        flight.getDateDepart(),
                        res.getSeatsReserved());
            }
        }

        System.out.println("╚════════════╩══════════════════╩═══════════════╩════════════════════╝");
    }
}
