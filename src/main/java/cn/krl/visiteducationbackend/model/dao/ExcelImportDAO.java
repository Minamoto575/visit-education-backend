package cn.krl.visiteducationbackend.model.dao;

import cn.krl.visiteducationbackend.model.dto.ExcelErrorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * excel导入检查的信息封装类
 *
 * @author kuang
 * @date 2021/10/22 19:41
 */
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class ExcelImportDAO {
    /** 错误列表 */
    private List<ExcelErrorDTO> errorList = new ArrayList<>();

    /** 是否对excel进行检查 */
    private boolean doCheck;

    public void addError(ExcelErrorDTO error) {
        errorList.add(error);
    }

    public boolean doCheck() {
        return doCheck;
    }

    public void clearErrorList() {
        errorList.clear();
    }
}
