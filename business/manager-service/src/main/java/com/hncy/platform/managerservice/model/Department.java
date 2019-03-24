package com.hncy.platform.managerservice.model;

import com.hncy.platform.managerservice.common.excel.ExcelField;

import java.io.Serializable;

/**
 * 部门信息类
 * @Auther: pengt
 * @Date: 2018-12-17 10:18
 * @Description:
 */
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;//部门id
    private String departmentName;//部门名称
    private String districtCode;//所属区域编码
    private String districtName;//所属区域名称
    private String parentDepartmentName;//父级部门名称
    private String parentDepartmentId;//父级部门编码
    private Integer departmentOrder;//部门序号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ExcelField(title = "部门名称",align = 2,sort = 1)
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }


    @ExcelField(title = "区域编码",align = 2,sort = 3)
    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    @ExcelField(title = "所属区域",align = 2,sort = 2)
    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    @ExcelField(title = "父级单位",align = 2,sort = 4)
    public String getParentDepartmentName() {
        return parentDepartmentName;
    }

    public void setParentDepartmentName(String parentDepartmentName) {
        this.parentDepartmentName = parentDepartmentName;
    }

    public String getParentDepartmentId() {
        return parentDepartmentId;
    }

    public void setParentDepartmentId(String parentDepartmentId) {
        this.parentDepartmentId = parentDepartmentId;
    }

    @ExcelField(title = "部门序号",align = 2,sort = 5)
    public Integer getDepartmentOrder() {
        return departmentOrder;
    }

    public void setDepartmentOrder(Integer departmentOrder) {
        this.departmentOrder = departmentOrder;
    }
}
