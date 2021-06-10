package cst438.car.domain;

import java.sql.Date;

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
    private Date startdate;
    private Date enddate;   
    private long userid;
    private long companyid;
    private float totalprice;
    private String location;
    
    public Reservation() {
    }

    public Reservation(long userid) {
        this.userid = userid;
    }
    
    public Reservation(long reserveid, long carid, Date startdate, Date enddate, long userid,
            long companyid, float totalprice, String location) {
        super();
        this.reserveid = reserveid;
        this.carid = carid;
        this.startdate = startdate;
        this.enddate = enddate;
        this.userid = userid;
        this.companyid = companyid;
        this.totalprice = totalprice;
        this.location = location;
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

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Reservation [reserveid=" + reserveid + ", carid=" + carid + ", startdate="
                + startdate + ", enddate=" + enddate + ", userid=" + userid + ", companyid="
                + companyid + ", totalprice=" + totalprice + ", location=" + location + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (carid ^ (carid >>> 32));
        result = prime * result + (int) (companyid ^ (companyid >>> 32));
        result = prime * result + ((enddate == null) ? 0 : enddate.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + (int) (reserveid ^ (reserveid >>> 32));
        result = prime * result + ((startdate == null) ? 0 : startdate.hashCode());
        result = prime * result + Float.floatToIntBits(totalprice);
        result = prime * result + (int) (userid ^ (userid >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Reservation other = (Reservation) obj;
        if (carid != other.carid)
            return false;
        if (companyid != other.companyid)
            return false;
        if (enddate == null) {
            if (other.enddate != null)
                return false;
        } else if (!enddate.equals(other.enddate))
            return false;
        if (location == null) {
            if (other.location != null)
                return false;
        } else if (!location.equals(other.location))
            return false;
        if (reserveid != other.reserveid)
            return false;
        if (startdate == null) {
            if (other.startdate != null)
                return false;
        } else if (!startdate.equals(other.startdate))
            return false;
        if (Float.floatToIntBits(totalprice) != Float.floatToIntBits(other.totalprice))
            return false;
        if (userid != other.userid)
            return false;
        return true;
    }

    
}
