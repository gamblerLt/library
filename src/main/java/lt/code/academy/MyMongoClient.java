package lt.code.academy;



import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lt.code.academy.data.Book;
import lt.code.academy.data.Reader;
import org.bson.Document;

import java.util.ArrayList;
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
        MyMongoClient myMongoClient = new MyMongoClient(database.getCollection("readers", Reader.class),
                                                        database.getCollection("books", Book.class))  ;


        //apl create books

        Scanner scanner = new Scanner(System.in);
        String action;
        do {
            myMongoClient.menu();
            action = scanner.nextLine();
            myMongoClient.readerSelection(scanner, action);
            while (!action.equals("5")) ;
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
