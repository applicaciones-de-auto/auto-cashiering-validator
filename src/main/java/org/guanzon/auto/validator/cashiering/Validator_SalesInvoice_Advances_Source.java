/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.cashiering;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.TransactionStatus;
import org.guanzon.auto.model.cashiering.Model_SalesInvoice_Advances_Source;

/**
 *
 * @author Arsiela
 */
public class Validator_SalesInvoice_Advances_Source implements ValidatorInterface {

    GRider poGRider;
    String psMessage;
    
    Model_SalesInvoice_Advances_Source poEntity;
    
    public Validator_SalesInvoice_Advances_Source(Object foValue){
        poEntity = (Model_SalesInvoice_Advances_Source) foValue;
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
            psMessage = "Source No is not set.";
            return false;
        } else {
            if (poEntity.getSourceNo().trim().isEmpty()){
                psMessage = "Source No is not set.";
                return false;
            }
        }
        
        if(poEntity.getTranType()== null) {
            psMessage = "Transtype is not set.";
            return false;
        } else {
            if (poEntity.getTranType().trim().isEmpty()){
                psMessage = "Transtype is not set.";
                return false;
            }
        }
        
        if(poEntity.getAdvAmt().compareTo(new BigDecimal("0.00")) <= 0){
            psMessage = "Invalid advances amount.";
            return false;
        }
        
        
        if(poEntity.getEntryNo() == null){
            psMessage = "Invalid SI row to be deduct.";
            return false;
        } else {
            if(poEntity.getEntryNo() <= 0){
                psMessage = "Invalid SI row to be deduct.";
                return false;
            }
        }
//        
//        try {
//            String lsID = "";
//            String lsSQL = poEntity.getSQL();
//            //Validate exisiting VSI Number
//            if(poEntity.getDocType().equals("0")){ //Vehicle Sales Invoice
//                lsSQL = MiscUtil.addCondition(lsSQL, " a.sReferNox = " + SQLUtil.toSQL(poEntity.getReferNo())
//                                                        +" AND a.sTransNox <> " + SQLUtil.toSQL(poEntity.getTransNo()));
//                                                       // +" AND a.cTranStat <> " + SQLUtil.toSQL(TransactionStatus.STATE_CANCELLED) ;
//                System.out.println("EXISTING VSI NO CHECK: " + lsSQL);
//                ResultSet loRS = poGRider.executeQuery(lsSQL);
//
//                if (MiscUtil.RecordCount(loRS) > 0){
//                        while(loRS.next()){
//                            lsID = loRS.getString("sReferNox");
//                        }
//
//                        MiscUtil.close(loRS);
//                        psMessage = "Found existing VSI No.\n\nSaving aborted." ;
//                        return false;
//                }
//            
//            }
            
//            if(poEntity.getCancelld() != null){
//                if(poEntity.getCancelld().trim().isEmpty()){
//                    lsID = "";
//                    lsSQL = poEntity.getSQL();
//
//                    lsSQL = MiscUtil.addCondition(lsSQL, "  (a.sCancelld = '' OR a.sCancelld = NULL) "
//                                                            + " AND a.sBrBankID = " + SQLUtil.toSQL(poEntity.getBrBankID())
//                                                            + " AND a.cPayModex = " + SQLUtil.toSQL(poEntity.getPayMode())
//                                                            + " AND a.sSourceNo = " + SQLUtil.toSQL(poEntity.getSourceNo())
//                                                            +" AND a.sTransNox <> " + SQLUtil.toSQL(poEntity.getTransNo())) ;
//                    System.out.println("EXISTING BANK APPLICATION CHECK: " + lsSQL);
//                    loRS = poGRider.executeQuery(lsSQL);
//
//                    if (MiscUtil.RecordCount(loRS) > 0){
//                            while(loRS.next()){
//                                lsID = loRS.getString("sApplicNo");
//                            }
//
//                            MiscUtil.close(loRS);
//                            psMessage = "Existing Bank Application for "+poEntity.getBankName()+". Saving aborted." ;
//                            return false;
//                    }  
//                }
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Validator_SalesInvoice_Source.class.getName()).log(Level.SEVERE, null, ex);
//        } 
        return true;
    }

    @Override
    public String getMessage() {
        return psMessage;
    }
    
}
