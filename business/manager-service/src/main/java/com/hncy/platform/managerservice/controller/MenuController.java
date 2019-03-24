package com.hncy.platform.managerservice.controller;

import com.hncy.platform.managerservice.common.msg.ResultBean;
import com.hncy.platform.managerservice.service.MenuApiFeign;
import com.hncy.platform.managerservice.model.Menu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author chenwz
 * @date 2019-03-12 14:39
 */
@RestController
@Component
@Slf4j
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuApiFeign menuApiFeign;

    @RequestMapping(value = "list",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String list(){
        return "menu/menu-list";
    }

    @RequestMapping(value = "treeMenuList",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultBean<List<Menu>> treeMenuList(){
        List<Menu> data = menuApiFeign.treeMenuList();
        log.info("treeMenuList:{}",data);
        return ResultBean.success(data);
    }
}
