package cn.krl.visiteducationbackend.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 记录数据传输对象
 * @author kuang
 * @data 2021/10/24
 */
@ApiModel(value = "记录传输对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordDTO implements Serializable {

    @ApiModelProperty(name = "id", value = "id", dataType = "Integer")
    private Integer id;

    @ApiModelProperty(name = "schoolName", value = "学校名称", dataType = "String")
    @ExcelProperty(value = "学校名称")
    private String schoolName;

    @ApiModelProperty(name = "subjectName", value = "学科专业名称", dataType = "String")
    @ExcelProperty(value = "学科专业名称")
    private String subjectName;

    @ApiModelProperty(name = "subjectCode", value = "学科专业代码", dataType = "String")
    @ExcelProperty(value = "学科专业代码")
    private String subjectCode;

    @ApiModelProperty(name = "teacherName", value = "教师名称", dataType = "String")
    @ExcelProperty(value = "教师姓名")
    private String teacherName;

    @ApiModelProperty(name = "taskName", value = "课题名称", dataType = "String")
    @ExcelProperty(value = "课题名称")
    private String taskName;

    @ApiModelProperty(name = "projectName", value = "项目名称", dataType = "String")
    @ExcelProperty(value = "项目名称")
    private String projectName;
}
