package cn.krl.visiteducationbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
//@Entity
@Api(value = "记录实体")
@AllArgsConstructor
@NoArgsConstructor
@TableName("record")
public class Record {

    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty(name = "id",value = "总序号",dataType = "Integer")
    private Integer id;

    @ApiModelProperty(name = "schoolName",value = "学校名称",dataType = "String")
    private String schoolName;

    @ApiModelProperty(name = "subjectName",value = "学科专业名称",dataType = "String")
    private String subjectName;

    @ApiModelProperty(name = "subjectCode",value = "学科专业代码",dataType = "String")
    private String subjectCode;

    @ApiModelProperty(name = "teacherName",value = "教师名称",dataType = "String")
    private String teacherName;

    @ApiModelProperty(name = "taskName",value = "课题名称",dataType = "String")
    private String taskName;

    @ApiModelProperty(name = "projectName",value = "项目名称",dataType = "String")
    private String projectName;

    private Long gmtCreate;

    private Long gmtModified;

}
