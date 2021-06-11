package cst438.car.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "partner")
public class Partner {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long companyid;
    
    @NotNull
    private String name = "";
    
    @NotNull
    private float discountrate = (float)1.0;
    
    public Partner() {
    }

    public Partner(long companyid, String name, float discountrate) {
        super();
        this.companyid = companyid;
        this.name = name;
        this.discountrate = discountrate;
    }

    public long getCompanyid() {
        return companyid;
    }

    public void setCompanyid(long companyid) {
        this.companyid = companyid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDiscountrate() {
        return discountrate;
    }

    public void setDiscountrate(float discountrate) {
        this.discountrate = discountrate;
    }

    @Override
    public String toString() {
        return "Partner [companyid=" + companyid + ", name=" + name + ", discountrate="
                + discountrate + "]";
    }

    
}
