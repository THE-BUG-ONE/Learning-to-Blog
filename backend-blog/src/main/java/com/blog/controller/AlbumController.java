package com.blog.controller;

import com.framework.service.AlbumService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Resource
    AlbumService service;

    @GetMapping("/test")
    public String test() {
        return service.list().toString();
    }
}
