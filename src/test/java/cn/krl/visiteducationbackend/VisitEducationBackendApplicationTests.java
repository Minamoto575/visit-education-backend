package cn.krl.visiteducationbackend;

import cn.krl.visiteducationbackend.common.utils.ExcelCheckUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VisitEducationBackendApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void isMessy(){
        String s = "①  国家科技部973计划：类风湿关节炎发病的免疫学机制及其干预策略的研究（2010CB529100）"; //国家自然科学基金 瓜氨酸肽在RA发病机制中作用 30300317";
        System.out.println(ExcelCheckUtil.isMessyCode(s));
    }

}
