/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.cashiering;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.TransactionStatus;
import org.guanzon.auto.model.cashiering.Model_Cashier_Receivables_Detail;

/**
 *
 * @author Arsiela
 */
public class Validator_CashierReceivables_Detail implements ValidatorInterface {

    GRider poGRider;
    String psMessage;
    
    Model_Cashier_Receivables_Detail poEntity;
    
    public Validator_CashierReceivables_Detail(Object foValue){
        poEntity = (Model_Cashier_Receivables_Detail) foValue;
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
