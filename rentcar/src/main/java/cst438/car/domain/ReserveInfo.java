package cst438.car.domain;

/**
 * This is a helper class to 
 * @author Ricardo and Max
 *
 */
public class ReserveInfo {
    
    private long reservation_id;
    private float total_price;
    
    public ReserveInfo() {
        
    }
    

    public ReserveInfo(long reservation_id, float total_price) {
        super();
        this.reservation_id = reservation_id;
        this.total_price = total_price;
    }
    
    
    
    public long getReservation_id() {
        return reservation_id;
    }


    public void setReservation_id(long reservation_id) {
        this.reservation_id = reservation_id;
    }


    public float getTotal_price() {
        return total_price;
    }


    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }


    @Override
    public String toString() {
        return "ReserveInfo [reservation_id=" + reservation_id + ", total_price=" + total_price
                + "]";
    }

    
}
