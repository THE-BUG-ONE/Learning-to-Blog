package com.blog.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.entity.dao.Carousel;
import com.blog.entity.vo.request.CarouselBackReq;
import com.blog.entity.vo.request.CarouselReq;
import com.blog.entity.vo.request.StatusReq;
import com.blog.entity.vo.response.CarouselBackResp;
import com.blog.entity.vo.response.CarouselResp;
import com.blog.entity.vo.response.PageResult;
import com.blog.mapper.CarouselMapper;
import com.blog.service.CarouselService;
import com.blog.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * (Carousel)表服务实现类
 *
 * @author makejava
 * @since 2024-04-16 15:31:44
 */
@Service("carouselService")
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService {

    @Override
    public List<CarouselResp> getCarouselList() {
        return BeanCopyUtils.copyBeanList(this.list(), CarouselResp.class);
    }

    @Override
    @Transactional
    public void addCarousel(CarouselReq carouselReq) {
        try {
            if (!this.save(BeanCopyUtils.copyBean(carouselReq, Carousel.class)
                    .setCreateTime(DateUtil.date())))
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("轮播图添加异常");
        }
    }

    @Override
    @Transactional
    public void deleteCarousel(List<Integer> carouselIdList) {
        try {
            if (!this.removeBatchByIds(carouselIdList))
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("轮播图删除异常");
        }
    }

    @Override
    public PageResult<CarouselBackResp> getBackCarouselList(CarouselBackReq carouselBackReq) {
        Integer page = carouselBackReq.getPage();
        Integer limit = carouselBackReq.getLimit();
        Integer status = carouselBackReq.getStatus();
        List<Carousel> carouselList = this.page(new Page<>(page, limit),
                this.lambdaQuery()
                        .eq(status != null, Carousel::getStatus, status)
                        .getWrapper()).getRecords();
        List<CarouselBackResp> carouselBackRespList =
                BeanCopyUtils.copyBeanList(carouselList, CarouselBackResp.class);
        return new PageResult<>(carouselBackRespList.size(), carouselBackRespList);
    }

    @Override
    @Transactional
    public void updateCarouselStatus(StatusReq statusReq) {
        Integer id = statusReq.getId();
        Integer status = statusReq.getStatus();
        try {
            if (id == null || !this.lambdaUpdate()
                    .eq(Carousel::getId, id)
                    .set(Carousel::getStatus, status)
                    .update())
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("轮播图状态修改异常");
        }
    }

    @Override
    @Transactional
    public void updateCarousel(CarouselReq carouselReq) {
        Integer id = carouselReq.getId();
        String imgUrl = carouselReq.getImgUrl();
        Integer status = carouselReq.getStatus();
        String remark = carouselReq.getRemark();
        try {
            if (id == null || !this.lambdaUpdate()
                    .eq(Carousel::getId, id)
                    .set(imgUrl != null, Carousel::getImgUrl, imgUrl)
                    .set(status != null, Carousel::getStatus, status)
                    .set(remark != null, Carousel::getRemark, remark)
                    .set(Carousel::getUpdateTime, DateUtil.date())
                    .update())
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("轮播图修改异常");
        }
    }

    //TODO 文件保存
    @Override
    @Transactional
    public String uploadCarousel(MultipartFile file) {
        return file.getOriginalFilename();
    }
}

