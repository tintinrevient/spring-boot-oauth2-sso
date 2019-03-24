package com.hncy.platform.managerservice.model;

import com.hncy.platform.managerservice.common.excel.ExcelField;

import java.io.Serializable;

/**
 * 用户信息导入字段模板
 * @Auther: pengt
 * @Date: 2018-12-17 14:38
 * @Description:
 */
public class MemberTemplate implements Serializable{

    private static final long serialVersionUID = 1L;

    private String nickName;//用户名称
    private String loginAccount;//登录账户
    private String telPhone;//电话号码
    private String districtCode;//所属区域编码
    private String departmentId;//部门id

    @ExcelField(title = "用户名称",align = 2,sort = 1)
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @ExcelField(title = "登录账号",align = 2,sort = 2)
    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    @ExcelField(title = "联系电话",align = 2,sort = 3)
    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    //@ExcelField(title = "区域编号",align = 2,sort = 4)
    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    //@ExcelField(title = "部门主键",align = 2,sort = 5)
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
