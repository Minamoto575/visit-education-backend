package cn.krl.visiteducationbackend.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author kuang
 * @description
 * @date 2021/10/27  15:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "组合条件批量删除数据传输对象")
public class DeleteDTO implements Serializable {
    private String projectName;
    private String schoolName;
    private String subjectName;
}
