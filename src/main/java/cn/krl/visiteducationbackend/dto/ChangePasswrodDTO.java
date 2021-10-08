package cn.krl.visiteducationbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "修改密码传输对象")
public class ChangePasswrodDTO implements Serializable {

    @ApiModelProperty(name = "id",value = "管理员id",dataType = "Integer")
    private Integer id;


    @ApiModelProperty(name="oldPassword",value = "旧密码",dataType = "String")
    private String oldPassword;


    @ApiModelProperty(name="newPassword",value = "旧密码",dataType = "String")
    private String newPassword;


}
