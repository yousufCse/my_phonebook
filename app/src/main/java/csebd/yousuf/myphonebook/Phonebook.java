package csebd.yousuf.myphonebook;

/**
 * Created by Yousuf on 3/9/2016.
 */
public class Phonebook {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String imagePath;

    public Phonebook(int id, String name, String phone, String email, String address, String imagePath) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.imagePath = imagePath;
    }

    public Phonebook(String name, String phone, String email, String address, String imagePath) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.imagePath = imagePath;
    }

    public Phonebook() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Phonebook{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
