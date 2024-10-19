package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogBackInfoResp {
    //文章数量
    private Long articleCount;
    //文章浏览量排行
    private List<ArticleRankResp> articleRankVOList;
    //文章贡献统计
    private List<ArticleStatisticsResp> articleStatisticsList;
    //分类统计
    private List<CategoryResp> categoryVOList;
    //留言量
    private Long messageCount;
    //标签列表
    private List<TagOptionResp> tagVOList;
    //用户量
    private Long userCount;
    //一周访问量
    private List<UserViewResp> userViewVOList;
    //网站访问量
    private Integer viewCount;
}
