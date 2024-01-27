package dao.entities;

public class Company {
    public int Id;
    public String name;
    public String email;
    public String password;

    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
