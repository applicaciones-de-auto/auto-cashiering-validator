/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.cashiering;

import org.guanzon.appdriver.base.GRider;
import org.guanzon.auto.model.cashiering.Model_SalesInvoice_Master;

/**
 *
 * @author Arsiela
 */
public class Validator_SalesInvoice_Master implements ValidatorInterface {

    GRider poGRider;
    String psMessage;
    
    Model_SalesInvoice_Master poEntity;
    
    public Validator_SalesInvoice_Master(Object foValue){
        poEntity = (Model_SalesInvoice_Master) foValue;
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
        
        if(poEntity.getReferNo()== null) {
            psMessage = "Reference No is not set.";
            return false;
        } else {
            if (poEntity.getReferNo().trim().isEmpty()){
                psMessage = "Reference No is not set.";
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
