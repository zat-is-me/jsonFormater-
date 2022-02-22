package jsonFormater;

/**
 * @author Tatek Ahmed on 2/17/2022
 **/

public class Company {
    private final String name;
    private final String city;
    private final Address address;

    public Company(String name, String city, Address address) {
        this.address = address;
        this.city = city;
        this.name = name;

    }
}
