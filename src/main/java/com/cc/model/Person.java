package com.cc.model;

/**
 * Created by bigcong on 10/01/2017.
 */
public class Person {
    private String transation_no;
    private String business_order_number;
    private String transation_create_time="1989-07-07 00:00:00";
    private String paid_time="1989-07-07 00:00:00";
    private String last_modified_time="1989-07-07 00:00:00";
    private String transation_source;
    private String transation_type;
    private String counterparty;
    private String goods_name;
    private String amount;
    private String payment_type;
    private String transation_status;
    private String service_charge;
    private String success_refund_amount;
    private String remark;
    private String fund_status;

    public String getTransation_create_time() {
        return transation_create_time;
    }

    public void setTransation_create_time(String transation_create_time) {
        this.transation_create_time = transation_create_time;
    }

    /**
     *     /**
     *  `transation_no` varchar(255) DEFAULT NULL COMMENT '交易号',
     `business_order_number` varchar(255) DEFAULT NULL COMMENT '商户订单号',
     `transation_create_time` datetime DEFAULT NULL COMMENT '交易创建时间',
     `paid_time` datetime DEFAULT NULL COMMENT '支付时间',
     `last_modified_time` datetime DEFAULT NULL COMMENT '最近修改时间',
     `transation_source` varchar(255) DEFAULT NULL COMMENT '交易来源',
     `transation_type` varchar(255) DEFAULT NULL COMMENT '交易类型',
     `counterparty` varchar(255) DEFAULT NULL COMMENT '交易对方',
     `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名字',
     `amount` double DEFAULT NULL COMMENT '金额',
     `payment_type` varchar(255) DEFAULT NULL COMMENT '收支',
     `transation_status` varchar(255) DEFAULT NULL COMMENT '交易状态',
     `service_charge` double DEFAULT NULL COMMENT '服务费',
     `success_refund_amount` double DEFAULT NULL COMMENT '成功退款',
     `remark` varchar(255) DEFAULT NULL COMMENT '备注',
     `fund_status` varchar(255) DEFAULT NULL COMMENT '资金状态'
     * @return
     */

    public String getTransation_no() {
        return transation_no;
    }

    public void setTransation_no(String transation_no) {
        this.transation_no = transation_no;
    }

    public String getBusiness_order_number() {
        return business_order_number;
    }

    public void setBusiness_order_number(String business_order_number) {
        this.business_order_number = business_order_number;
    }

    public String getPaid_time() {
        return paid_time;
    }

    public void setPaid_time(String paid_time) {
        this.paid_time = paid_time;
    }

    public String getLast_modified_time() {
        return last_modified_time;
    }

    public void setLast_modified_time(String last_modified_time) {
        this.last_modified_time = last_modified_time;
    }

    public String getTransation_source() {
        return transation_source;
    }

    public void setTransation_source(String transation_source) {
        this.transation_source = transation_source;
    }

    public String getTransation_type() {
        return transation_type;
    }

    public void setTransation_type(String transation_type) {
        this.transation_type = transation_type;
    }

    public String getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(String counterparty) {
        this.counterparty = counterparty;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getTransation_status() {
        return transation_status;
    }

    public void setTransation_status(String transation_status) {
        this.transation_status = transation_status;
    }

    public String getService_charge() {
        return service_charge;
    }

    public void setService_charge(String service_charge) {
        this.service_charge = service_charge;
    }

    public String getSuccess_refund_amount() {
        return success_refund_amount;
    }

    public void setSuccess_refund_amount(String success_refund_amount) {
        this.success_refund_amount = success_refund_amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFund_status() {
        return fund_status;
    }

    public void setFund_status(String fund_status) {
        this.fund_status = fund_status;
    }
}
