package lt.code.academy.data;

import org.bson.types.ObjectId;

import java.util.Set;

public class Reader {
    private ObjectId id;
    private String name;
    private String surname;
    private String phone;

    private Set<Book> books;

    public Reader(ObjectId id, String name, String surname, String phone, Set<Book> books) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.books = books;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Reader{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", books=" + books +
                '}';
    }
}
