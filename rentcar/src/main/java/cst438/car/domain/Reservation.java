package cst438.car.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reservation")
public class Reservation {
    
    @Id
    @GeneratedValue
    private long reserveid;
    
    private long carid;
    private LocalDate startdate;
    private LocalDate enddate;   
    private long userid;
    private long companyid;
    private float totalprice;
    
    public Reservation() {
    }

    public Reservation(long reserveid, long carid, LocalDate startdate, LocalDate enddate,
            long userid, long companyid, float totalprice) {
        super();
        this.reserveid = reserveid;
        this.carid = carid;
        this.startdate = startdate;
        this.enddate = enddate;
        this.userid = userid;
        this.companyid = companyid;
        this.totalprice = totalprice;
    }

    public long getReserveid() {
        return reserveid;
    }

    public void setReserveid(long reserveid) {
        this.reserveid = reserveid;
    }

    public long getCarid() {
        return carid;
    }

    public void setCarid(long carid) {
        this.carid = carid;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public LocalDate getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDate enddate) {
        this.enddate = enddate;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public long getCompanyid() {
        return companyid;
    }

    public void setCompanyid(long companyid) {
        this.companyid = companyid;
    }

    public float getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(float totalprice) {
        this.totalprice = totalprice;
    }

    @Override
    public String toString() {
        return "Reservation [reserveid=" + reserveid + ", carid=" + carid + ", startdate="
                + startdate + ", enddate=" + enddate + ", userid=" + userid + ", companyid="
                + companyid + ", totalprice=" + totalprice + "]";
    }

    
}
