public class Main {
  publis
  static void countBooks(String[] args) {
    Library book1 = new Library("Book1", new Book[] { new Book("Miranda", "by George", 1234) });
  }

  class Book { // Preparing Book Class
    private String title; // title (a String)
    private String author; // author (a String)
    private int ID; // int (ID)

    public Book(String aTitle, String author, int id) {
      this.title = aTitle;
      this.author = author;
      this.ID = id;
    }

    private String title;

    public String getTitle() {
      return title;
    }

    private String author;

    public String getAuthor() {
      return author;
    }

    private String ID;

    public String getID() {
      return ID;
    }

    public void print() {
      System.out.println("Title of the book" + title);
      System.out.println("Author of the book" + ID);
    }
  }

  class Library {
    public Library(int id_3, Book[] books) {
      this.id_3 = id_3;
      this.books = books;
    }

    private int id_3;
  public int getID)
    {
      return books;
    }
  }

  public static void main(String[] args) {
    List<String> books = new BookName<>;

    books.add("Marlyn");
    books.add("Computer Science");
    books.add("Gatsby");

    System.out.println(books);
  }
