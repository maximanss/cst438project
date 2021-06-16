package cst438.car.domain;

/**
 * This is a helper class to 
 * @author Ricardo and Max
 *
 */
public class ReserveInfo {
    
    private long reservation_id;
    private String car_type;
    private String description;
    private float total_price;
    
    public ReserveInfo() {
        
    }

    public ReserveInfo(long reservation_id, String car_type, String description,
            float total_price) {
        super();
        this.reservation_id = reservation_id;
        this.car_type = car_type;
        this.description = description;
        this.total_price = total_price;
    }

    public long getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(long reservation_id) {
        this.reservation_id = reservation_id;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    @Override
    public String toString() {
        return "ReserveInfo [reservation_id=" + reservation_id + ", car_type=" + car_type
                + ", description=" + description + ", total_price=" + total_price + "]";
    }
    

    
    
}
