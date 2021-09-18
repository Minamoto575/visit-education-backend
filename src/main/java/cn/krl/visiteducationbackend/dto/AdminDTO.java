package cn.krl.visiteducationbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "管理员传输对象")
public class AdminDTO {
    @NotNull
    @ApiModelProperty(name = "id",value = "管理员id",dataType = "Integer")
    private Integer id;

    @NotNull
    @ApiModelProperty(name="name",value = "管理员名称",dataType = "String")
    private String name;

    @NotNull
    @ApiModelProperty(name="password",value = "密码",dataType = "String")
    private String password;
}
