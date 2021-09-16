package cn.krl.visiteducationbackend.dao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "查询请求数据传输对象",description = "包含项目、学校、学科名称")
public class RecordQueryDTO implements Serializable {

    @ApiModelProperty(name = "projectName",value = "项目名称",dataType = "String")
    private String projectName;

    @ApiModelProperty(name = "schoolName",value = "学校名称",dataType = "String")
    private String schoolName;

    @ApiModelProperty(name = "subjectName",value = "学科专业名称",dataType = "String")
    private String subjectName;
}
