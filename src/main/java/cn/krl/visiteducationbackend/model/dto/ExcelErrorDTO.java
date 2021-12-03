package cn.krl.visiteducationbackend.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author kuang
 * @description 存在问题的记录传输对象
 * @date 2021/10/27 17:38
 */
@ApiModel(value = "存在问题的记录传输对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelErrorDTO implements Serializable{

    @ApiModelProperty(name = "sheetName", value = "sheet名称", dataType = "String")
    private String sheetName;

    @ApiModelProperty(name = "rowIndex", value = "行号", dataType = "Integer")
    private Integer rowIndex;

    @ApiModelProperty(name = "errorType", value = "错误类型描述", dataType = "String")
    private String errorType;

    @ApiModelProperty(name = "schoolName", value = "学校名称", dataType = "String")
    private String schoolName;

    @ApiModelProperty(name = "subjectName", value = "学科专业名称", dataType = "String")
    private String subjectName;

    @ApiModelProperty(name = "subjectCode", value = "学科专业代码", dataType = "String")
    private String subjectCode;

    @ApiModelProperty(name = "teacherName", value = "教师名称", dataType = "String")
    private String teacherName;

    @ApiModelProperty(name = "taskName", value = "课题名称", dataType = "String")
    private String taskName;

    @ApiModelProperty(name = "projectName", value = "项目名称", dataType = "String")
    private String projectName;
}
