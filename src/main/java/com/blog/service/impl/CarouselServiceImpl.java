package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.entity.dao.Carousel;
import com.blog.entity.vo.response.CarouselResp;
import com.blog.mapper.CarouselMapper;
import com.blog.service.CarouselService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Felz
* @description 针对表【carousel】的数据库操作Service实现
* @createDate 2025-01-11 12:46:49
*/
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService{

    @Override
    public List<CarouselResp> getCarouselList() {
        return baseMapper.getCarouselList();
    }
}




