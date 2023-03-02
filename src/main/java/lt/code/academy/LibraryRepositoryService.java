package lt.code.academy;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import lt.code.academy.data.Book;
import lt.code.academy.data.Address;
import lt.code.academy.data.Reader;
import lt.code.academy.data.Reservation;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.not;
import static com.mongodb.client.model.Updates.set;

public class LibraryRepositoryService {
    private final MongoCollection<Reader> readerCollection;
    private final MongoCollection<Book> bookCollection;
    private Reader reader;

    public LibraryRepositoryService() {
        MongoClient client = MongoObjectClientProvider.getClient();
        MongoDatabase database = client.getDatabase("taskLibraryDB");
        readerCollection = database.getCollection("readers", Reader.class);
        bookCollection = database.getCollection("books", Book.class);

    }

    public Reader getLoginReader(String name, String surname) {
        reader = readerCollection.find(and(eq("name", name), eq("surname", surname))).first();
        return reader;

    }
    public Book findBook(String title, String author) {
        return bookCollection.find(and(eq("title", title), eq("author", author))).first();

    }
    public void orderBookForReader(Book book){
        LocalDateTime currentDate = LocalDateTime.now();
        Reservation reservation = new Reservation(reader.getName(), reader.getSurname(), currentDate, currentDate.plusDays(7));
        bookCollection.updateOne(eq("id", book.getId()), set("reservation", reservation));

        book.setReservation(reservation);
        addBookToReader(book);
    }
    public FindIterable<Book> getAvailableBooks() {
        return bookCollection.find(eq("reservation", null));

    }
    public FindIterable<Book> getReservationBooks() {
        return bookCollection.find(not(eq("reservation", null)));

    }
    public void updateBookReservation(ObjectId id, LocalDateTime returnDate) {
        bookCollection.updateOne(eq("_id", id), set("reservation.returnDate", returnDate));

    }
    private void addBookToReader(Book book) {
        if(reader.getBooks()==null) {
            reader.setBooks(new HashSet<>());
        }
        reader.getBooks().add(book);
        readerCollection.updateOne(eq("_id", reader.getId()), set("books", reader.getBooks()));

    }
    private void createBooks() {
        List<Book> books = List.of(new Book(null, "Java", "O. Oneal", "jsns854775", 2022, null),
                new Book(null, "C#", "Petraukas", "8888r8r8", 2021, null),
                new Book(null, "Python", "T. Butautas", "99985555", 1999, null),
                new Book(null, "Spring", "J. Kaziukaitis", "qqweqw3eqw", 2020, null),
                new Book(null, "TypeScrip", "N. Petraitis", "dsfsd99641q1", 2023, null));

        bookCollection.insertMany(books);
    }
    private void createReaders() {
        List<Reader> readers = List.of(new Reader(null, "Jonas", "Jonaitis", "+37058457668", Set.of(new Address("LT", "Kaunas", "Savvanoriu av.", "LT-85555")), null),
                new Reader(null, "Petras", "Petraitis", "+3705842554", Set.of(new Address("LT", "Vilnius", "Savanoriu av. 122", "LT-99999")), null));

        readerCollection.insertMany(readers);
    }
public Reader getReader() {
        return reader;
}

}
