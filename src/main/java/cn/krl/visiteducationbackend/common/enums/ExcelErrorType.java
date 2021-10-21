package cn.krl.visiteducationbackend.common.enums;

public enum ExcelErrorType {

    NULL_TEACHERNAME(0,"教师名称为空"),
    NULL_SCHOOLNAME(1,"学校名称为空"),
    NULL_SUBJECTNAME(2,"专业名称为空"),
    NULL_SUBJECTCODE(3,"专业代码为空"),
    NULL_TASKNAME(4,"课题名称为空"),
    NULL_PROJECTNAME(5,"项目名称为空"),


    ILLEGAL_PROJECTNAME(11,"非法的项目名称"),
    ILLEGAL_PROJECTCODE(1111,"非法的项目代码");


    private Integer code;
    private String type;

    ExcelErrorType(Integer code,String type){
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
