/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.cashiering;

/**
 *
 * @author Arsiela
 */
public class ValidatorFactory {
    
    public enum TYPE{
          VehicleSalesInvoice_Master
        , VehicleSalesInvoice_Source
    }
    
    public static ValidatorInterface make(ValidatorFactory.TYPE foType, Object foValue){
        switch (foType) {
            case VehicleSalesInvoice_Master:
                return new Validator_VehicleSalesInvoice_Master(foValue);
            default:
                return null;
        }
    }
    
}
