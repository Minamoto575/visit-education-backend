package cn.krl.visiteducationbackend.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Api("记录传输对象")
@Data
@AllArgsConstructor
public class RecordDTO {
    @NotNull
    @ApiModelProperty(name = "id",value = "总序号",dataType = "Long")
    private Long id;

    @NotNull
    @ApiModelProperty(name = "schoolName",value = "学校名称",dataType = "String")
    private String schoolName;

    @NotNull
    @ApiModelProperty(name = "subjectName",value = "学科专业名称",dataType = "String")
    private String subjectName;

    @NotNull
    @ApiModelProperty(name = "subjectCode",value = "学科",dataType = "Long")
    private Long subjectCode;

    @NotNull
    @ApiModelProperty(name = "teacherName",value = "教师名称",dataType = "String")
    private String teacherName;

    @NotNull
    @ApiModelProperty(name = "taskName",value = "课题名称",dataType = "String")
    private String taskName;

    @NotNull
    @ApiModelProperty(name = "projectName",value = "项目名称",dataType = "String")
    private String projectName;
}
