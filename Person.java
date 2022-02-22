package jsonFormater;

/**
 * @author Tatek Ahmed on 2/17/2022
 **/

public class Person {
    private final String name;
    private final boolean employed;
    private final int age;
    private final float salary;
    private final Address address;
    private final Company job;
    private String[] favoriteSports;;

    public Person(String name, Boolean employed, int age, float salary, Address address, Company job, String[] sports) {
        this.name = name;
        this.employed = employed;
        this.age = age;
        this.salary = salary;
        this.address = address;
        this.job = job;
        this.favoriteSports = sports;
    }
}
