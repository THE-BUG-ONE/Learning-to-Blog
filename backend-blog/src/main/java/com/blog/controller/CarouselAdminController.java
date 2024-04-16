package com.blog.controller;

import com.framework.Result;
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

    //接口：添加轮播图
    @PostMapping("/add")
    public Result<?> addCarousel(@RequestBody @Validated CarouselReq carouselReq) {
        carouselService.addCarousel(carouselReq);
        return Result.success();
    }

    //接口：删除轮播图
    @DeleteMapping("/delete")
    public Result<?> deleteCarousel(@RequestBody @Validated List<Integer> carouselIdList) {
        carouselService.deleteCarousel(carouselIdList);
        return Result.success();
    }

    //接口：查看轮播图列表
    @GetMapping("/list")
    public Result<PageResult<CarouselBackResp>> getBackCarouselList(
            @Validated CarouselBackReq carouselBackReq) {
        PageResult<CarouselBackResp> res = carouselService.getBackCarouselList(carouselBackReq);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    //接口：修改轮播图状态
    @PutMapping("/status")
    public Result<?> updateCarouselStatus(@RequestBody @Validated CarouselStatusReq carouselStatusReq) {
        carouselService.updateCarouselStatus(carouselStatusReq);
        return Result.success();
    }

    //接口：修改轮播图
    @PostMapping("/update")
    public Result<?> updateCarousel(@RequestBody @Validated CarouselReq carouselReq) {
        carouselService.updateCarousel(carouselReq);
        return Result.success();
    }

    //接口：上传轮播图片
    @PostMapping("/upload")
    public Result<String> uploadCarousel(@RequestParam("file") MultipartFile file) {
        String res = carouselService.uploadCarousel(file);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }
}
