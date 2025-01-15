package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.dao.Carousel;
import com.blog.entity.vo.response.CarouselResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Felz
* @description 针对表【carousel】的数据库操作Mapper
* @createDate 2025-01-11 12:46:49
* @Entity com.blog.entity.dao.Carousel
*/
@Mapper
public interface CarouselMapper extends BaseMapper<Carousel> {

    @Select("select * from carousel where status = 1")
    List<CarouselResp> getCarouselList();
}




