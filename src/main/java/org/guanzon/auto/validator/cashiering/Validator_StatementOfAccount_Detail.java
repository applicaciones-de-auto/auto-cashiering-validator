/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.cashiering;

import org.guanzon.appdriver.base.GRider;
import org.guanzon.auto.model.cashiering.Model_StatementOfAccount_Detail;

/**
 *
 * @author Arsiela
 */
public class Validator_StatementOfAccount_Detail implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_StatementOfAccount_Detail poEntity;
    
    public Validator_StatementOfAccount_Detail(Object foValue){
        poEntity = (Model_StatementOfAccount_Detail) foValue;
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
        
        if(poEntity.getTranType()== null) {
            psMessage = "Transaction Type is not set.";
            return false;
        } else {
            if (poEntity.getTranType().trim().isEmpty()){
                psMessage = "Transaction Type is not set.";
                return false;
            }
        }
        
        return true;
    }

    @Override
    public String getMessage() {
        return psMessage;
    }
    
}
