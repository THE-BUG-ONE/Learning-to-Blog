package com.blog.controller;

import com.blog.entity.vo.Result;
import com.blog.annotation.SystemLog;
import com.blog.entity.vo.request.CarouselBackReq;
import com.blog.entity.vo.request.CarouselReq;
import com.blog.entity.vo.request.StatusReq;
import com.blog.entity.vo.response.CarouselBackResp;
import com.blog.entity.vo.response.PageResult;
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
        carouselService.addCarousel(carouselReq);
        return Result.success();
    }

    @SystemLog(businessName = "删除轮播图")
    @DeleteMapping("/delete")
    public Result<?> deleteCarousel(@RequestBody @Valid List<Integer> carouselIdList) {
        carouselService.deleteCarousel(carouselIdList);
        return Result.success();
    }

    @SystemLog(businessName = "查看轮播图列表")
    @GetMapping("/list")
    public Result<PageResult<CarouselBackResp>> getBackCarouselList(
            @Valid CarouselBackReq carouselBackReq) {
        PageResult<CarouselBackResp> res = carouselService.getBackCarouselList(carouselBackReq);
        return Result.result(res);
    }

    @SystemLog(businessName = "修改轮播图状态")
    @PutMapping("/status")
    public Result<?> updateCarouselStatus(@RequestBody @Valid StatusReq statusReq) {
        carouselService.updateCarouselStatus(statusReq);
        return Result.success();
    }

    @SystemLog(businessName = "修改轮播图")
    @PostMapping("/update")
    public Result<?> updateCarousel(@RequestBody @Valid CarouselReq carouselReq) {
        carouselService.updateCarousel(carouselReq);
        return Result.success();
    }

    @SystemLog(businessName = "上传轮播图片")
    @PostMapping("/upload")
    public Result<String> uploadCarousel(@RequestParam("file") MultipartFile file) {
        String res = carouselService.uploadCarousel(file);
        return Result.result(res);
    }
}
