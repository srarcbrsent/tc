package com.ysu.zyw.tc.model.api.classifies;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class TmClassify implements Serializable {

    @ApiModelProperty(value = "分类id")
    private String id;

    @ApiModelProperty(value = "分类名")
    private String name;

    @ApiModelProperty(value = "父分类id")
    private String parentId;

    @ApiModelProperty(value = "继承关系")
    private String hierarchy;

    @ApiModelProperty(value = "分类层级")
    private Integer level;

    @ApiModelProperty(value = "父分类")
    private TmClassify parentClassify;

    @ApiModelProperty(value = "子分类列表")
    private List<TmClassify> subClassifies;

}
