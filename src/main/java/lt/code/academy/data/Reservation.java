package lt.code.academy.data;

import java.time.LocalDateTime;

public class Reservation {
    private String name;
    private String surname;
    private LocalDateTime took;
    private LocalDateTime returnDate;

    public Reservation() {}

    public Reservation(String name, String surname, LocalDateTime took, LocalDateTime returnDate) {
        this.name = name;
        this.surname = surname;
        this.took = took;
        this.returnDate = returnDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDateTime getTook() {
        return took;
    }

    public void setTook(LocalDateTime took) {
        this.took = took;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", took=" + took +
                ", returnDate=" + returnDate +
                '}';
    }
}
