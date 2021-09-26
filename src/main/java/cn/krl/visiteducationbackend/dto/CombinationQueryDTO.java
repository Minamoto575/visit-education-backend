package cn.krl.visiteducationbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "组合查询请求数据传输对象",description = "包含项目、学校、学科名称、页码、每页记录数")
public class CombinationQueryDTO implements Serializable {

    @NotNull
    @ApiModelProperty(name = "page",value = "页码",dataType = "Integer")
    private Integer page;

    @NotNull
    @ApiModelProperty(name = "limit",value = "每页记录数",dataType = "Integer")
    private Integer limit;

    @NotNull
    @ApiModelProperty(name = "projectName",value = "项目名称",dataType = "String")
    private String projectName;

    @NotNull
    @ApiModelProperty(name = "schoolName",value = "学校名称",dataType = "String")
    private String schoolName;

    @NotNull
    @ApiModelProperty(name = "subjectName",value = "学科专业名称",dataType = "String")
    private String subjectName;
}
