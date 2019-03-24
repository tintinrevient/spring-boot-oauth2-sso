package com.hncy.platform.managerservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.hncy.platform.managerservice.common.msg.BaseResponse;
import com.hncy.platform.managerservice.common.excel.ExportExcel;
import com.hncy.platform.managerservice.model.Department;
import com.hncy.platform.managerservice.service.DepartmentRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门信息控制层类
 * @Auther: pengt
 * @Date: 2018-12-13 10:25
 * @Description:
 */
@RestController
@Component
@RequestMapping("department")
@Slf4j
public class DepartmentController {

    @Autowired
    private DepartmentRemoteService departmentRemoteService;

    /**
     * 打开部门信息列表页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String list(HttpServletRequest request) {
        return "department/department-list";
    }

    /**
     * 获取部门信息列表
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listDepartment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String listDepartment(HttpServletRequest request) {
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");
        String belongId = request.getParameter("belongId");

        if (StringUtils.isEmpty(pageNo)) {
            pageNo = "1";
        }
        if (StringUtils.isEmpty(pageSize)) {
            pageSize = "10";
        }
        String result = departmentRemoteService.listDepartment(pageNo, pageSize,belongId);
        return result;
    }

    /**
     * 获取treegird格式部门信息列表
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listDepartment2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String listDepartment2(HttpServletRequest request) {
        String belongId = request.getParameter("belongId");
        if(StringUtils.isEmpty(belongId)){
            belongId = "";
        }
        //查询部门数据
        String result = departmentRemoteService.getDepartmentByCondition(belongId);
        Map<String,Object> resultMap = JSONObject.parseObject(result);
        List<Map<String,Object>> departments = (List<Map<String,Object>>)resultMap.get("departments");

        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

        for (int i = 0; i < departments.size(); i++) {
            String id = String.valueOf(departments.get(i).get("id"));
            String departmentName = String.valueOf(departments.get(i).get("departmentName"));
            String departmentOrder = String.valueOf(departments.get(i).get("departmentOrder")==null?"":departments.get(i).get("departmentOrder"));

            String parentId = "";
            String parentName = "";
            Object parentDepartment = departments.get(i).get("parentDepartment");
            if(null != parentDepartment){
                Map<String,Object> parentMap = (Map<String,Object>)parentDepartment;
                parentId = String.valueOf(parentMap.get("id"));
                parentName = String.valueOf(parentMap.get("departmentName"));
            }

            Map<String,Object> districtMap = (Map<String,Object>)departments.get(i).get("district");
            String districtName = String.valueOf(districtMap.get("districtName"));
            String districtCode = String.valueOf(districtMap.get("districtCode"));

            if("".equals(parentId)){
                //根级节点
                Map<String,Object> rootMap = new HashMap<String,Object>();
                rootMap.put("id",id);
                rootMap.put("departmentName",departmentName);
                rootMap.put("departmentOrder",departmentOrder);
                rootMap.put("parentId",parentId);
                rootMap.put("parentName",parentName);
                rootMap.put("districtName",districtName);
                rootMap.put("districtCode",districtCode);
                list.add(districtTreeGrid(departments,rootMap));
            }
        }
        return JSONObject.toJSONString(list);
    }

    /**
     * 构造treegrid格式部门数据
     * @param departments
     * @param rootMap
     * @return
     */
    public Map<String,Object> districtTreeGrid(List<Map<String,Object>> departments,Map<String,Object> rootMap){
        List<Map<String,Object>> childrens = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < departments.size(); i++) {
            String parentId = "";
            String parentName = "";
            Object parentDepartment = departments.get(i).get("parentDepartment");
            if(null != parentDepartment){
                Map<String,Object> parentMap = (Map<String,Object>)parentDepartment;
                parentId = String.valueOf(parentMap.get("id"));
                parentName = String.valueOf(parentMap.get("departmentName"));
            }
            String rootId = String.valueOf(rootMap.get("id"));

            if(rootId.equals(parentId)){
                String id = String.valueOf(departments.get(i).get("id"));
                String departmentName = String.valueOf(departments.get(i).get("departmentName"));
                String departmentOrder = String.valueOf(departments.get(i).get("departmentOrder")==null?"":departments.get(i).get("departmentOrder"));

                Map<String,Object> districtMap = (Map<String,Object>)departments.get(i).get("district");
                String districtName = String.valueOf(districtMap.get("districtName"));
                String districtCode = String.valueOf(districtMap.get("districtCode"));

                Map<String,Object> childMap = new HashMap<>();
                childMap.put("id",id);
                childMap.put("departmentName",departmentName);
                childMap.put("departmentOrder",departmentOrder);
                childMap.put("parentId",parentId);
                childMap.put("parentName",parentName);
                childMap.put("districtName",districtName);
                childMap.put("districtCode",districtCode);
                childrens.add(districtTreeGrid(departments,childMap));
            }
        }
        if(childrens.size() > 0){
            rootMap.put("children",childrens);
        }
        return rootMap;
    }

    /**
     * 获取部门树数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "treeDepartment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String treeDepartment(HttpServletRequest request){
        String districtId = request.getParameter("districtId");
        if(StringUtils.isEmpty(districtId)){
            districtId = "";
        }
        return departmentRemoteService.treeDepartment(districtId,"");
    }


    /**
     * 获取区域部门组合树数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "compositeTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String compositeTree(){
        return departmentRemoteService.compositeTree();
    }

    /**
     * 新增部门信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveDepartment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String saveDepartment(HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        String districtId = request.getParameter("districtId");
        String pDepartmentId = request.getParameter("pDepartmentId");
        String departmentName = request.getParameter("departmentName");
        String departmentOrder = request.getParameter("departmentOrder");

        if(StringUtils.isEmpty(districtId)){
            baseResponse.setStatus(0);
            baseResponse.setMessage("区域编码为空!");
            return JSONObject.toJSONString(baseResponse);
        }

        if(StringUtils.isEmpty(departmentName)){
            baseResponse.setStatus(0);
            baseResponse.setMessage("部门名称为空!");
            return JSONObject.toJSONString(baseResponse);
        }

        try {
            String result = departmentRemoteService.storeDepartment(pDepartmentId,districtId,departmentName,Integer.parseInt(departmentOrder));
            return result;
        }catch (Exception e){
            e.printStackTrace();
            baseResponse.setStatus(0);
            baseResponse.setMessage("保存部门信息失败!");
        }
        return JSONObject.toJSONString(baseResponse);
    }

    /**
     * 修改部门信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateDepartment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String updateDepartment(HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        String departmentId = request.getParameter("departmentId");
        String districtId = request.getParameter("districtId");
        String pDepartmentId = request.getParameter("pDepartmentId");
        String departmentName = request.getParameter("departmentName");
        String departmentOrder = request.getParameter("departmentOrder");

        if(StringUtils.isEmpty(departmentId)){
            baseResponse.setStatus(0);
            baseResponse.setMessage("部门主键为空!");
            return JSONObject.toJSONString(baseResponse);
        }

        if(StringUtils.isEmpty(districtId)){
            baseResponse.setStatus(0);
            baseResponse.setMessage("区域编码为空!");
            return JSONObject.toJSONString(baseResponse);
        }

        if(StringUtils.isEmpty(departmentName)){
            baseResponse.setStatus(0);
            baseResponse.setMessage("部门名称为空!");
            return JSONObject.toJSONString(baseResponse);
        }

        try {
            String result = departmentRemoteService.updateDepartment(departmentId,pDepartmentId,districtId,departmentName,Integer.parseInt(departmentOrder));
            return result;
        }catch (Exception e){
            e.printStackTrace();
            baseResponse.setStatus(0);
            baseResponse.setMessage("保存区域信息失败!");
        }
        return JSONObject.toJSONString(baseResponse);
    }

    /**
     * 删除部门信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteDepartment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String deleteDepartment(HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        String id = request.getParameter("delId");

        if(StringUtils.isEmpty(id)){
            baseResponse.setStatus(0);
            baseResponse.setMessage("删除部门信息失败!");
            return JSONObject.toJSONString(baseResponse);
        }

        try {
            String result = departmentRemoteService.delDepartmentById(id);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            baseResponse.setStatus(0);
            baseResponse.setMessage("删除部门信息失败!");
        }
        return JSONObject.toJSONString(baseResponse);
    }

    /**
     * 导出部门信息
     * @param request
     * @return
     */
    @RequestMapping(value = "exportDepartment", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public void exportDepartment(HttpServletRequest request, HttpServletResponse response){
        String belongId = request.getParameter("belongId");
        //获取需要导出的部门信息数据
        String result = departmentRemoteService.listDepartment("1", "200",belongId);

        List<Department> list = new ArrayList<Department>();

        Map<String,Object> resultMap = (Map<String,Object>)JSONObject.parse(result);
        List<Map<String,Object>> datas = (List<Map<String,Object>>)resultMap.get("list");
        if(datas.size() > 0){
            for (int i = 0; i < datas.size(); i++) {
                String id = String.valueOf(datas.get(i).get("id"));
                String departmentName = String.valueOf(datas.get(i).get("departmentName"));
                Integer departmentOrder = Integer.parseInt(String.valueOf(datas.get(i).get("departmentOrder")));
                String districtCode = "";
                String districtName = "";
                if(datas.get(i).containsKey("district")){
                    Map<String,Object> districtMap = (Map<String,Object>)datas.get(i).get("district");
                    districtCode = String.valueOf(districtMap.get("districtCode"));
                    districtName = String.valueOf(districtMap.get("districtName"));
                }
                String parentDepartmentId = "";
                String parentDepartmentName = "";
                if(datas.get(i).containsKey("parentDepartment")){
                    Map<String,Object> parentDepartmentMap = (Map<String,Object>)datas.get(i).get("parentDepartment");
                    parentDepartmentId = String.valueOf(parentDepartmentMap.get("id"));
                    parentDepartmentName = String.valueOf(parentDepartmentMap.get("departmentName"));
                }

                Department department = new Department();
                department.setId(id);
                department.setDepartmentName(departmentName);
                department.setDepartmentOrder(departmentOrder);
                department.setDistrictName(districtName);
                department.setDistrictCode(districtCode);
                department.setParentDepartmentId(parentDepartmentId);
                department.setParentDepartmentName(parentDepartmentName);
                list.add(department);
            }
        }
        try {
            String fileName = "部门信息数据.xlsx";
            new ExportExcel("部门信息", Department.class).setDataList(list).write(response, fileName).dispose();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
