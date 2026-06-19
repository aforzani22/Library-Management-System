// This is the Library Management System class, this is where all of the heavy lifting of the program will be doing.
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LMS {
    private ArrayList<Patron> patrons;

    public LMS() {
        patrons = new ArrayList<Patron>();
    }

    // Here a patron is added
    public String addPatron(Patron patron) {
        String error = validatePatron(patron);

        if (error != null) {
            return error;
        }

        patrons.add(patron);
        return "The patron is added successfully!";
    }

    // Here a patron is removed
    public boolean removePatron(String id) {
        for (int i = 0; i < patrons.size(); i++) {
            if (patrons.get(i).getId().equals(id)) {
                patrons.remove(i);
                return true;
            }
        }

        return false;
    }

    // Here it displays every patrons there is in the LMS
    public void displayPatrons() {
        if (patrons.size() == 0) {
            System.out.println("There are no patrons in the system.");
            return;
        }

        for (int i = 0; i < patrons.size(); i++) {
            System.out.println("---------------------------------");
            System.out.println(patrons.get(i));
        }

        System.out.println("---------------------------------");
        System.out.println("All patrons displayed");
    }

    // Here is a check if the patron's ID is real or not
    public boolean idExists(String id) {
        for (Patron patron : patrons) {
            if (patron.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    // This will read the text file with the patrons info
    public String loadFromFile(String fileName) {
        int loaded = 0;
        int skipped = 0;

        try {
            File file = new File(fileName);
            Scanner fileReader = new Scanner(file);

            int lineNumber = 1;

            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine().trim();

                if (line.length() == 0) {
                    lineNumber++;
                    continue;
                }

                Patron patron = makePatronFromLine(line);

                if (patron == null) {
                    System.out.println("Skipped line " + lineNumber + ": Invalid data.");
                    skipped++;
                } else {
                    String result = addPatron(patron);

                    if (result.equals("The patron is added successfully!")) {
                        loaded++;
                    } else {
                        System.out.println("Skipped line " + lineNumber + ": " + result);
                        skipped++;
                    }
                }

                lineNumber++;
            }

            fileReader.close();

        } catch (FileNotFoundException e) {
            return "File not found. Please check the file name and try again.";
        }

        if (loaded > 0 && skipped == 0) {
            return "All patrons loaded successfully!";
        } else if (loaded > 0) {
            return "Loaded " + loaded + " patron(s). Skipped " + skipped + " invalid line(s).";
        } else {
            return "Invalid data, no patrons loaded.";
        }
    }

    // Here is where the separation of the patron's data is used by dashes
    private Patron makePatronFromLine(String line) {
        int firstDash = line.indexOf("-");
        int secondDash = line.indexOf("-", firstDash + 1);
        int lastDash = line.lastIndexOf("-");

        if (firstDash == -1 || secondDash == -1 || lastDash == -1 || secondDash == lastDash) {
            return null;
        }

        String id = line.substring(0, firstDash).trim();
        String name = line.substring(firstDash + 1, secondDash).trim();
        String address = line.substring(secondDash + 1, lastDash).trim();
        String fineText = line.substring(lastDash + 1).trim();

        double fineAmnt;

        try {
            fineAmnt = Double.parseDouble(fineText);
        } catch (NumberFormatException e) {
            return null;
        }

        return new Patron(id, name, address, fineAmnt);
    }

    // The Error messages for the wrong outputs
    public String validatePatron(Patron patron) {
        if (patron.getId() == null || !patron.getId().matches("\\d{7}")) {
            return "Invalid, there should be seven digits for the ID";
        }

        if (idExists(patron.getId())) {
            return "This ID already exists";
        }

        if (patron.getName() == null || patron.getName().trim().length() == 0) {
            return "The name cannot be blank";
        }

        if (patron.getAddress() == null || patron.getAddress().trim().length() == 0) {
            return "The address cannot be blank";
        }

        if (patron.getFineAmnt() < 0) {
            return "The fine cannot be less than 0";
        }

        if (patron.getFineAmnt() > 250) {
            return "The fine is greater than 250";
        }

        return null;
    }
}
