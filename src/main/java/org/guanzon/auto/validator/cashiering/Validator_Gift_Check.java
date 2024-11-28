/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.cashiering;

import java.math.BigDecimal;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.auto.model.cashiering.Model_Gift_Check;

/**
 *
 * @author Arsiela
 */
public class Validator_Gift_Check implements ValidatorInterface {

    GRider poGRider;
    String psMessage;
    
    Model_Gift_Check poEntity;
    
    public Validator_Gift_Check(Object foValue){
        poEntity = (Model_Gift_Check) foValue;
    }

    @Override
    public void setGRider(GRider foValue) {
        poGRider = foValue;
    }

    @Override
    public boolean isEntryOkay() {
        if(poEntity.getTransNo()== null) {
            psMessage = "Transaction No is not set.";
            return false;
        } else {
            if (poEntity.getTransNo().isEmpty()){
                psMessage = "Transaction No is not set.";
                return false;
            }
        }
        
        if(poEntity.getSourceCD()== null) {
            psMessage = "Source Code is not set.";
            return false;
        } else {
            if (poEntity.getSourceCD().trim().isEmpty()){
                psMessage = "Source Code is not set.";
                return false;
            }
        }
        
        if(poEntity.getSourceNo()== null) {
            psMessage = "Transaction Source is not set.";
            return false;
        } else {
            if (poEntity.getSourceNo().trim().isEmpty()){
                psMessage = "Transaction Source is not set.";
                return false;
            }
        }
        
        if(poEntity.getGCertNo()== null) {
            psMessage = "GC Number is not set.";
            return false;
        } else {
            if (poEntity.getGCertNo().trim().isEmpty()){
                psMessage = "GC Number is not set.";
                return false;
            }
        }
        
        if(poEntity.getAmount().compareTo(new BigDecimal("0.00")) <= 0) {
            psMessage = "Amount is not set.";
            return false;
        } 
        return true;
    }

    @Override
    public String getMessage() {
        return psMessage;
    }
    
}
