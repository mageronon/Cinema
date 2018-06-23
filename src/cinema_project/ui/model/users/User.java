package cinema_project.ui.model.users;

public class User {

    private String email;
    private String first_name;
    private String last_name;
    private String phone_number;
    private int bought_tickets_number;
    private String password;
    private boolean admin;
    private boolean _isCash = false;

    public User(String em, String first, String last, String phone, int bought, String pass, boolean adm, boolean iscash){
        email = em;
        first_name = first;
        last_name = last;
        phone_number = phone;
        bought_tickets_number = bought;
        password = pass;
        admin = adm;
        _isCash = iscash;
    }

    public User getUser(){
        return this;
    }

    public boolean is_isCash() {
        return _isCash;
    }

    public void set_isCash(boolean _isCash) {
        this._isCash = _isCash;
    }

    public boolean isAdmin() {
        try {
            return admin;
        }catch (NullPointerException e){
            return false;
        }
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public int getBought_tickets_number() {

        return bought_tickets_number;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setBought_tickets_number(int bought_tickets_number) {
        this.bought_tickets_number = bought_tickets_number;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString(){
        return "e-mail:" + email + " |password:" + password
                + " |last name:" + last_name + " |first name:" + first_name
                + " |phone number:" + phone_number + " |admin:" + admin +" |bought tickets number:" + bought_tickets_number;
    }
}

