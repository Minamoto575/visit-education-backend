package cn.krl.visiteducationbackend.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 管理员数据传输对象
 * @author kuang
 * @data 2021/10/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "管理员传输对象")
public class AdminDTO implements Serializable {

    @ApiModelProperty(name = "id", value = "管理员id", dataType = "Integer")
    private Integer id;

    @ApiModelProperty(name = "name", value = "管理员名称", dataType = "String")
    private String name;

    @ApiModelProperty(name = "password", value = "密码", dataType = "String")
    private String password;
}
