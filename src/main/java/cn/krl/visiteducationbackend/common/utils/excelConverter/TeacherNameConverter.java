package cn.krl.visiteducationbackend.common.utils.excelConverter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

public class TeacherNameConverter implements Converter<String> {

    private  static final char STAR = '*';

    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 读的时候调用
     * @param cellData
     * @param excelContentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty,
                                    GlobalConfiguration globalConfiguration) throws Exception {

        String teacherName = cellData.getStringValue();

//
//        /**
//         * 消除姓名中的空格
//         */
//        teacherName = teacherName.replace(" ","");
//
//
//        /**
//         * 青骨项目 导师姓名必须带*  一般项目不带*
//         */
//        char last = teacherName.charAt(teacherName.length()-1);
//        if(ProjectType.QINGGU_PROJECT.getType().equals(projectName)){
//            if(last!=STAR){
//                StringBuffer sb = new StringBuffer(teacherName);
//                sb.append(STAR);
//                teacherName = sb.toString();
//            }
//        }else if(ProjectType.COMMON_PROJECT.getType().equals(projectName)){
//            if(last==STAR){
//                teacherName = teacherName.substring(0,teacherName.length()-1);
//            }
//        }else {
//            throw new Exception(ExcelErrorType.ILLEGAL_PROJECTNAME.getType());
//        }
        return teacherName;
    }

    /**
     * 写的时候调用
     * @param s
     * @param excelContentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public CellData convertToExcelData(String s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return null;
        //return new CellData(value);
    }
}
