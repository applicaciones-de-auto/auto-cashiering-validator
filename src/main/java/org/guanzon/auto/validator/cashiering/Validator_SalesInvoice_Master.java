/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.cashiering;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
        try {
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


            Date date = (Date) poEntity.getTransactDte();
            if (date == null) {
                psMessage = "Invalid Receipt Date.";
                return false;
            } else {
                if ("1900-01-01".equals(xsDateShort(date))) {
                    psMessage = "Invalid Receipt Date.";
                    return false;
                }
            }

            // Convert java.util.Date to java.time.LocalDate
            LocalDate ldReceiptDte = Instant.ofEpochMilli(date.getTime())
                                          .atZone(ZoneId.systemDefault())
                                          .toLocalDate();

            // Convert transaction date to LocalDate
            Date transactDate = poGRider.getServerDate();
            LocalDate ldTranDte = Instant.ofEpochMilli(transactDate.getTime())
                                         .atZone(ZoneId.systemDefault())
                                         .toLocalDate();

            if(!poEntity.getDocType().equals("0")){
                if (ldReceiptDte.isAfter(ldTranDte)) {
                    psMessage = "Receipt Date should not be greater than the date current date.";
                    return false;
                }

                // Check if receipt date is prior to transaction date
                if (ldReceiptDte.isBefore(ldTranDte)) {
                    psMessage = "Receipt Date cannot be prior to current date.";
                    return false;
                }
                
                String lsCnt = "";
                String lsSQL = " SELECT sValuexxx from xxxstandard_sets where sDescript = 'si_date_back_date_days' ";
                System.out.println("EXISTING ALLOWABLE TO BACK DATE CHECK: " + lsSQL);
                ResultSet loRS = poGRider.executeQuery(lsSQL);

                if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                        lsCnt = loRS.getString("sValuexxx");
                    }
                    MiscUtil.close(loRS);
                    
                    if(lsCnt != null){
                        if(!lsCnt.isEmpty()){
                            Period lnPeriod = Period.between(ldReceiptDte, strToDate(xsDateShort(poGRider.getServerDate())));
                            if(Integer.valueOf(lsCnt) < lnPeriod.getDays()){
                                psMessage =  "Your Receipt Date has exceeded the allowable days for backdate of " + lsCnt + " days only.";
                                return false;
                            }
                        }
                    }     
                }
                
                //VALIDATE EXISTING RECEIPT DATE
                
                
//                lsCnt = "12"; //set default into 1 year if no interval has been set
//                lsSQL = " SELECT sValuexxx from xxxstandard_sets where sDescript = 'si_no_repetition_month_interval' ";
//                System.out.println("EXISTING ALLOWABLE TO REPEAT RECEIPT NUMBER CHECK: " + lsSQL);
//                loRS = poGRider.executeQuery(lsSQL);
//
//                if (MiscUtil.RecordCount(loRS) > 0){
//                    while(loRS.next()){
//                        lsCnt = loRS.getString("sValuexxx");
//                    }
//                    MiscUtil.close(loRS);     
//                }
//                
//                Period lnPeriod = Period.between(ldReceiptDte, strToDate(xsDateShort(poGRider.getServerDate())));
//                if(Integer.valueOf(lsCnt) < lnPeriod.getMonths()){
//                    String lsReceiptType = "";
//                    switch(poEntity.getDocType()){
//                        case "1":
//                            
//                        break;
//                        case "2":
//                            
//                        break;
//                        case "3":
//                            
//                        break;
//                        case "4":
//                            
//                        break;
//                        case "5":
//                            
//                        break;
//                    }
//                    
//                    psMessage =  lsReceiptType +" No. " + poEntity.getReferNo() + " already exists!";
//                    return false;
//                }
                

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

            if(!poEntity.getDocType().equals("0")){
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
            }
            
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
    
    private static String xsDateShort(Date fdValue) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(fdValue);
        return date;
    }

    private static String xsDateShort(String fsValue) throws org.json.simple.parser.ParseException, java.text.ParseException {
        SimpleDateFormat fromUser = new SimpleDateFormat("MMMM dd, yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String lsResult = "";
        lsResult = myFormat.format(fromUser.parse(fsValue));
        return lsResult;
    }
    
    /*Convert Date to String*/
    private LocalDate strToDate(String val) {
        DateTimeFormatter date_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(val, date_formatter);
        return localDate;
    }
    
}
