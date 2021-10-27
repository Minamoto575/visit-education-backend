package cn.krl.visiteducationbackend.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 记录查询数据传输对象
 * @author kuang
 * @data 2021/10/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "查询请求数据传输对象", description = "包含项目、学校、学科名称、教师名称、页码、每页记录数")
public class RecordQueryDTO implements Serializable {

    @ApiModelProperty(name = "page", value = "页码", dataType = "Integer")
    private Integer page;

    @ApiModelProperty(name = "limit", value = "每页记录数", dataType = "Integer")
    private Integer limit;

    @ApiModelProperty(name = "projectName", value = "项目名称", dataType = "String")
    private String projectName;

    @ApiModelProperty(name = "schoolName", value = "学校名称", dataType = "String")
    private String schoolName;

    @ApiModelProperty(name = "subjectName", value = "学科专业名称", dataType = "String")
    private String subjectName;

    @ApiModelProperty(name = "teacherName", value = "教师名称", dataType = "String")
    private String teacherName;
}
