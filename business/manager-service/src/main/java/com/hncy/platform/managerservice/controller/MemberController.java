package com.hncy.platform.managerservice.controller;

import com.alibaba.fastjson.JSONObject;

import com.hncy.platform.managerservice.common.msg.BaseResponse;
import com.hncy.platform.managerservice.common.msg.ResponseCode;
import com.hncy.platform.managerservice.common.excel.ExportExcel;
import com.hncy.platform.managerservice.common.excel.ImportExcel;
import com.hncy.platform.managerservice.model.Member;
import com.hncy.platform.managerservice.model.MemberTemplate;
import com.hncy.platform.managerservice.service.DepartmentRemoteService;
import com.hncy.platform.managerservice.service.MemberRemoteService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户信息控制层类
 * @Auther: pengt
 * @Date: 2018-12-13 10:02
 * @Description:
 */
@RestController
@Component
@RequestMapping("member")
public class MemberController {

    @Autowired
    private MemberRemoteService memberRemoteService;

    @Autowired
    private DepartmentRemoteService departmentRemoteService;

    /**
     * 打开用户信息列表页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String list(HttpServletRequest request) {
        return "member/member-list";
    }

    /**
     * 获取用户信息列表
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listMember", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String listDistrict(HttpServletRequest request) {
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");
        //人员所属区域或者部门的id
        String belongId = request.getParameter("belongId");

        if (StringUtils.isEmpty(pageNo)) {
            pageNo = "1";
        }
        if (StringUtils.isEmpty(pageSize)) {
            pageSize = "10";
        }
        String result = memberRemoteService.listMember(pageNo, pageSize,belongId);
        return result;
    }

    /**
     * 新增用户信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveMember", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String saveMember(HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        String districtId = request.getParameter("districtId");
        String departmentId = request.getParameter("departmentId");
        String nickName = request.getParameter("nickName");
        String loginAccount = request.getParameter("loginAccount");
        String telPhone = request.getParameter("telPhone");

        if(StringUtils.isEmpty(loginAccount)){
            baseResponse.setStatus(ResponseCode.ERROR);
            baseResponse.setMessage("用户登录账号不能为空!");
            return JSONObject.toJSONString(baseResponse);
        }

        try {
            String result = memberRemoteService.storeMember(districtId,departmentId,loginAccount,nickName,telPhone);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            baseResponse.setStatus(ResponseCode.ERROR);
            baseResponse.setMessage("保存用户信息失败!");
        }
        return JSONObject.toJSONString(baseResponse);
    }

    /**
     * 修改用户信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateMember", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String updateMember(HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        String memberId = request.getParameter("memberId");
        String districtId = request.getParameter("districtId");
        String departmentId = request.getParameter("departmentId");
        String nickName = request.getParameter("nickName");
        String loginAccount = request.getParameter("loginAccount");
        String telPhone = request.getParameter("telPhone");

        if(StringUtils.isEmpty(memberId)){
            baseResponse.setStatus(ResponseCode.ERROR);
            baseResponse.setMessage("用户主键不能为空!");
            return JSONObject.toJSONString(baseResponse);
        }

        try {
            //修改用户信息
            String result = memberRemoteService.updateMember(memberId,districtId,departmentId,loginAccount,nickName,telPhone);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            baseResponse.setStatus(ResponseCode.ERROR);
            baseResponse.setMessage("保存用户信息失败!");
        }
        return JSONObject.toJSONString(baseResponse);
    }

    /**
     * 删除用户信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteMember", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String deleteMember(HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        String ids = request.getParameter("delIds");

        if(StringUtils.isEmpty(ids)){
            baseResponse.setStatus(ResponseCode.ERROR);
            baseResponse.setMessage("删除用户信息失败!");
            return JSONObject.toJSONString(baseResponse);
        }

        try {
            String result = memberRemoteService.delMemberByIds(ids);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            baseResponse.setStatus(ResponseCode.ERROR);
            baseResponse.setMessage("删除用户信息失败!");
        }
        return JSONObject.toJSONString(baseResponse);
    }

    /**
     * 修改用户登录密码
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updatePwd", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String updatePwd(HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        String memberIds = request.getParameter("memberIds");
        String newPwd = request.getParameter("newPwd");

        if(StringUtils.isEmpty(memberIds)){
            baseResponse.setStatus(ResponseCode.ERROR);
            baseResponse.setMessage("用户主键不能为空!");
            return JSONObject.toJSONString(baseResponse);
        }

        if(StringUtils.isEmpty(newPwd)){
            baseResponse.setStatus(ResponseCode.ERROR);
            baseResponse.setMessage("新密码不能为空!");
            return JSONObject.toJSONString(baseResponse);
        }

        try {
            //修改用户登录密码
            String result = memberRemoteService.updatePassword(memberIds,newPwd);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            baseResponse.setStatus(ResponseCode.ERROR);
            baseResponse.setMessage("修改用户登录密码失败!");
        }
        return JSONObject.toJSONString(baseResponse);
    }

    /**
     * 导出用户信息
     * @param request
     * @return
     */
    @RequestMapping(value = "exportMember", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public void exportMember(HttpServletRequest request, HttpServletResponse response){
        String belongId = request.getParameter("belongId");
        //获取需要导出的用户信息数据
        String result = memberRemoteService.listMember("1", "200",belongId);

        List<Member> list = new ArrayList<Member>();

        Map<String,Object> resultMap = (Map<String,Object>)JSONObject.parse(result);
        List<Map<String,Object>> datas = (List<Map<String,Object>>)resultMap.get("list");
        if(datas.size() > 0){
            for (int i = 0; i < datas.size(); i++) {
                String id = String.valueOf(datas.get(i).get("id"));
                String nickName = String.valueOf(datas.get(i).get("nickName"));
                String loginAccount = String.valueOf(datas.get(i).get("loginAccount"));
                String telPhone = String.valueOf(datas.get(i).get("telPhone")==null?"":datas.get(i).get("telPhone"));
                String districtCode = "";
                String districtName = "";
                if(datas.get(i).containsKey("district")){
                    Map<String,Object> districtMap = (Map<String,Object>)datas.get(i).get("district");
                    districtCode = String.valueOf(districtMap.get("districtCode"));
                    districtName = String.valueOf(districtMap.get("districtName"));
                }
                String departmentId = "";
                String departmentName = "";
                if(datas.get(i).containsKey("department")){
                    Map<String,Object> parentDepartmentMap = (Map<String,Object>)datas.get(i).get("department");
                    departmentId = String.valueOf(parentDepartmentMap.get("id"));
                    departmentName = String.valueOf(parentDepartmentMap.get("departmentName"));
                }

                Member member = new Member();
                member.setId(id);
                member.setNickName(nickName);
                member.setLoginAccount(loginAccount);
                member.setTelPhone(telPhone);
                member.setDistrictCode(districtCode);
                member.setDistrictName(districtName);
                member.setDepartmentName(departmentName);
                member.setDepartmentId(departmentId);
                list.add(member);
            }
        }
        try {
            String fileName = "用户信息数据.xlsx";
            new ExportExcel("用户信息", Member.class).setDataList(list).write(response, fileName).dispose();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 导入用户信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "importMember", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String importMember(HttpServletRequest request, HttpServletResponse response){
        StringBuilder failureMsg = new StringBuilder();
        int successNum = 0;//导入成功条数
        int failureNum = 0;//导入失败条数
        try {
            //导入用户所属的部门id
            String importDepartmentId = request.getParameter("importDepartmentId");
            if(StringUtils.isEmpty(importDepartmentId)){
                request.setAttribute("returnMsg","导入用户信息失败!");
                return "member/member-list";
            }

            //获取部门信息
            String d = departmentRemoteService.getDepartmentById(importDepartmentId);
            Map<String,Object> departMap = JSONObject.parseObject(d);
            MultipartHttpServletRequest re = (MultipartHttpServletRequest)request;
            List<MultipartFile> files = re.getFiles("file1");
            MultipartFile file = null;
            if(null != files && files.size() > 0){
                file = files.get(0);
            }
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<MemberTemplate> list = ei.getDataList(MemberTemplate.class);
            if(null != list && list.size() > 0){
                for (int i = 0; i < list.size(); i++) {
                    //解决电话号码传入格式为科学计数法
                    String telPhone = list.get(i).getTelPhone();
                    if(!telPhone.contains("-") && telPhone.length()>=11){
                        BigDecimal bd = new BigDecimal(telPhone);
                        telPhone = bd.toPlainString();
                    }
                    String result = memberRemoteService.storeMember(String.valueOf(departMap.get("district")),String.valueOf(departMap.get("id")),list.get(i).getLoginAccount(),list.get(i).getNickName(),telPhone);
                    Map<String,Object> resultMap = (Map<String,Object>)JSONObject.parse(result);
                    if(Integer.parseInt(String.valueOf(resultMap.get("status"))) == ResponseCode.ERROR){
                        //保存失败
                        failureMsg.append("登录账号:"+list.get(i).getNickName()+" "+resultMap.get("message")+",");
                        failureNum++;
                    }else if(Integer.parseInt(String.valueOf(resultMap.get("status"))) == ResponseCode.SUCCESS){
                        //保存成功
                        successNum++;
                    }
                }
            }
            if(failureNum > 0){
                failureMsg.insert(0,",失败 "+failureNum+" 条,失败用户信息如下:");
            }
            String msg = "已成功导入 "+successNum+" 条用户"+failureMsg.toString();
            /*response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('"+msg+"');");
            out.println("</script>");
            out.close();*/
            request.setAttribute("returnMsg",msg);
        }catch (Exception e){
            e.printStackTrace();
            /*try {
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('导入失败')");
                out.println("</script>");
                out.close();
            }catch (IOException e1){
                e1.printStackTrace();
            }*/
            request.setAttribute("returnMsg","导入用户信息失败!");
        }
        return "member/member-list";
    }

    /**
     * 导入用户信息模板下载
     * @param file 导入的附件
     * @return
     */
    @RequestMapping(value = "importMemberTemplate", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public void importMemberTemplate(MultipartFile file, HttpServletResponse response){
        try {
            List<MemberTemplate> list = new ArrayList<MemberTemplate>();
            MemberTemplate template = new MemberTemplate();
            template.setNickName("管理员");
            template.setLoginAccount("admin");
            template.setTelPhone("18208175722");
            //template.setDistrictCode("510104");
            //template.setDepartmentId("6zq4mrS5");
            list.add(template);

            String fileName = "用户信息导入模板.xlsx";
            new ExportExcel("用户信息", MemberTemplate.class).setDataList(list).write(response, fileName).dispose();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 用户登录
     * @param request 导入的附件
     * @return
     */
    @RequestMapping(value = "loginUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String loginUser(HttpServletRequest request,HttpServletResponse response){
        /*BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(ResponseCode.ERROR);
        baseResponse.setMessage("登录失败!");*/
        try {
            String loginAccount = request.getParameter("loginAccount");
            String loginPassword = request.getParameter("loginPassword");
            if(StringUtils.isEmpty(loginAccount) || StringUtils.isEmpty(loginPassword)){
                request.setAttribute("result","登录失败!");
                return "redirect:/login";
            }
            String result = memberRemoteService.loginUser(loginAccount,loginPassword);
            //request.setAttribute("result",result);
            //response.sendRedirect("/main/index");
            return "redirect:/main/index";
        }catch (Exception e){
            e.printStackTrace();
        }
        request.setAttribute("result","登录失败!");
        return "redirect:/login";
    }

}
