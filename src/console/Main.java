package console;

import java.util.Scanner;

import main.AirportHandler;
import models.Flight;
import models.Reservation;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("1. Gérer les vols");
            System.out.println("2. Gérer les réservations");
            System.out.println("3. Voir les vols");

            int choix = sc.nextInt();
            sc.nextLine(); // flush
            switch (choix) {
                case 1:
                    System.out.println("1. Ajouter un vol");
                    System.out.println("2. Mettre a jour un vol");
                    System.out.println("3. Supprimer un vol");
                    choix = sc.nextInt();
                    sc.nextLine(); // flush
                    switch (choix) {
                        case 1:
                            System.out.print("Numéro du vol : ");
                            String num = sc.nextLine();
                            System.out.print("Destination : ");
                            String dest = sc.nextLine();
                            System.out.print("Date départ (YYYY-MM-DD) : ");
                            String date = sc.nextLine();
                            System.out.print("Nombre de places : ");
                            int places = sc.nextInt();

                            Flight flight = new Flight(num, dest, date, places);
                            AirportHandler.addFlight(flight);
                            System.out.println("Vol ajouté !");
                            break;

                        case 2:
                            System.out.print("Numéro du vol : ");
                            String flightNumber = sc.nextLine();
                            Flight flightToUpdate = AirportHandler.getFlights().stream()
                                    .filter(f -> f.getFlightId().equals(flightNumber))
                                    .findFirst()
                                    .orElse(null);
                            if (flightToUpdate != null) {
                                System.out.print("Nouveau nombre de places : ");
                                int newSeats = sc.nextInt();
                                flightToUpdate.setSeatsNumber(newSeats);
                                AirportHandler.saveFlights(AirportHandler.getFlights());
                                System.out.println("Vol mis à jour !");
                            } else {
                                System.out.println("Vol non trouvé.");
                            }
                            break;
                        case 3:
                            System.out.print("Numéro du vol à supprimer : ");
                            String flightToRemove = sc.nextLine();
                            String result = AirportHandler.removeFlight(flightToRemove);
                            System.out.println(result);
                            break;
                        default:
                            break;
                    }
                case 2:
                    System.out.print("Nom du client : ");
                    String clientName = sc.nextLine();
                    System.out.print("Numéro du vol : ");
                    String flightId = sc.nextLine();
                    System.out.print("Nombre de places réservées : ");
                    int seatsReserved = sc.nextInt();
                    Reservation reservation = new Reservation(clientName, flightId, seatsReserved);
                    AirportHandler.bookFlight(reservation);
                    System.out.println(flightId + " réservé pour " + clientName + " avec " + seatsReserved + " places.");
                    break;

                case 3:
                    AirportHandler.displayFlights();
                    // AirportHandler.getFlights().forEach(System.out::println);
                    // AirportHandler.getFlights().forEach(flightItem -> {
                    //     System.out.println("Vol: " + flightItem.getFlightId() + ", Destination: " + flightItem.getDestination() +
                    //             ", Date: " + flightItem.getDateDepart() + ", Places disponibles: " + flightItem.getSeatsNumber());
                    // });
                    break;
                default:
                    break;
            }
        }
    }
}
