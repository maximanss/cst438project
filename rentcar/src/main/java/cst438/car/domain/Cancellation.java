package cst438.car.domain;

/**
 * It is a helper class to cancel a reservation
 */

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class Cancellation {
    
    @NotNull
    @Size(min = 1, max = 50)
    private String emailAddress;
    
    @NotNull
    @Min(1)
    @Max(2147483647)
    private Long reserveNum;
    
    public Cancellation() {
        
    }

    public Cancellation(@NotNull String emailAddress,
            @NotNull @Min(1) @Max(2147483647) Long reserveNum) {
        super();
        this.emailAddress = emailAddress;
        this.reserveNum = reserveNum;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Long getReserveNum() {
        return reserveNum;
    }

    public void setReserveNum(Long reserveNum) {
        this.reserveNum = reserveNum;
    }

    @Override
    public String toString() {
        return "Cancellation [emailAddress=" + emailAddress + ", reserveNum=" + reserveNum + "]";
    }
    
    

}
