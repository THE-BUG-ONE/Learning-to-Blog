package com.blog.controller;

import com.framework.Result;
import com.framework.annotation.SystemLog;
import com.framework.entity.vo.request.CarouselBackReq;
import com.framework.entity.vo.request.CarouselReq;
import com.framework.entity.vo.request.CarouselStatusReq;
import com.framework.entity.vo.response.CarouselBackResp;
import com.framework.entity.vo.response.PageResult;
import com.framework.service.CarouselService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
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
    public Result<?> addCarousel(@RequestBody @Validated CarouselReq carouselReq) {
        carouselService.addCarousel(carouselReq);
        return Result.success();
    }

    @SystemLog(businessName = "删除轮播图")
    @DeleteMapping("/delete")
    public Result<?> deleteCarousel(@RequestBody @Validated List<Integer> carouselIdList) {
        carouselService.deleteCarousel(carouselIdList);
        return Result.success();
    }

    @SystemLog(businessName = "查看轮播图列表")
    @GetMapping("/list")
    public Result<PageResult<CarouselBackResp>> getBackCarouselList(
            @Validated CarouselBackReq carouselBackReq) {
        PageResult<CarouselBackResp> res = carouselService.getBackCarouselList(carouselBackReq);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    @SystemLog(businessName = "修改轮播图状态")
    @PutMapping("/status")
    public Result<?> updateCarouselStatus(@RequestBody @Validated CarouselStatusReq carouselStatusReq) {
        carouselService.updateCarouselStatus(carouselStatusReq);
        return Result.success();
    }

    @SystemLog(businessName = "修改轮播图")
    @PostMapping("/update")
    public Result<?> updateCarousel(@RequestBody @Validated CarouselReq carouselReq) {
        carouselService.updateCarousel(carouselReq);
        return Result.success();
    }

    @SystemLog(businessName = "上传轮播图片")
    @PostMapping("/upload")
    public Result<String> uploadCarousel(@RequestParam("file") MultipartFile file) {
        String res = carouselService.uploadCarousel(file);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }
}
