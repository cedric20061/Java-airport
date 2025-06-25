package controllers;

import models.User;

import java.io.*;

public class UserController {

    private static final String FILE_PATH = "./data/users.txt";

    public static User register(String username, String password, int role) {
        if (username.isBlank() || password.isBlank()) {
            System.out.println("Username and password cannot be empty.");
            return null;
        }

        if (findUserByUsername(username) != null) {
            System.out.println("Username already exists.");
            return null;
        }

        int newId = getLastUserId() + 1;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(newId + "," + username + "," + password + "," + role);
            bw.newLine();
            return new User(newId, username, password, role);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User login(String username, String password) {
        User user = findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    private static User findUserByUsername(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4 && parts[1].equals(username)) {
                    return User.fromString(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int getLastUserId() {
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
}
