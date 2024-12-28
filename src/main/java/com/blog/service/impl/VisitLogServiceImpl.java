package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.entity.dao.VisitLog;
import com.blog.mapper.VisitLogMapper;
import com.blog.service.VisitLogService;
import org.springframework.stereotype.Service;

/**
 * (VisitLog)表服务实现类
 *
 * @author makejava
 * @since 2024-04-09 14:26:37
 */
@Service("visitLogService")
public class VisitLogServiceImpl extends ServiceImpl<VisitLogMapper, VisitLog> implements VisitLogService {

}

