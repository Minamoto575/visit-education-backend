package cn.krl.visiteducationbackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Api(value = "记录实体")
@AllArgsConstructor
@NoArgsConstructor
@TableName("record")
public class Record {

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

    //
//    private Long gmtCreate;
//
//    private Long gmtModified;

}
