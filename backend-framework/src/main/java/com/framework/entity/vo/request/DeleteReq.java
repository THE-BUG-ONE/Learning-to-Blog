package com.framework.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteReq {
    //id列表
    private List<Integer> idList;
    //是否删除 (0否 1是)
    private Integer isDelete;
}
