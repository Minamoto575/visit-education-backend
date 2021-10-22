package cn.krl.visiteducationbackend.common.enums;

/**
 * 管理员类型
 * @author kuang
 */
public enum AdminType {

    SUPER_ADMIN(0,"super"),
    COMMON_ADMIN(1,"common");

    private Integer code;
    private String type;

    AdminType(Integer code,String type){
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
