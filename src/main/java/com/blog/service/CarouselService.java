package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.dao.Carousel;
import com.blog.entity.vo.request.CarouselBackReq;
import com.blog.entity.vo.request.CarouselReq;
import com.blog.entity.vo.request.StatusReq;
import com.blog.entity.vo.response.CarouselBackResp;
import com.blog.entity.vo.response.CarouselResp;
import com.blog.entity.vo.response.PageResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * (Carousel)表服务接口
 *
 * @author makejava
 * @since 2024-04-16 15:31:44
 */
public interface CarouselService extends IService<Carousel> {

    List<CarouselResp> getCarouselList();

    void addCarousel(CarouselReq carouselReq);

    void deleteCarousel(List<Integer> carouselIdList);

    PageResult<CarouselBackResp> getBackCarouselList(CarouselBackReq carouselBackReq);

    void updateCarouselStatus(StatusReq statusReq);

    void updateCarousel(CarouselReq carouselReq);

    String uploadCarousel(MultipartFile file);
}

