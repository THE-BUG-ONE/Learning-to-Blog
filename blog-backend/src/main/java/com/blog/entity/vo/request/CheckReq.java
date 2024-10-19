package com.blog.entity.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckReq {
    //id集合
    @NotNull
    private List<Integer> idList;
    //是否通过 (0否 1是)
    @NotNull
    private Integer isCheck;
}
