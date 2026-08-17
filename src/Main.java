import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Library library = new Library(100, 100);

        // -------------------------
        // SAMPLE ITEMS
        // -------------------------

        library.registerItem(
                new Book(
                        "B001",
                        "The Hobbit",
                        "J.R.R. Tolkien",
                        310
                )
        );

        library.registerItem(
                new Book(
                        "B002",
                        "1984",
                        "George Orwell",
                        328
                )
        );

        library.registerItem(
                new Magazine(
                        "M001",
                        "National Geographic",
                        120
                )
        );

        library.registerItem(
                new DVD(
                        "D001",
                        "Interstellar",
                        169
                )
        );

        // -------------------------
        // SAMPLE MEMBERS
        // -------------------------

        library.registerMember(
                new Member("Ahmed", "MEM001")
        );

        library.registerMember(
                new Member("Sara", "MEM002")
        );

        int choice;

        do {

            printMenu();

            System.out.print("Choose an option: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    library.displayCatalogue();
                    break;

                case 2:
                    registerMember(scanner, library);
                    break;

                case 3:
                    borrowItem(scanner, library);
                    break;

                case 4:
                    returnItem(scanner, library);
                    break;

                case 5:
                    renewItem(scanner, library);
                    break;

                case 6:
                    searchItem(scanner, library);
                    break;

                case 7:
                    viewByStatus(scanner, library);
                    break;

                case 8:
                    library.displayMembers();
                    break;

                case 9:
                    library.displayReport();
                    break;

                case 0:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

            System.out.println();

        } while (choice != 0);

        scanner.close();
    }

    public static void printMenu() {

        System.out.println("=================================");
        System.out.println("   BAYT AL HEKMA LIBRARY");
        System.out.println("=================================");

        System.out.println("1 - View catalogue");
        System.out.println("2 - Register member");
        System.out.println("3 - Borrow item");
        System.out.println("4 - Return item");
        System.out.println("5 - Renew loan");
        System.out.println("6 - Search item by ID");
        System.out.println("7 - View items by status");
        System.out.println("8 - View all members");
        System.out.println("9 - Library report");
        System.out.println("0 - Exit");
    }

    private static void registerMember(
            Scanner scanner,
            Library library) {

        System.out.print("Member name: ");
        String name = scanner.nextLine();

        System.out.print("Membership ID: ");
        String id = scanner.nextLine();

        Member member = new Member(name, id);

        if (library.registerMember(member)) {
            System.out.println(
                    "Member registered successfully."
            );
        }
    }

    private static void borrowItem(
            Scanner scanner,
            Library library) {

        System.out.print("Item ID: ");
        String itemId = scanner.nextLine();

        System.out.print("Membership ID: ");
        String memberId = scanner.nextLine();

        library.lendItem(itemId, memberId);
    }

    private static void returnItem(
            Scanner scanner,
            Library library) {

        System.out.print("Item ID: ");
        String itemId = scanner.nextLine();

        System.out.print("Membership ID: ");
        String memberId = scanner.nextLine();

        library.returnItem(itemId, memberId);
    }

    private static void renewItem(
            Scanner scanner,
            Library library) {

        System.out.print("Item ID: ");
        String itemId = scanner.nextLine();

        library.renewItem(itemId);
    }

    private static void searchItem(
            Scanner scanner,
            Library library) {

        System.out.print("Item ID: ");
        String id = scanner.nextLine();

        LibraryItem item = library.findItem(id);

        if (item == null) {
            System.out.println("Item not found.");
        } else {
            item.display();
        }
    }

    private static void viewByStatus(
            Scanner scanner,
            Library library) {

        System.out.println("1 - AVAILABLE");
        System.out.println("2 - ON_LOAN");
        System.out.println("3 - RESERVED");
        System.out.println("4 - LOST");

        System.out.print("Status: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        ItemStatus status;

        switch (choice) {

            case 1:
                status = ItemStatus.AVAILABLE;
                break;

            case 2:
                status = ItemStatus.ON_LOAN;
                break;

            case 3:
                status = ItemStatus.RESERVED;
                break;

            case 4:
                status = ItemStatus.LOST;
                break;

            default:
                System.out.println("Invalid status.");
                return;
        }

        library.displayItemsByStatus(status);
    }
}