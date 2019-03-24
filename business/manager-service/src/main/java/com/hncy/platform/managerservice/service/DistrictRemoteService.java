package com.hncy.platform.managerservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 区域远程访问服务类
 *
 * @Auther: pengt
 * @Date: 2018-12-07 11:47
 * @Description:
 */
@FeignClient(value = "server-gateway/api/user/district")
public interface DistrictRemoteService {

    /**
     * 区域信息分页查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/listDistrict", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String listDistrict(@RequestParam("pageNo") String pageNo,
                        @RequestParam("pageSize") String pageSize);

    /**
     * 保存行政区域信息
     *
     * @param parentId
     * @param districtCode
     * @param districtName
     * @return
     */
    @RequestMapping(value = "/storeDistrict", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String storeDistrict(@RequestParam("parentId") String parentId,
                         @RequestParam("districtCode") String districtCode,
                         @RequestParam("districtName") String districtName);

    /**
     * 修改行政区域信息
     *
     * @param id
     * @param parentId
     * @param districtCode
     * @param districtName
     * @return
     */
    @RequestMapping(value = "/updateDistrict", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String updateDistrict(@RequestParam("id") String id,
                          @RequestParam("parentId") String parentId,
                          @RequestParam("districtCode") String districtCode,
                          @RequestParam("districtName") String districtName);

    /**
     * 根据id删除区域信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delDistrictById", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String delDistrictById(@RequestParam("id") String id);

    /**
     * 根据id查询区域信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getDistrictById", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String getDistrictById(@RequestParam("id") String id);

    /**
     * 获取区域树节点数据
     *
     * @return
     */
    @RequestMapping(value = "/treeDistrict", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String treeDistrict();

    /**
     * 获取全部区域数据
     *
     * @return
     */
    @RequestMapping(value = "/getAllDistrict", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    String getAllDistrict();

}
