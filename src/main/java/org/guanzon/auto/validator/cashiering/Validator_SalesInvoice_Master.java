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
            psMessage = "Receipt No is not set.";
            return false;
        } else {
            if (poEntity.getReferNo().trim().isEmpty()){
                psMessage = "Receipt No is not set.";
                return false;
            }
        }
        
        if(poEntity.getBranchCd()== null) {
            psMessage = "Branch code is not set.";
            return false;
        } else {
            if (poEntity.getBranchCd().trim().isEmpty()){
                psMessage = "Branch code is not set.";
                return false;
            }
        }
        
        if(poEntity.getDocType()== null) {
            psMessage = "Document type is not set.";
            return false;
        } else {
            if (poEntity.getDocType().trim().isEmpty()){
                psMessage = "Document type is not set.";
                return false;
            }
        }
        
        if(poEntity.getClientID()== null) {
            psMessage = "Payer is not set.";
            return false;
        } else {
            if (poEntity.getClientID().trim().isEmpty()){
                psMessage = "Payer is not set.";
                return false;
            }
        }
        
        if(poEntity.getTranTotl().compareTo(new BigDecimal("0.00")) <= 0){
            psMessage = "Invalid Transaction Total.";
            return false;
        }
        
        if(poEntity.getNetTotal().compareTo(new BigDecimal("0.00")) <= 0){
            psMessage = "Invalid Net Total.";
            return false;
        }
        
        if(poEntity.getCashAmt().compareTo(new BigDecimal("0.00")) <= 0
            && poEntity.getChckAmt().compareTo(new BigDecimal("0.00")) <= 0
            && poEntity.getCardAmt().compareTo(new BigDecimal("0.00")) <= 0
            && poEntity.getOthrAmt().compareTo(new BigDecimal("0.00")) <= 0
            && poEntity.getGiftAmt().compareTo(new BigDecimal("0.00")) <= 0){
            psMessage = "Invalid Payer Mode Amount.";
            return false;
        }
        
        if(poEntity.getAmtPaid().compareTo(new BigDecimal("0.00")) <= 0){
            psMessage = "Invalid Amount Paid.";
            return false;
        }
        
        try {
            String lsID = "";
            String lsSQL = poEntity.getSQL();
            //Validate exisiting VSI Number
            if(poEntity.getDocType().equals("0")){ //Vehicle Sales Invoice
                lsSQL = MiscUtil.addCondition(lsSQL, " a.sReferNox = " + SQLUtil.toSQL(poEntity.getReferNo())
                                                        +" AND a.sTransNox <> " + SQLUtil.toSQL(poEntity.getTransNo()));
                                                       // +" AND a.cTranStat <> " + SQLUtil.toSQL(TransactionStatus.STATE_CANCELLED) ;
                System.out.println("EXISTING VSI NO CHECK: " + lsSQL);
                ResultSet loRS = poGRider.executeQuery(lsSQL);

                if (MiscUtil.RecordCount(loRS) > 0){
                        while(loRS.next()){
                            lsID = loRS.getString("sReferNox");
                        }

                        MiscUtil.close(loRS);
                        psMessage = "Found existing VSI No.\n\nSaving aborted." ;
                        return false;
                }
            
            }
            
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
        } catch (SQLException ex) {
            Logger.getLogger(Validator_SalesInvoice_Master.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return true;
    }

    @Override
    public String getMessage() {
        return psMessage;
    }
    
}
