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
@ApiModel(value = "教师名称查询请求数据传输对象",description = "包含教师名称、页码、每页记录数")
public class TeacherQueryDTO implements Serializable {

    @NotNull
    @ApiModelProperty(name = "page",value = "页码",dataType = "Integer")
    private Integer page;

    @NotNull
    @ApiModelProperty(name = "limit",value = "每页记录数",dataType = "Integer")
    private Integer limit;

    @NotNull
    @ApiModelProperty(name = "teacherName",value = "教师名称",dataType = "String")
    private String teacherName;

}

