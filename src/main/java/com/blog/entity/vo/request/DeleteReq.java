package com.blog.entity.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteReq {
    //id列表
    @NotNull
    private List<Integer> idList;
    //是否删除 (0否 1是)
    @NotNull
    private Integer isDelete;
}
