package com.framework.entity.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * (Carousel)表实体类
 *
 * @author makejava
 * @since 2024-04-16 15:31:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("t_carousel")
public class Carousel {
    @TableId
    //主键    
    private Integer id;
    //轮播图地址
    private String imgUrl;
    //是否显示 (0否 1是)
    private Integer status;
    //备注
    private String remark;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}

