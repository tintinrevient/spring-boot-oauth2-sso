package com.hncy.platform.managerservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 部门远程访问服务类
 *
 * @Auther: pengt
 * @Date: 2018-12-07 15:13
 * @Description:
 */
@FeignClient(value = "server-gateway/api/user/department")
public interface DepartmentRemoteService {

    /**
     * 保存部门信息
     *
     * @param parentId
     * @param districtCode
     * @param departmentName
     * @param departmentOrder
     * @return
     */
    @RequestMapping(value = "/storeDepartment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String storeDepartment(@RequestParam("parentId") String parentId,
                           @RequestParam("districtCode") String districtCode,
                           @RequestParam("departmentName") String departmentName,
                           @RequestParam("departmentOrder") Integer departmentOrder);

    /**
     * 分页查询部门信息
     *
     * @param pageNo
     * @param pageSize
     * @param belongId
     * @return
     */
    @RequestMapping(value = "/listDepartment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String listDepartment(@RequestParam("pageNo") String pageNo,
                          @RequestParam("pageSize") String pageSize,
                          @RequestParam("belongId") String belongId);


    /**
     * 修改部门信息
     *
     * @param id
     * @param parentId
     * @param districtCode
     * @param departmentName
     * @param departmentOrder
     * @return
     */
    @RequestMapping(value = "/updateDepartment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String updateDepartment(@RequestParam("id") String id,
                            @RequestParam("parentId") String parentId,
                            @RequestParam("districtCode") String districtCode,
                            @RequestParam("departmentName") String departmentName,
                            @RequestParam("departmentOrder") Integer departmentOrder);


    /**
     * 根据id删除部门信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delDepartmentById", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String delDepartmentById(@RequestParam("id") String id);

    /**
     * 根据id查询部门信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getDepartmentById", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String getDepartmentById(@RequestParam("id") String id);

    /**
     * 获取部门树节点数据
     * @param districtId 区域id
     * @param departmentId 部门id
     * @return
     */
    @RequestMapping(value = "/treeDepartment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String treeDepartment(@RequestParam(value = "districtId", required = false) String districtId,
                          @RequestParam(value = "departmentId", required = false) String departmentId);

    /**
     * 获取行政区域部门组合树节点数据
     *
     * @return
     */
    @RequestMapping(value = "/compositeTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String compositeTree();

    /**
     * 条件查询部门信息数据
     *
     * @return
     */
    @RequestMapping(value = "/getDepartmentByCondition", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String getDepartmentByCondition(@RequestParam(value = "belongId", required = false) String belongId);
}
