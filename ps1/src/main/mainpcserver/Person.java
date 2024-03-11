package mainpcserver;

import java.io.Serializable;

public class Person implements Serializable {
    private int index;
    private String name;
    private String surname;
    private int age;
    private double salary;

    public Person(int index, String name, String surname, int age, double salary) {
        this.index = index;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.salary = salary;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Person{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}

