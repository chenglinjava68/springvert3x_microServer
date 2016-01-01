package com.solodream.spring.vertx.jpa.domain;

import com.solodream.spring.vertx.jpa.model.base.BaseBean;

/**
 * ScheduleInfo
 *
 * @author Young
 * @version 1.0
 * @since 2015-09-24 00:18:16
 */
public class ScheduleInfoDto extends BaseBean {

    private static final long serialVersionUID = -1L;

    private String jobAllowance;
    /**
     * 排班日期
     */
    private java.util.Date departureDate;
    
    private java.util.Date departureTime;

    /**
     * 车辆ID
     */
    private Integer vehicleId;

    /**
     * 车牌号
     */
    private String vehicleNo;

    /**
     * 司机ID
     */
    private Integer driverId;

    /**
     * 司机姓名
     */
    private String driverName;

    /**
     * 司机号码
     */
    private String driverMobile;

    /**
     * 路线ID
     */
    private Integer routeId;

    /**
     * 路线名称
     */
    private String routeName;

    /**
     * 合同ID
     */
    private Integer contractId;

    /**
     * 合同编号或名称
     */
    private String contractNo;

    /**
     * 客户公司ID
     */
    private Integer customerId;

    /**
     * 客户公司名称
     */
    private String customerName;

    /**
     * 操作员
     */
    private String operator;

    /**
     * 备注
     */
    private String remark;


    private java.util.Date startTime;

    private String dayType;


    private String tripType;


    private String planCap;

    private Integer companyId;

    private String companyName;

    private String customerPhone;

    private String updateBy;

    public String getDayType() {
        return dayType;
    }

    public void setDayType(String dayType) {
        this.dayType = dayType;
    }

    public String getPlanCap() {
        return planCap;
    }

    public void setPlanCap(String planCap) {
        this.planCap = planCap;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getDriverMobile() {
        return driverMobile;
    }

    public void setDriverMobile(String driverMobile) {
        this.driverMobile = driverMobile;
    }

    /**
     * 车辆ID
     *
     * @return
     */
    public Integer getVehicleId() {
        return this.vehicleId;
    }

    /**
     * 车辆ID
     *
     * @param vehicleId
     */
    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    /**
     * 车牌号
     *
     * @return
     */
    public String getVehicleNo() {
        return this.vehicleNo;
    }

    /**
     * 车牌号
     *
     * @param vehicleNo
     */
    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    /**
     * 司机ID
     *
     * @return
     */
    public Integer getDriverId() {
        return this.driverId;
    }

    /**
     * 司机ID
     *
     * @param driverId
     */
    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    /**
     * 司机姓名
     *
     * @return
     */
    public String getDriverName() {
        return this.driverName;
    }

    /**
     * 司机姓名
     *
     * @param driverName
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    /**
     * 路线ID
     *
     * @return
     */
    public Integer getRouteId() {
        return this.routeId;
    }

    /**
     * 路线ID
     *
     * @param routeId
     */
    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    /**
     * 路线名称
     *
     * @return
     */
    public String getRouteName() {
        return this.routeName;
    }

    /**
     * 路线名称
     *
     * @param routeName
     */
    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    /**
     * 合同ID
     *
     * @return
     */
    public Integer getContractId() {
        return this.contractId;
    }

    /**
     * 合同ID
     *
     * @param contractId
     */
    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    /**
     * 合同编号或名称
     *
     * @return
     */
    public String getContractNo() {
        return this.contractNo;
    }

    /**
     * 合同编号或名称
     *
     * @param contractNo
     */
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    /**
     * 客户公司ID
     *
     * @return
     */
    public Integer getCustomerId() {
        return this.customerId;
    }

    /**
     * 客户公司ID
     *
     * @param customerId
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * 客户公司名称
     *
     * @return
     */
    public String getCustomerName() {
        return this.customerName;
    }

    /**
     * 客户公司名称
     *
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 操作员
     *
     * @return
     */
    public String getOperator() {
        return this.operator;
    }

    /**
     * 操作员
     *
     * @param operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 备注
     *
     * @return
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 备注
     *
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
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

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getJobAllowance() {
		return jobAllowance;
	}

	public void setJobAllowance(String jobAllowance) {
		this.jobAllowance = jobAllowance;
	}

	public java.util.Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(java.util.Date departureDate) {
		this.departureDate = departureDate;
	}

	public java.util.Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(java.util.Date departureTime) {
		this.departureTime = departureTime;
	}

	public java.util.Date getStartTime() {
		return startTime;
	}

	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

}
