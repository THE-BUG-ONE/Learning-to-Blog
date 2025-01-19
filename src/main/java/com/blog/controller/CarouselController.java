package com.blog.controller;

import com.blog.annotation.SystemLog;
import com.blog.entity.vo.Result;
import com.blog.entity.vo.response.CarouselResp;
import com.blog.service.CarouselService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carousel")
public class CarouselController {

    @Resource
    private CarouselService carouselService;

    @SystemLog(businessName = "获取轮播图列表")
    @GetMapping("/list")
    public Result<List<CarouselResp>> getCarouselList() {
        List<CarouselResp> res = carouselService.getCarouselList();
        return Result.result(res);
    }
}
