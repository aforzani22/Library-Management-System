// This is the main Class, which will be the main display of the LMS.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        LMS library = new LMS();

        boolean showMenu = true;

        while (showMenu) {
            displayMenu();
            String choice = input.nextLine();

            if (choice.equals("1")) {
                loadPatronsFromFile(input, library);
            } else if (choice.equals("2")) {
                addPatronManually(input, library);
            } else if (choice.equals("3")) {
                removePatronById(input, library);
            } else if (choice.equals("4")) {
                library.displayPatrons();
            } else if (choice.equals("5")) {
                showMenu = false;
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid choice, choose an option between 1-5");
            }

            System.out.println();
        }

        input.close();
    }

    // Menu being displayed
    public static void displayMenu() {
        System.out.println("=================================");
        System.out.println(" Library Management System");
        System.out.println("=================================");
        System.out.println("1. Add patrons from a text file");
        System.out.println("2. Add a patron manually");
        System.out.println("3. Remove a patron by ID");
        System.out.println("4. Display all patrons");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    // Adding Patrons through files
    public static void loadPatronsFromFile(Scanner input, LMS library) {
        System.out.print("Enter the text file name: ");
        String fileName = input.nextLine();

        String result = library.loadFromFile(fileName);
        System.out.println(result);
    }

    // Manually adding Patrons
    public static void addPatronManually(Scanner input, LMS library) {
        System.out.print("Enter seven digit ID: ");
        String id = input.nextLine();

        System.out.print("Enter name: ");
        String name = input.nextLine();

        System.out.print("Enter address: ");
        String address = input.nextLine();

        System.out.print("Enter fine amount: ");
        String fineText = input.nextLine();

        double fineAmount;

        try {
            fineAmount = Double.parseDouble(fineText);
        } catch (NumberFormatException e) {
            System.out.println("Invalid, please enter a number for the fine.");
            return;
        }

        Patron patron = new Patron(id, name, address, fineAmount);
        String result = library.addPatron(patron);

        System.out.println(result);
    }

    // Removing patrons
    public static void removePatronById(Scanner input, LMS library) {
        System.out.print("Enter patron ID to remove: ");
        String id = input.nextLine();

        if (library.removePatron(id)) {
            System.out.println("This patron has been removed");
        } else {
            System.out.println("This patron doesn't exist");
        }
    }
}
