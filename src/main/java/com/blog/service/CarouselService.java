package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.dao.Carousel;
import com.blog.entity.vo.response.CarouselResp;

import java.util.List;

/**
* @author Felz
* @description 针对表【carousel】的数据库操作Service
* @createDate 2025-01-11 12:46:49
*/
public interface CarouselService extends IService<Carousel> {

    List<CarouselResp> getCarouselList();
}
