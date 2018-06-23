package cinema_project.ui.model.users;

public class Ticket {
    private int row;
    private int col;
    private int hall;
    private String phoneNumber;
    private String firstCahier;
    private String lastCahier;
    private String nameMovies;
    private String date;
    private String time;
    private String price;
    private String firstName;
    private String lastName;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getHall() {
        return hall;
    }

    public void setHall(int hall) {
        this.hall = hall;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstCahier() {
        return firstCahier;
    }

    public void setFirstCahier(String firstCahier) {
        this.firstCahier = firstCahier;
    }

    public String getLastCahier() {
        return lastCahier;
    }

    public void setLastCahier(String lastCahier) {
        this.lastCahier = lastCahier;
    }

    public String getNameMovies() {
        return nameMovies;
    }

    public void setNameMovies(String nameMovies) {
        this.nameMovies = nameMovies;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Ticket(String name, String d, String t, int r, int c, String first, String last, int h, String pn, String firstNameCashier, String lastNameCashier, String pr){
        nameMovies = name;
        date = d;
        time = t;
        row = r;
        col = c;
        price = pr;
        phoneNumber = pn;
        hall = h;
        firstCahier = firstNameCashier;
        lastCahier = lastNameCashier;
        firstName = first;
        lastName = last;
    }

    @Override
    public String toString() {
        return "\tTicket at the film: " + nameMovies +
                "\n\n\t Row: " + row +
                "\n\t Seat: " + col +
                "\n\t Hall: " + hall +
                "\n\n\t Bought by:" +
                "\n\t First name: " + firstName +
                "\n\t Last name: " + lastName +
                "\n\t phone number: " + phoneNumber +
                "\n\n\t Sell by cashier:" +
                "\n\t First name: " + firstCahier +
                "\n\t Last name: " + lastCahier +
                "\n\n\t Price: " + price;
    }

}
