package com.jsloan.repayment;

import java.math.BigDecimal;

import com.jsloan.common.constant.Constants;

import lombok.Data;

/**
 * @Date : 2018. 2. 5
 * @author Kim jongseong
 * @Descrption : 대출상환 (상환계획에 따라 처리된 수납내역 결과)
 */
@Data
public class LoanRepayment {
    
    //상환계획 회차
    private int termNo;

    //상환계획 일자
    private String planDate;
    
    //기준일자
    private String baseDate;
    
    //수납일자
    private String receiveDate;
    
    //원금 (상환처리된 )
    private BigDecimal principal;
    
    //이자 (상환 된)
    private BigDecimal interest;
    
    //연체료 (상환 된)
    private BigDecimal overdueFee;

    //총금액 (상환 된) 
    private BigDecimal repayAmount;    
    
    //대출잔액 (상환 전)    
    private BigDecimal balance;
    
    //대출잔액 (상환 후)
    private BigDecimal afterBalance;
    
    //상환종류
    private Constants.RepayType repayType;

    //중도상환수수료
    private BigDecimal earlyRedemptionFee;

    public LoanRepayment() {
        
    }
    
    
    public LoanRepayment(LoanRepayment loanRepayment) {
        this.termNo       = loanRepayment.termNo      ;
        this.planDate     = loanRepayment.planDate    ;
        this.baseDate     = loanRepayment.baseDate    ;
        this.receiveDate  = loanRepayment.receiveDate ; 
        this.principal    = loanRepayment.principal   ;
        this.interest     = loanRepayment.interest    ;
        this.overdueFee   = loanRepayment.overdueFee  ;
        this.repayAmount  = loanRepayment.repayAmount ;
        this.balance      = loanRepayment.balance     ;
        this.afterBalance = loanRepayment.afterBalance;
        this.repayType    = loanRepayment.repayType   ;
        this.earlyRedemptionFee    = loanRepayment.earlyRedemptionFee   ;
    }

    /**
     * 상환내역을 생성한다.
     */
    public static LoanRepayment createRepayment(LoanReceipt receipt, LoanRepayPlan repayPlan, BigDecimal repayOverDueFee,
                                          BigDecimal repayInterest, BigDecimal repayPrincipal) {

        LoanRepayment repayment = new LoanRepayment();
        repayment.setReceiveDate(receipt.getReceiveDate());
        repayment.setBaseDate(receipt.getBaseDate());
        repayment.setPlanDate(repayPlan.getPlanDate());
        repayment.setTermNo(repayPlan.getTermNo());
        repayment.setRepayType(receipt.getRepayType());
        repayment.setBalance(repayPlan.getBalance().subtract(repayPlan.getRecvPrincipal()));
        repayment.setOverdueFee( repayOverDueFee );
        repayment.setInterest( repayInterest );
        repayment.setPrincipal( repayPrincipal );
        repayment.setRepayAmount(repayment.getOverdueFee()
                .add(repayment.getInterest())
                .add(repayment.getPrincipal()));
        repayment.setAfterBalance(repayment.getBalance().subtract(repayment.getPrincipal()));
        repayment.setEarlyRedemptionFee(repayPlan.getEarlyRedemptionFee());
        return repayment;

    }
}