package com.framework.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckReq {
    //id集合
    private List<Integer> idList;
    //是否通过 (0否 1是)
    private Integer isCheck;
}
