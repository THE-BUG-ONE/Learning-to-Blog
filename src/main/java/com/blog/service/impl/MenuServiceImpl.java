package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.entity.dao.Menu;
import com.blog.mapper.MenuMapper;
import com.blog.service.MenuService;
import org.springframework.stereotype.Service;

/**
* @author Felz
* @description 针对表【menu】的数据库操作Service实现
* @createDate 2024-11-02 14:15:13
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService{

}




