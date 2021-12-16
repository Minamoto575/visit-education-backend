package cn.krl.visiteducationbackend.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kuang
 * @description 通知实体类
 * @date 2021/12/15  16:58
 */
@Data
@ApiModel(value = "通知实体")
@AllArgsConstructor
@NoArgsConstructor
@TableName("notice")
public class Notice {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(name = "id", value = "通知id", dataType = "Integer")
    private Integer id;

    @ApiModelProperty(name = "content", value = "通知内容", dataType = "String")
    private String content;

    private Long gmtCreate;
}
