package cn.krl.visiteducationbackend.common.enums;

/**
 * @description excel导入可能出现的错误类型枚举
 * @author kuang
 * @data 2021/10/24
 */
public enum ExcelErrorType {
    NULL_TEACHERNAME(0, "教师名称为空"),
    NULL_SCHOOLNAME(1, "学校名称为空"),
    NULL_SUBJECTNAME(2, "专业名称为空"),
    NULL_SUBJECTCODE(3, "专业代码为空"),
    NULL_TASKNAME(4, "课题名称为空"),
    NULL_PROJECTNAME(5, "项目名称为空"),

    ILLEGAL_TASKNAME(10, "课题名称乱码"),
    ILLEGAL_PROJECTNAME(11, "项目名称不合法"),
    ILLEGAL_SUBJECTCODE(12, "专业代码不合法");

    private Integer code;
    private String type;

    ExcelErrorType(Integer code, String type) {
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
