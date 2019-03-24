package com.hncy.platform.managerservice.service;

import com.hncy.platform.managerservice.model.Menu;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author chenwz
 * @date 2019-03-12 17:04
 */
@FeignClient(value = "server-gateway/api/user/menu")
public interface MenuApiFeign {

    @RequestMapping(value = "treeMenuList",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    List<Menu> treeMenuList();
}
