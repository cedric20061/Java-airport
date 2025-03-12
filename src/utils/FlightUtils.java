package src.utils;
import java.util.List;

import src.models.Flight;

public class FlightUtils {
    public static void displayFlights(List<Flight> flights) {
        if (flights.isEmpty()) {
            System.out.println("No flights available.");
            return;
        }

        System.out.println("\nList of Available Flights:");
        System.out.println("---------------------------------------------------");
        System.out.printf("%-10s | %-15s | %-12s | %-5s%n", "Flight Code", "Destination", "Date", "Seats");
        System.out.println("---------------------------------------------------");

        for (Flight flight : flights) {
            System.out.printf("%-10s | %-15s | %-12s | %-5d%n",
                    flight.getFlightCode(),
                    flight.getDestination(),
                    flight.getDate(),
                    flight.getAvailableSeats()
            );
        }
        System.out.println("---------------------------------------------------\n");
    }
}
