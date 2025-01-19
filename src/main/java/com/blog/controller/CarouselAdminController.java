package com.blog.controller;

import com.blog.annotation.SystemLog;
import com.blog.entity.vo.Result;
import com.blog.entity.vo.request.CarouselReq;
import com.blog.service.CarouselService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin/carousel")
public class CarouselAdminController {

    @Resource
    private CarouselService carouselService;

    @SystemLog(businessName = "添加轮播图")
    @PostMapping("/add")
    public Result<?> addCarousel(@RequestBody @Valid CarouselReq carouselReq) {
//        carouselService.addCarousel(carouselReq);
        return Result.success();
    }

    @SystemLog(businessName = "删除轮播图")
    @DeleteMapping("/delete")
    public Result<?> deleteCarousel(@RequestBody @Valid List<Integer> carouselIdList) {
//        carouselService.deleteCarousel(carouselIdList);
        return Result.success();
    }

    @SystemLog(businessName = "修改轮播图")
    @PostMapping("/update")
    public Result<?> updateCarousel(@RequestBody @Valid CarouselReq carouselReq) {
//        carouselService.updateCarousel(carouselReq);
        return Result.success();
    }

    @SystemLog(businessName = "上传轮播图片")
    @PostMapping("/upload")
    public Result<String> uploadCarousel(@RequestParam("file") MultipartFile file) {
//        String res = carouselService.uploadCarousel(file);
        return Result.result(null);
    }
}
