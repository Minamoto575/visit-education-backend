package cn.krl.visiteducationbackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "管理员实体")
@AllArgsConstructor
@TableName("admin")
public class Admin {

    @ApiModelProperty(name = "id",value = "管理员id",dataType = "Long")
    private Integer id;

    @NotNull
    @ApiModelProperty(name="name",value = "管理员名称",dataType = "String")
    private String name;

    @NotNull
    @ApiModelProperty(name="password",value = "密码",dataType = "String")
    private String password;
}
