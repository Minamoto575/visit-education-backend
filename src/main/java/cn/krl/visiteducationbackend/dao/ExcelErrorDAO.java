package cn.krl.visiteducationbackend.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * excel导入检查的错误封装类
 * @author kuang
 * @date 2021/10/22  19:41
 */
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class ExcelErrorDAO {
    /**
     * 错误列表
     */
    private List<String> errorList = new ArrayList<>();


    public void addError(String error){
        errorList.add(error);
    }
}
