package com.hncy.platform.managerservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 成员远程访问服务类
 *
 * @Auther: pengt
 * @Date: 2018-12-07 15:26
 * @Description:
 */
@FeignClient(value = "server-gateway/api/user/member")
public interface MemberRemoteService {

    /**
     * 保存成员信息
     *
     * @param districtId
     * @param departmentId
     * @param loginAccount
     * @param nickName
     * @param telPhone
     * @return
     */
    @RequestMapping(value = "/storeMember", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String storeMember(@RequestParam("districtId") String districtId,
                       @RequestParam("departmentId") String departmentId,
                       @RequestParam("loginAccount") String loginAccount,
                       @RequestParam("nickName") String nickName,
                       @RequestParam("telPhone") String telPhone);


    /**
     * 修改成员信息
     *
     * @param id
     * @param districtId
     * @param departmentId
     * @param loginAccount
     * @param nickName
     * @param telPhone
     * @return
     */
    @RequestMapping(value = "/updateMember", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String updateMember(@RequestParam("id") String id,
                        @RequestParam("districtId") String districtId,
                        @RequestParam("departmentId") String departmentId,
                        @RequestParam("loginAccount") String loginAccount,
                        @RequestParam("nickName") String nickName,
                        @RequestParam("telPhone") String telPhone);

    /**
     * 获取成员信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getMemberById", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String getMemberById(@RequestParam("id") String id);

    /**
     * 删除成员信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delMemberById", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String delMemberById(@RequestParam("id") String id);

    /**
     * 批量删除成员信息
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delMemberByIds", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String delMemberByIds(@RequestParam("ids") String ids);

    /**
     * 分页查询成员信息
     *
     * @param pageNo
     * @param pageSize
     * @param belongId 区域或者部门id
     * @return
     */
    @RequestMapping(value = "/listMember", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String listMember(@RequestParam("pageNo") String pageNo,
                      @RequestParam("pageSize") String pageSize,
                      @RequestParam(value = "belongId", required = false) String belongId);

    /**
     * 修改成员登录密码
     *
     * @param ids
     * @param newPwd
     * @return
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String updatePassword(@RequestParam("ids") String ids,
                          @RequestParam("newPwd") String newPwd);

    /**
     * 用户登录
     *
     * @param loginAccount
     * @param loginPassword
     * @return
     */
    @RequestMapping(value = "/loginUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String loginUser(@RequestParam("loginAccount") String loginAccount,
                     @RequestParam("loginPassword") String loginPassword);
}
