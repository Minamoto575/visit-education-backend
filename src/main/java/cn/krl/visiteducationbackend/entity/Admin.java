package cn.krl.visiteducationbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Entity
@ApiModel(value = "管理员实体")
@AllArgsConstructor
@NoArgsConstructor
@TableName("admin")
public class Admin {

    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty(name = "id",value = "管理员id",dataType = "Integer")
    private Integer id;

    @ApiModelProperty(name="type",value = "管理员类型 super为超级管理员、common为普通",dataType = "String")
    private String type;

    @ApiModelProperty(name="name",value = "管理员名称",dataType = "String")
    private String name;

    @ApiModelProperty(name="password",value = "密码",dataType = "String")
    private String password;

    @ApiModelProperty(name="salt",value = "盐",dataType = "String")
    private String salt;

    private Long gmtCreate;

    private Long gmtModified;
}
