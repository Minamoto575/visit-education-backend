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
@ApiModel(value = "查询请求数据传输对象",description = "包含项目、学校、学科名称")
public class RecordQueryDTO implements Serializable {

    @NotNull
    @ApiModelProperty(name = "projectName",value = "项目名称",dataType = "String")
    private String projectName;

    @NotNull
    @ApiModelProperty(name = "schoolName",value = "学校名称",dataType = "String")
    private String schoolName;

    @NotNull
    @ApiModelProperty(name = "subjectName",value = "学科专业名称",dataType = "String")
    private String subjectName;
}
