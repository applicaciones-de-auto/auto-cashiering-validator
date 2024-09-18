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
          SalesInvoice_Master
        , SalesInvoice_Source
        , VehicleSalesInvoice
    }
    
    public static ValidatorInterface make(ValidatorFactory.TYPE foType, Object foValue){
        switch (foType) {
            case SalesInvoice_Master:
                return new Validator_SalesInvoice_Master(foValue);
            case VehicleSalesInvoice:
                return new Validator_VehicleSalesInvoice_Source(foValue);
            default:
                return null;
        }
    }
    
}
