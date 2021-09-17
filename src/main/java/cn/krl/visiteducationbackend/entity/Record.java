package cn.krl.visiteducationbackend.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Api(value = "记录实体")
@AllArgsConstructor
@NoArgsConstructor
@TableName("record")
public class Record {

    @ApiModelProperty(name = "id",value = "总序号",dataType = "Long")
    private Long id;

    @ApiModelProperty(name = "schoolName",value = "学校名称",dataType = "String")
    @Excel(name = "学校名称")
    private String schoolName;

    @ApiModelProperty(name = "subjectName",value = "学科专业名称",dataType = "String")
    @Excel(name = "学科专业名称")
    private String subjectName;

    @ApiModelProperty(name = "subjectCode",value = "学科专业代码",dataType = "Long")
    @Excel(name = "学科专业代码")
    private String subjectCode;

    @ApiModelProperty(name = "teacherName",value = "教师名称",dataType = "String")
    @Excel(name = "教师名称")
    private String teacherName;

    @ApiModelProperty(name = "taskName",value = "课题名称",dataType = "String")
    @Excel(name = "课题名称")
    private String taskName;

    @ApiModelProperty(name = "projectName",value = "项目名称",dataType = "String")
    @Excel(name = "项目名称")
    private String projectName;

    private Long gmtCreate;

    private Long gmtModified;

}
