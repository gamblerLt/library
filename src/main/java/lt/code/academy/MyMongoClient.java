package lt.code.academy;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

import com.mongodb.client.MongoIterable;
import lt.code.academy.data.Book;
import lt.code.academy.data.Reader;
import lt.code.academy.data.Reservation;

import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;

public class MyMongoClient {
    private final LibraryRepositoryService repositoryService;
    public LibraryRepositoryService(LibraryRepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    //  private final MongoCollection<Reader> readerCollection;
   // private final MongoCollection<Book> bookCollection;

   /* public MyMongoClient(MongoCollection<Reader> readerCollection, MongoCollection<Book> bookCollection) {
        this.readerCollection = readerCollection;
        this.bookCollection = bookCollection;
    }*/

    public static void main(String[] args) {

        /*MongoClient client = MongoObjectClientProvider.getClient();
        MongoDatabase database = client.getDatabase("taskLibraryDB");
        MyMongoClient aplication = new MyMongoClient(database.getCollection("readers", Reader.class),
                database.getCollection("books", Book.class));*/
        MyMongoClient aplication = new MyMongoClient(new LibraryRepositoryService());

        Scanner scanner = new Scanner(System.in);
        aplication.login(scanner);
        String action;

        do {
            aplication.menu();
            action = scanner.nextLine();
            aplication.readerSelection(scanner, action);
        } while (!action.equals("5"));
    }
    private void login(Scanner scanner) {
        Reader reader;

        do {
            System.out.println("Prasome prisijungti");
            System.out.println("Iveskite varda");
            String name = scanner.nextLine();
            System.out.println("Iveskite pavarde");
            String surname = scanner.nextLine();
            reader = repositoryService.getLoginReader(name, surname);
            if (reader == null) {
                System.out.println("Tokio vartotojo nera");
            }
        } while (reader == null);
    }


        //apl create books

        /*Scanner scanner = new Scanner(System.in);
        String action;
        do {
            aplication.menu();
            action = scanner.nextLine();
            aplication.readerSelection(scanner, action);
        } while (!action.equals("5"));
    }*/
        private void readerSelection(Scanner scanner, String action){
            switch (action) {
                case "1" -> orderBook(scanner);
                case "2" -> showAvailableBooks();
                case "3" -> System.out.println("3");
                case "4" -> extendBookReservation(scanner);
                case "5" -> System.out.println("Progrma baigė darbą");
                default -> System.out.println("Tokio veiksmo nera");
            }
            }

        private void orderBook(Scanner scanner) {
            System.out.println("Iveskite norimos knygos pavadinima");
            String title = scanner.nextLine();
            System.out.println("Iveskite knygos autoriu");
            String author = scanner.nextLine();

            Book book = repositoryService.findBook(title, author);
            if(book == null) {
                System.out.println("Tokios knygos nera");
                return;
            }
            repositoryService.orderBookForReader(book);

/*            FindIterable<Book> books = bookCollection.find(and(eq("title", title), eq("author", author)));

            MongoCursor<Book> book = books.iterator();
            if(!book.hasNext()) {
                System.out.println("Tokios knygos nera");
                return;
        }
            System.out.println("Iveskite varda ir pavarde");*/
        }

        private void showAvailableBooks() {
        FindIterable<Book> books = repositoryService.getAvailableBooks();
        for(Book book: books) {
            /*if(book.getReservation()==null) {
                System.out.printf("%s %s %s %n", book.getTitle(), book.getAuthor(), book.getPublishYear());
            }*/
            System.out.printf("%s %s %s%n", book.getTitle(), book.getAuthor(), book.getPublishYear());
        }
        }

        private  void showReservedBooks() {
            FindIterable<Book> books = repositoryService.getReservationBooks();
            for(Book book: books) {
                System.out.println("%s %s %s %s %s %n", book.getTitle(), book.getAuthor(), book.getReservation().getName(),book.getReservation().getSurname(), book.getReservation().getReturnDate()());
            }
        }
        private void extendBookReservation(Scanner scanner) {
            System.out.println("Jusu paimto knygos:");
            Reader reader = repositoryService.getReader();
            if(reader.getBooks()==null) {
                System.out.println("Knygu nera paimta");
                return;
            }
            for(Book book: reader.getBooks()) {
                System.out.printf("%s %s %s%n",book.getTitle(), book.getAuthor(), book.getReservation().getReturnDate());
            }
            System.out.println("Iveskite norimos knygos pavadinima:");
            String title = scanner.nextLine();
            System.out.println("Iveskite autoriu");
            String author = scanner.nextLine();
            Book book = reader.getBooks()
                    .stream()
                    .filter(b -> b.getTitle().equals(title)&& b.getAuthor().equals(author))
                    .findFirst()
                    .orElse(null);
            if(book == null) {
                System.out.println("Tokios knygos nesate pasiemes");
                return;
            }
            Reservation reservation = book.getReservation();
            reservation.setReturnDate(reservation.getReturnDate().plusDays(7));
            repositoryService.updateBookReservation(book.getId(), reservation.getReturnDate());
        }

        private void menu() {
            System.out.println("""
                    1. uzsakyti
                    2. laisvos knygos
                    3. paimtos knygos
                    4. pratesimas
                    5. iseiti
                    """);
    }

 /*  private  void createBooks() {
        List<Book> books = List.of(new Book(null, "Poezija", "J. Marcinkevisius", "kdhd5566", 2011, null),
                new Book (null, "Poezija", "J. Marcinkevisius", "kdhd5566", 2011, null),
                new Book (null, "Noveles", "A. Vienuolis", "kdhd1166", 1988, null),
                new Book (null, "Proza", "A. Prozevicz", "kd22226", 1711, null),
                new Book (null, "Profundis", "O. Wilde", "sss55855", 1888, null),
                new Book (null, "ABC", "A. Adomaitis", "kdh22556", 2010, null));

        bookCollection.insertMany(books);
   }*/
}
