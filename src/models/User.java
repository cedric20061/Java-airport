package models;

public class User {
    private int id;
    private String username;
    private String password;
    private int role;

    public User(int id, String username, String password, int role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public int getUserId(){
        return this.id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public int getUserRole() {
        return this.role;
    }

    public static User fromString(String line){
        String[] parts = line.split(";");
        return new User(Integer.parseInt(parts[0]), parts[1], parts[2], Integer.parseInt(parts[3]));
    }

    @Override
    public String toString() {
        return id + ";" + username + ";" + password + ";" + role;
    }
}
