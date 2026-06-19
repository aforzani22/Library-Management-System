// This is the patron class, which will store the Patron ID, Patron name, Patron address and the Fine Amount.

public class Patron {
    private String id;
    private String name;
    private String address;
    private double fineAmnt;

    // Patron Creation
    public Patron(String id, String name, String address, double fineAmnt){
        this.id = id;
        this.name = name;
        this.address = address;
        this.fineAmnt = fineAmnt;
    }

    //Getters
    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public double getFineAmnt(){
        return fineAmnt;
    }

    // How it will display on the menu
    @Override
    public String toString() {
        return "ID: " + id + "\n"
                + "Name: " + name + "\n"
                + "Address: " + address + "\n"
                + String.format("Fine: $%.2f", fineAmnt);
    }
}
