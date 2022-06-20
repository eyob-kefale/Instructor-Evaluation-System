// import javafx.beans.property.SimpleStringProperty;

public class Students {
    String firstName,
            lastName,
            password,
            id,
            sex,
            year;

    public Students(String firstName, String lastName, String password, String id, String sex, String year) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.id = id;
        this.sex = sex;
        this.year = year;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
