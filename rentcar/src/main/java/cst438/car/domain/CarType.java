package cst438.car.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cartype")
public class CarType {

    @Id
    private int cartypeid;
    
    @NotNull
    private String cartypename = "";


    public CarType() {
    }


    public CarType(int cartypeid, String cartypename) {
        super();
        this.cartypeid = cartypeid;
        this.cartypename = cartypename;
    }


    public int getCartypeid() {
        return cartypeid;
    }


    public void setCartypeid(int cartypeid) {
        this.cartypeid = cartypeid;
    }


    public String getCartypename() {
        return cartypename;
    }


    public void setCartypename(String cartypename) {
        this.cartypename = cartypename;
    }


    @Override
    public String toString() {
        return "CarType [cartypeid=" + cartypeid + ", cartypename=" + cartypename + "]";
    }


    
}
