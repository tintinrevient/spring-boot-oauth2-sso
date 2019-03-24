package com.hncy.platform.managerservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.hncy.platform.managerservice.common.msg.BaseResponse;
import com.hncy.platform.managerservice.service.DistrictRemoteService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 行政区域控制层类
 *
 * @Auther: pengt
 * @Date: 2018-12-07 10:42
 * @Description:
 */
@RestController
@Component
@RequestMapping("district")
public class DistrictController {

    @Autowired
    private DistrictRemoteService districtRemoteService;

    @RequestMapping(value = "/index", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String index() {
        return "index";
    }

    /**
     * 打开行政区域信息列表页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String list(HttpServletRequest request) {

        //String result = districtRemoteService.listDistrict("1","10");
        //System.out.println("==="+result);
        return "district/district-list";
    }

    /**
     * 获取行政区域信息列表
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listDistrict", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String listDistrict(HttpServletRequest request) {
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");
        if (StringUtils.isEmpty(pageNo)) {
            pageNo = "1";
        }
        if (StringUtils.isEmpty(pageSize)) {
            pageSize = "10";
        }
        String result = districtRemoteService.listDistrict(pageNo, pageSize);
        return result;
    }

    /**
     * 新增区域信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveDistrict", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String saveDistrict(HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        String parentId = request.getParameter("parentId");
        String districtCode = request.getParameter("districtCode");
        String districtName = request.getParameter("districtName");

        if(StringUtils.isEmpty(districtCode)){
            baseResponse.setStatus(0);
            baseResponse.setMessage("区域编码为空!");
            return JSONObject.toJSONString(baseResponse);
        }

        if(StringUtils.isEmpty(districtName)){
            baseResponse.setStatus(0);
            baseResponse.setMessage("区域名称为空!");
            return JSONObject.toJSONString(baseResponse);
        }

        try {
            String result = districtRemoteService.storeDistrict(parentId,districtCode,districtName);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            baseResponse.setStatus(0);
            baseResponse.setMessage("保存区域信息失败!");
        }
        return JSONObject.toJSONString(baseResponse);
    }

    /**
     * 修改区域信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateDistrict", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String updateDistrict(HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        String districtId = request.getParameter("districtId");
        String parentId = request.getParameter("parentId");
        String districtCode = request.getParameter("districtCode");
        String districtName = request.getParameter("districtName");

        if(StringUtils.isEmpty(districtId)){
            baseResponse.setStatus(0);
            baseResponse.setMessage("区域主键不能为空!");
            return JSONObject.toJSONString(baseResponse);
        }

        try {
            //修改区域信息
            String result = districtRemoteService.updateDistrict(districtId,parentId,districtCode,districtName);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            baseResponse.setStatus(0);
            baseResponse.setMessage("保存区域信息失败!");
        }
        return JSONObject.toJSONString(baseResponse);
    }

    /**
     * 获取区域树数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "treeDistrict", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String treeDistrict(){
        return districtRemoteService.treeDistrict();
    }

    /**
     * 删除区域信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteDistrict", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String deleteDistrict(HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        String id = request.getParameter("delId");

        if(StringUtils.isEmpty(id)){
            baseResponse.setStatus(0);
            baseResponse.setMessage("删除区域信息失败!");
            return JSONObject.toJSONString(baseResponse);
        }

        try {
            String result = districtRemoteService.delDistrictById(id);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            baseResponse.setStatus(0);
            baseResponse.setMessage("删除区域信息失败!");
        }
        return JSONObject.toJSONString(baseResponse);
    }

    /**
     * 获取行政区域信息列表 treeGrid格式显示
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listDistrict2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String listDistrict2(HttpServletRequest request) {
        //获取全部行政区域信息
        String result = districtRemoteService.getAllDistrict();
        Map<String,Object> resultMap = (Map<String,Object>)JSONObject.parse(result);
        List<Map<String,Object>> districts = (List<Map<String,Object>>)resultMap.get("districts");

        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        //构造区域treeGrid树数据
        for (int i = 0; i < districts.size(); i++) {
            String parentId = String.valueOf(districts.get(i).get("parentId"));
            if("0".equals(parentId)){
                //区域根节点
                String id = String.valueOf(districts.get(i).get("id"));
                String districtCode = String.valueOf(districts.get(i).get("districtCode"));
                String districtName = String.valueOf(districts.get(i).get("districtName"));
                Map<String,Object> rootMap = new HashMap<>();
                rootMap.put("id",id);
                rootMap.put("districtCode",districtCode);
                rootMap.put("districtName",districtName);
                rootMap.put("parentId",parentId);
                list.add(districtTreeGrid(districts,rootMap));
            }
        }
        return JSONObject.toJSONString(list);
    }

    /**
     * 构造treeGrid结构区域树网格
     * @param districts
     * @param rootMap
     * @return
     */
    public Map<String,Object> districtTreeGrid(List<Map<String,Object>> districts,Map<String,Object> rootMap){
        List<Map<String,Object>> childrens = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < districts.size(); i++) {
            String parentId = String.valueOf(districts.get(i).get("parentId"));
            String rootId = String.valueOf(rootMap.get("id"));
            if(rootId.equals(parentId)){
                String id = String.valueOf(districts.get(i).get("id"));
                String districtCode = String.valueOf(districts.get(i).get("districtCode"));
                String districtName = String.valueOf(districts.get(i).get("districtName"));
                Map<String,Object> childMap = new HashMap<>();
                childMap.put("id",id);
                childMap.put("districtCode",districtCode);
                childMap.put("districtName",districtName);
                childMap.put("parentId",parentId);
                childrens.add(districtTreeGrid(districts,childMap));
            }
        }
        if(childrens.size() > 0){
            rootMap.put("children",childrens);
        }
        return rootMap;
    }
}
