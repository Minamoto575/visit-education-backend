package cn.krl.visiteducationbackend.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@Api("管理员传输对象")
public class AdminDTO {
    @NotNull
    @ApiModelProperty(name = "id",value = "管理员id",dataType = "Long")
    private Integer id;

    @NotNull
    @ApiModelProperty(name="name",value = "管理员名称",dataType = "String")
    private String name;

    @NotNull
    @ApiModelProperty(name="password",value = "密码",dataType = "String")
    private String password;
}
