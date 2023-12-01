// Online Book Store
package BookStoreApplication;
 
import java.util.*;

// class User start
class User {
    String username;
    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

// class User ends

// class Book start

class Book {

    int id;
    String title;
    String author;
    boolean isAvailable;
    String review;
    double totalRatingPoints;
    int numRating;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.review = "";
        this.totalRatingPoints = 0.0;
        this.numRating = 0;
    }

    public double getAverageRatings() {
        if (numRating == 0) {
            return 0.0;
        }
        return totalRatingPoints / numRating;
    }

    public boolean isAvailable() {
        return false;
    }
}
// class Book ends
 // class Library start

class Library {
    private List<Book> books;

    public Library() {
        books = new ArrayList<>();

        books.add(new Book(1, "A", "James"));
        books.add(new Book(2, "B", "john"));
        books.add(new Book(3, "C", "Aadam"));
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.isAvailable) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    public void addBook(int id, String title, String author) {
        books.add(new Book(id, title, author));
    }

    public Book getBookById(int id) {
        for (Book book : books) {
            if (book.id == id) {
                return book;
            }
        }
        return null; // book not found
    }

     public void borrowBook(int id) {     // method borrowbook
        Book book = getBookById(id);
        if (book == null) {
            System.out.println("Book with ID " + id + "not found");
            return;
        }

        if (!book.isAvailable) {
            System.out.println("Book with ID " + id + "is not avaliable for borrowing");
            return;
        }

        // Book is avalibale mark it as borrowed.
        book.isAvailable = false;
        System.out.println("Book with ID " + id + "has been borrowed");
    }

      public void returnBook(int id) {   // method returnBook
        Book book = getBookById(id);
        if (book == null) {
            System.out.println("Book with ID " + id + "not found");
            return;
        }

        if (book.isAvailable) { 
            System.out.println("Book with ID " + id + "is alraedy available");
            return;
        }

        // Book is return mark it as available.
        book.isAvailable = true;
        System.out.println("Book with ID " + id + "has been returned");

    }

   
    public void rateAndReviewBook(int id, int rating, String review) {   // method rateAndReviewBook
        Book book = getBookById(id);
        if (book == null) {
            System.out.println("Book with ID " + id + "not found");
            return;
        }
        if (rating < 1 || rating > 5) {
            System.out.println("Invalid rating.Rating should be between 1 to 5");
            return;
        }

        // Update the book's rating and review
        book.totalRatingPoints += rating;
        book.numRating++;
        book.review = review;

        System.out.println("Book with ID" + id + "has been rated and reviewed");
    }

}

// class library end

public class BookStoreApplication {
    private static Scanner sc = new Scanner(System.in);
    private static Library library = new Library();
    private static User loggedInUser;
    public static void main(String[] args) {
        showMainMenu();
    }

    private static void borrowBooks() {
        System.out.print("Enter the Book ID you want to borrow : ");
        int id = sc.nextInt();
        sc.nextLine();// consume the character
        library.borrowBook(id);
    }

    private static void returnBook() {    // method returnBook
        System.out.print("Enter the Book ID you want to return : ");
        int id = sc.nextInt();
        sc.nextLine();// consume the newline character
        library.returnBook(id);
    }

   
    private static void rateAndReviewBook() {   // method rataAndReviewBook
        System.out.print("Enter the Book ID you want to rate and review : ");
        int id = sc.nextInt();
        sc.nextLine();// consume the newline character
        System.out.print("Enter the rating(1 to 5) : ");
        int rating = sc.nextInt();
        sc.nextLine();// consume the newline character
        System.out.print("Enter the review : ");
        String review = sc.nextLine();
        library.rateAndReviewBook(id, rating, review);
    }

    private static void showMainMenu() {    // method showMainMenu
        System.out.println("************************");
        System.out.println("welcome to the library  ");
        System.out.println("************************");
        System.out.println("*  1. register         *");
        System.out.println("*  2. Login            *");
        System.out.println("*  3. Exit             *");
        System.out.println("************************");
       System.out.println("\nEnter your choice :");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                register();
                break;
            case 2:
                login();
                break;
            case 3:
                System.out.println("GoodBye");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice please try again");
                showMainMenu();
        }

    }

   private static void register() {    // method register
        System.out.print("Enter your username :");
        String username = sc.nextLine();
        System.out.print("Enter your password :");
        String password = sc.nextLine();
        loggedInUser = new User(username, password);
        System.out.println("*******Ragistration Successfull*******");
        sc.nextLine();
        showBookSection();
    }

     private static void login() {   
        System.out.println("Enter your username : ");
        String username = sc.nextLine();
        System.out.println("Enter your password : ");
        String password = sc.nextLine();

        if (loggedInUser != null && loggedInUser.username.equals(username) && loggedInUser.password.equals(password)) {
            System.out.println("******Login successfull*******");
            showBookSection();
        } else {
            System.out.println("Invalid username and password please try again");
            showMainMenu();
        }
    }

    private static void showBookSection() {  // method showBookSection
        sc.nextLine();
        System.out.println("********************************");
        System.out.println("          Book Section          ");
        System.out.println("********************************");
        System.out.println("*    1. Show All Books         *");
        System.out.println("*    2. Add Books              *");
        System.out.println("*    3. Rate and Review Book   *");
        System.out.println("*    4. Show Available Books   *");
        System.out.println("*    5. Borrow Books           *");
        System.out.println("*    6. Return Book            *");
        System.out.println("*    7. Exit                   *");
        System.out.println("********************************");
        System.out.println("\nEnter your choice : ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                showAllBooks();
                break;
            case 2:
                addBooks();
                break;
            case 3:
                rateAndReviewBook();
                break;
            case 4:
                showAvailableBooks();    
                break;
            case 5:
                borrowBooks();
                break;
            case 6:
                returnBook();
                break;
            case 7:
                System.out.println("Thank you for using the library");
                System.exit(0);
                return;
            default:
                System.out.println("Invalid choice please try again");
        }
        showBookSection();// Show Book Section Again
    }

    private static void addBooks() {  // method addBooks
        System.out.println("Enter the book ID you want to add");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the title of the book :  ");
        String title = sc.nextLine();
        System.out.println();
        System.out.print("Enter the author of the book : ");
        String author = sc.nextLine();
        library.addBook(id, title, author);
    }

    private static void showAllBooks() {  // method showAllBooks
        System.out.println("**********All Books**********");
        List<Book> allBooks = library.getAllBooks();
        displayBooks(allBooks);
    }

    private static void showAvailableBooks() {  // Method show Available Books
        System.out.println("**********Available Books**********");
        List<Book> availableBooks = library.getAvailableBooks();
        displayBooks(availableBooks);
    }

    private static void displayBooks(List<Book> availableBooks) {
        for (Book book : availableBooks) {
            System.out.println("Id    :" + book.id + "   ");
            System.out.println("Title :" + book.title + "   ");
            System.out.println("Author:" + book.author + "   ");
            System.out.print("\tAvailable " + (!book.isAvailable() ? "yes" : "No"));
            System.out.print("\tAverage rating " + book.getAverageRatings() + "   ");
            System.out.print("Review: " + book.review);
            System.out.println();
        }
    }
}
 
