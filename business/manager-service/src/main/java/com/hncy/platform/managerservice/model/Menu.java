package com.hncy.platform.managerservice.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author chenwz
 * @date 2019-03-13 13:53
 */
@Data
@NoArgsConstructor
@ApiModel("菜单模型")
public class Menu implements Serializable{

    private static final long serialVersionUID = -3075343309318015279L;

    private String id;
    /**
     * 菜单名
     */
    private String name;

    /**
     * 父键
     */
    private String pid;

    /**
     *  排序
     */
    private Long sort;

    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 地址
     */
    private String path;


    private Integer isParent;

    private Integer isShow;
    /**
     *   删除字段
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 子菜单
     */
    private List<Menu> children;
}
