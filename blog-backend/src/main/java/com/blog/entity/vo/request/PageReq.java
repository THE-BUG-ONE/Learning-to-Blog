package com.blog.entity.vo.request;

import com.blog.entity.vo.PageCalculate;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageReq extends PageCalculate {
    //当前页
    @NotNull
    private Integer current;
    //条数
    @NotNull
    private Integer size;
}
