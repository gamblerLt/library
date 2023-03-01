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

import java.util.List;
import java.util.Scanner;

public class MyMongoClient {

    private final MongoCollection<Reader> readerCollection;
    private final MongoCollection<Book> bookCollection;

    public MyMongoClient(MongoCollection<Reader> readerCollection, MongoCollection<Book> bookCollection) {
        this.readerCollection = readerCollection;
        this.bookCollection = bookCollection;
    }

    public static void main(String[] args) {

        MongoClient client = MongoObjectClientProvider.getClient();
        MongoDatabase database = client.getDatabase("taskLibraryDB");
        MyMongoClient aplication = new MyMongoClient(database.getCollection("readers", Reader.class),
                database.getCollection("books", Book.class));


        //apl create books

        Scanner scanner = new Scanner(System.in);
        String action;
        do {
            aplication.menu();
            action = scanner.nextLine();
            aplication.readerSelection(scanner, action);
        } while (!action.equals("5"));
    }
        private void readerSelection(Scanner scanner, String action){
            switch (action) {
                case "1" -> orderBook(scanner);
                case "2" -> showAvailableBooks();
                case "3" -> System.out.println("3");
                case "4" -> System.out.println("4");
                case "5" -> System.out.println("Progrma baigė darbą");
                default -> System.out.println("Tokio veiksmo nera");
            }
            }

        private void orderBook(Scanner scanner) {
            System.out.println("Iveskite norimos knygos pavadinima");
            String title = scanner.nextLine();
            System.out.println("Iveskite knygos autoriu");
            String author = scanner.nextLine();
            FindIterable<Book> books = bookCollection.find(and(eq("title", title), eq("author", author)));

            MongoCursor<Book> book = books.iterator();
            if(!book.hasNext()) {
                System.out.println("Tokios knygos nera");
                return;
        }
            System.out.println("Iveskite varda ir pavarde");
        }

        private void showAvailableBooks() {
        FindIterable<Book> books = bookCollection.find();
        for(Book book: books) {
            if(book.getReservation()==null) {
                System.out.printf("%s %s %s %n", book.getTitle(), book.getAuthor(), book.getPublishYear());
            }
        }
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

   private  void createBooks() {
        List<Book> books = List.of(new Book(null, "Poezija", "J. Marcinkevisius", "kdhd5566", 2011, null),
                new Book (null, "Poezija", "J. Marcinkevisius", "kdhd5566", 2011, null),
                new Book (null, "Noveles", "A. Vienuolis", "kdhd1166", 1988, null),
                new Book (null, "Proza", "A. Prozevicz", "kd22226", 1711, null),
                new Book (null, "Profundis", "O. Wilde", "sss55855", 1888, null),
                new Book (null, "ABC", "A. Adomaitis", "kdh22556", 2010, null));

        bookCollection.insertMany(books);
   }
}
