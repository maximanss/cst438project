package cst438.car.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "user")
public class User {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long userid;
    
    @NotNull
    @Size(min=1, max=50)
    private String emailaddress = "";
    
    @NotNull
    @Size(min=1, max=50)
    private String name = "";
    
    @NotNull
    @Size(min=3, max=30)
    private String password = "";
    
    @NotNull
    @Size(min=1, max=30)
    private String creditnumber;
    
    @NotNull
    @Size(min=1, max=80)
    private String address = "";
    
    public User() {
    }

    public User(long userid, String emailaddress, String name, String password, String creditnumber,
            String address) {
        super();
        this.userid = userid;
        this.emailaddress = emailaddress;
        this.name = name;
        this.password = password;
        this.creditnumber = creditnumber;
        this.address = address;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreditnumber() {
        return creditnumber;
    }

    public void setCreditnumber(String creditnumber) {
        this.creditnumber = creditnumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User [userid=" + userid + ", emailaddress=" + emailaddress + ", name=" + name
                + ", password=" + password + ", creditnumber=" + creditnumber + ", address="
                + address + "]";
    }

        
}
