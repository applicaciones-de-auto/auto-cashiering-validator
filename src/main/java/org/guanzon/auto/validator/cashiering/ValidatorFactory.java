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
        , SalesInvoice_Payment
        , SalesInvoice_Advances_Source
        , VehicleSalesInvoice
        , CashierReceivables_Master
        , CashierReceivables_Detail
        , StatementOfAccount_Master
        , StatementOfAccount_Detail
    }
    
    public static ValidatorInterface make(ValidatorFactory.TYPE foType, Object foValue){
        switch (foType) {
            case SalesInvoice_Master:
                return new Validator_SalesInvoice_Master(foValue);
            case SalesInvoice_Source:
                return new Validator_SalesInvoice_Source(foValue);
            case SalesInvoice_Payment:
                return new Validator_SalesInvoice_Payment(foValue);
            case SalesInvoice_Advances_Source:
                return new Validator_SalesInvoice_Advances_Source(foValue);
            case VehicleSalesInvoice:
                return new Validator_VehicleSalesInvoice_Source(foValue);
            case CashierReceivables_Master:
                return new Validator_CashierReceivables_Master(foValue);
            case CashierReceivables_Detail:
                return new Validator_CashierReceivables_Detail(foValue);
            case StatementOfAccount_Master:
                return new Validator_StatementOfAccount_Master(foValue);
            case StatementOfAccount_Detail:
                return new Validator_StatementOfAccount_Detail(foValue);
            default:
                return null;
        }
    }
    
}
