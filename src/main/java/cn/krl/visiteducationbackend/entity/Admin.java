package cn.krl.visiteducationbackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "管理员实体")
@AllArgsConstructor
@NoArgsConstructor
@TableName("admin")
public class Admin {

    @ApiModelProperty(name = "id",value = "管理员id",dataType = "Long")
    private Long id;

    @ApiModelProperty(name="name",value = "管理员名称",dataType = "String")
    private String name;

    @ApiModelProperty(name="password",value = "密码",dataType = "String")
    private String password;

    @ApiModelProperty(name="salt",value = "盐",dataType = "String")
    private String salt;

    private Long gmtCreate;

    private Long gmtModified;
}
