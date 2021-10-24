package cn.krl.visiteducationbackend.common.enums;

/**
 * @description 项目的枚举类型
 * @author kuang
 * @data 2021/10/24
 */
public enum ProjectType {
    QINGGU_PROJECT(0, "中西部青年骨干教师访学项目"),
    COMMON_PROJECT(1, "一般访问学者项目");

    private Integer code;
    private String type;

    ProjectType(Integer code, String type) {
        this.code = code;
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
