import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

abstract class BookLibrary {
    private String title;
    private String author;
    private int year;
    private boolean isAvailable;

    public BookLibrary(String title,String author,int year){
        this.title=title;
        this.author=author;
        this.year=year;
        this.isAvailable=true;
    }
    
    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public Integer getYear(){
        return year;
    }

    public Boolean getIsAvailable(){
        return isAvailable;
    }
    public void setIsAvailable(Boolean isAvailable){
        this.isAvailable=isAvailable;
    }
}

class BookLap extends BookLibrary{
    public BookLap(String title,String author,int year){
        super(title, author, year);
    }
}
public class Library_Book {
    public static void main(String[] args){
        ArrayList<BookLap> Books = new ArrayList<>();
        try(BufferedReader Book_Read = new BufferedReader(new FileReader("Book_Library.txt"))){
                String Line;
                while((Line=Book_Read.readLine())!=null){
                    String[] parts =Line.split(",");
                    String title=parts[0];
                    String author=parts[1];
                    Integer year=(Integer.parseInt(parts[2]));
                    Boolean isAvaliable=(Boolean.parseBoolean(parts[3]));
                    BookLap Book=new BookLap(title, author, year);
                    Book.setIsAvailable(isAvaliable);
                    Books.add(Book);
                    System.out.println(Line);
                }
                Book_Read.close();
            }
        catch(IOException e){
                System.out.println("Error: "+e.getMessage());
            }
        Scanner scan=new Scanner(System.in);
        int choice;
        int n=0;
            System.out.println("---Select one option---");
            System.out.println("1: Add book");
            System.out.println("2: Show All Book");
            System.out.println("3: Search the book");
            System.out.println("4: Lending book");
            System.out.println("5: Return book");
            System.out.println("6: Delete book");
            System.out.println("7: Exit");
            System.out.println("____________");
        while (n==0){
            System.out.println("Enter your choic please:");
            try{
                choice=scan.nextInt();
            }
            catch(InputMismatchException e){
                System.out.println("Please Enter number\nError: "+e.getMessage());
                scan.nextLine();
                continue;
            }
            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    scan.nextLine();
                    String title=scan.nextLine();
                    System.out.print("\n Enter author: ");
                    String author = scan.nextLine();  
                    System.out.print("\n Enter year: ");
                    int year=scan.nextInt();
                    scan.nextLine();
                    BookLap book = new BookLap(title,author,year);
                    Books.add(book);
                    System.out.println("Book "+"\""+title+"\""+"added successfuly.");
                    break;
                case 2:
                    int index=1;
                    System.out.println("==All Books==");
                    for(BookLap b:Books){
                        System.out.println((index++)+". Titel: "+b.getTitle()+", Author: "+b.getAuthor()+", Year: "+b.getYear()+", Avaliable: "+(b.getIsAvailable()? "Yes":"No"));
                    }
                    break;
                case 3:
                    System.out.print("Enter title to search:");
                    scan.nextLine();
                    String NameBook = scan.nextLine();
                    System.out.println("🔍 Search results:");
                    for(BookLap b:Books){
                        if(b.getTitle().toLowerCase().contains(NameBook.toLowerCase())){
                            System.out.println("- Title: "+b.getTitle()+", Author: "+b.getAuthor()+", Year: "+b.getYear()+", Available: "+(b.getIsAvailable()? "Yes":"No" ));
                        }
                    }
                    break;
                case 4:
                    boolean found=false; 
                    System.out.print("Enter book title to borrow:");
                    scan.nextLine();
                    String Namebook=scan.nextLine();
                    for(BookLap b:Books){
                        if(b.getTitle().toLowerCase().contains(Namebook.toLowerCase())){
                            System.out.println("You borrowed \""+b.getTitle()+"\"");
                            b.setIsAvailable(false);
                            found=true;
                        }
                    }
                    if(!found)System.out.println("Not Found Book");
                    break;
                case 5: 
                    boolean Found=false;
                     System.out.print("Enter book title to return: ");
                     scan.nextLine();
                    String NamebOok=scan.nextLine();
                    for(BookLap b:Books){
                        if(b.getTitle().toLowerCase().contains(NamebOok.toLowerCase())){
                            System.out.println("You retruned \""+b.getTitle()+"\"");
                            b.setIsAvailable(true);
                            Found=true;
                        }
                    }
                   if(!Found) System.out.println("Not Found Book");
                    break;
                case 6:
                    System.out.println("Enter book title to delete: ");
                    scan.nextLine();
                    String namebook=scan.nextLine();
                    Iterator<BookLap> it = Books.iterator();
                    boolean fOund=false;
                    while (it.hasNext()) {
                             BookLap b = it.next();
                         if (b.getTitle().toLowerCase().contains(namebook.toLowerCase())) {
                                System.out.println("Book \"" + b.getTitle() + "\" deleted successfully.");
                                it.remove();
                                fOund=true;
                          }
                    }
                    if(!fOund)System.out.println("Not found Book");
                    break;
                case 7:
                    System.out.println("📚 Exiting program. Goodbye!");
                    n=1;
                    break;
                default:
                    System.out.println("you shoud enter bittwin 1 and 7");
                break;
            }
            }
            scan.close();
            System.out.println("***********");
            try(BufferedWriter Book_Library = new BufferedWriter(new FileWriter("Book_Library.txt"))){
                for(BookLap b:Books){
                    Book_Library.write(b.getTitle()+","+b.getAuthor()+","+b.getYear()+","+b.getIsAvailable()+"\n");
                }
                Book_Library.close();
            }
            catch(IOException e){
                System.out.println("Error: "+e.getMessage());
            }
    }
    
}
