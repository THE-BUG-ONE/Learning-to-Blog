package com.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.entity.dao.Carousel;
import com.framework.entity.vo.request.CarouselBackReq;
import com.framework.entity.vo.request.CarouselReq;
import com.framework.entity.vo.request.StatusReq;
import com.framework.entity.vo.response.CarouselBackResp;
import com.framework.entity.vo.response.CarouselResp;
import com.framework.entity.vo.response.PageResult;
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

