/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.cashiering;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.TransactionStatus;
import org.guanzon.auto.model.cashiering.Model_VehicleSalesInvoice;

/**
 *
 * @author Arsiela
 */
public class Validator_VehicleSalesInvoice_Source implements ValidatorInterface {

    GRider poGRider;
    String psMessage;
    
    Model_VehicleSalesInvoice poEntity;
    
    public Validator_VehicleSalesInvoice_Source(Object foValue){
        poEntity = (Model_VehicleSalesInvoice) foValue;
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
        
        
        try {
            //check if linked with VSI
            String lsID = "";
            String lsDesc = "";
//            String lsType = "";
            String lsSQL =   " SELECT "                                             
                    + "   a.sTransNox "                                     
                    + " , a.sReferNox "                                     
                    + " , a.sSourceCD "                                     
                    + " , a.sSourceNo "                                     
                    + " , a.sTranType "                                     
                    + " , b.sReferNox AS sSINoxxxx "                        
                    + " , b.dTransact "                      
                    + " , b.cTranStat  "                                  
                    + " FROM si_master_source a "                           
                    + " LEFT JOIN si_master b ON b.sTransNox = a.sTransNox ";
            lsSQL = MiscUtil.addCondition(lsSQL, " b.cTranStat <> " + SQLUtil.toSQL(TransactionStatus.STATE_CANCELLED) 
                                                + " AND a.sReferNox = " + SQLUtil.toSQL(poEntity.getReferNo()) 
                                                + " AND b.sTransNox <> " + SQLUtil.toSQL(poEntity.getTransNo()) 
                                                );
            System.out.println("EXISTING VSI CHECK: " + lsSQL);
            ResultSet loRS = poGRider.executeQuery(lsSQL);

            if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                        lsID = loRS.getString("sSINoxxxx");
//                        lsType = loRS.getString("sTranType"); 
                        lsDesc = xsDateShort(loRS.getDate("dTransact"));
                    }

                    MiscUtil.close(loRS);

                    psMessage = "Found an existing vehicle sales invoice."
                            + "\n\n<Invoice No:" + lsID + ">"
                            + "\n<Invoice Date:" + lsDesc + ">"
//                            + "\n<Invoice Type:" + lsType + ">"
                            + "\n\nSaving aborted.";
                    return false;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Validator_VehicleSalesInvoice_Source.class.getName()).log(Level.SEVERE, null, ex);
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
    
}
