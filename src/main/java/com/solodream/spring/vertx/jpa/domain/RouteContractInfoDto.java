package com.solodream.spring.vertx.jpa.domain;

import com.solodream.spring.vertx.jpa.model.base.BaseBean;


public class RouteContractInfoDto extends BaseBean {

    private static final long serialVersionUID = -12222L;


    private String name;


    private Integer type;


    private String startPoi;


    private String startTime;


	private String endPoi;


    private String endTime;


    private Integer orgId;


    private String orgName;


    private Integer contractId;


    private String contractName;


    private String remark;

    //0:route 1:job 2:contract
    private Integer routeType;

    private Integer customerId;

    private String customerName;

    private String customerPhone;

    private Integer companyId;

    private String companyName;

    private String updateBy;

    private String poisUnique;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStartPoi() {
        return startPoi;
    }

    public void setStartPoi(String startPoi) {
        this.startPoi = startPoi;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndPoi() {
        return endPoi;
    }

    public void setEndPoi(String endPoi) {
        this.endPoi = endPoi;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getRouteType() {
        return routeType;
    }

    public void setRouteType(Integer routeType) {
        this.routeType = routeType;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getPoisUnique() {
        return poisUnique;
    }

    public void setPoisUnique(String poisUnique) {
        this.poisUnique = poisUnique;
    }
}
