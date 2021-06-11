package cst438.car.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "user")
public class User {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long userid;
    
    @NotNull
    private String emailaddress = "";
    
    @NotNull
    private String name = "";
    
    @NotNull
    private String password = "";
    
    @NotNull
    private int creditnumber =0;
    
    @NotNull
    private String address = "";
    
    public User() {
    }

    public User(long userid, String emailaddress, String name, String password, int creditnumber,
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

    public int getCreditnumber() {
        return creditnumber;
    }

    public void setCreditnumber(int creditnumber) {
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
