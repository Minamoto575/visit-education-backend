package cn.krl.visiteducationbackend.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Api("记录传输对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordDTO {

    @NotNull
    @ApiModelProperty(name = "id",value = "id",dataType = "Long")
    private Long id;

    @NotNull
    @ApiModelProperty(name = "schoolName",value = "学校名称",dataType = "String")
    @Excel(name = "学校名称")
    private String schoolName;

    @NotNull
    @ApiModelProperty(name = "subjectName",value = "学科专业名称",dataType = "String")
    @Excel(name = "学科专业名称")
    private String subjectName;

    @NotNull
    @ApiModelProperty(name = "subjectCode",value = "学科专业代码",dataType = "Long")
    @Excel(name = "学科专业代码")
    private String subjectCode;

    @NotNull
    @ApiModelProperty(name = "teacherName",value = "教师名称",dataType = "String")
    @Excel(name = "教师姓名")
    private String teacherName;

    @NotNull
    @ApiModelProperty(name = "taskName",value = "课题名称",dataType = "String")
    @Excel(name = "课题名称")
    private String taskName;

    @NotNull
    @ApiModelProperty(name = "projectName",value = "项目名称",dataType = "String")
    @Excel(name = "项目名称")
    private String projectName;
}
