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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((car_type == null) ? 0 : car_type.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + (int) (reservation_id ^ (reservation_id >>> 32));
        result = prime * result + Float.floatToIntBits(total_price);
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
        ReserveInfo other = (ReserveInfo) obj;
        if (car_type == null) {
            if (other.car_type != null)
                return false;
        } else if (!car_type.equals(other.car_type))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (reservation_id != other.reservation_id)
            return false;
        if (Float.floatToIntBits(total_price) != Float.floatToIntBits(other.total_price))
            return false;
        return true;
    }
    

    
    
}
