package cn.krl.visiteducationbackend.common.utils;

import cn.krl.visiteducationbackend.common.enums.ExcelErrorType;
import cn.krl.visiteducationbackend.common.enums.ProjectType;
import cn.krl.visiteducationbackend.dto.RecordDTO;
import com.alibaba.excel.exception.ExcelAnalysisException;

/**
 * Excel导入 检查工具类
 * @author kuang
 */
public class ExcelCheckUtil{

    private  static final char STAR = '*';


    /**
     * 整个的检查方法
     * @param recordDTO
     * @return
     * @throws Exception
     */
    public static RecordDTO check(RecordDTO recordDTO){

        String teacherName = recordDTO.getTeacherName();
        String projectName = recordDTO.getProjectName();
        String taskName = recordDTO.getTaskName();
        String subjectCode = recordDTO.getSubjectCode();

        /**
         * 检查是否存在空的数据项
         */
       checkEmptyOrNull(recordDTO);

        /**
         * 专业代码不合法
         */
        if(!codeIsLeage(subjectCode)){
            throw new ExcelAnalysisException(ExcelErrorType.ILLEGAL_SUBJECTCODE.getType() + ": " + subjectCode);
        }


        /**
         * 课题名称乱码
         */
//        if(isGB2312(taskName)){
//            throw new ExcelAnalysisStopException(ExcelErrorType.ILLEGAL_TASKNAME.getType()+ ": " + taskName);
//        }


        /**
         * 消除姓名中的空格
         */
        teacherName = teacherName.replace(" ","");


        /**
         * 青骨项目 导师姓名必须带*  一般项目不带*
         */
        char last = teacherName.charAt(teacherName.length()-1);
        if(ProjectType.QINGGU_PROJECT.getType().equals(projectName)){
            if(last!=STAR){
                StringBuffer sb = new StringBuffer(teacherName);
                sb.append(STAR);
                teacherName = sb.toString();
            }
        }else if(ProjectType.COMMON_PROJECT.getType().equals(projectName)){
            if(last==STAR){
                teacherName = teacherName.substring(0,teacherName.length()-1);
            }
        }else {
            throw new ExcelAnalysisException(ExcelErrorType.ILLEGAL_PROJECTNAME.getType());
        }

        recordDTO.setTeacherName(teacherName);
        return recordDTO;
    }


    /**
     * 检查recordDRTO是否存在空项
     * @param recordDTO
     * @throws Exception
     */
    public static void checkEmptyOrNull(RecordDTO recordDTO){
        String projectName = recordDTO.getProjectName();
        String teacherName = recordDTO.getTeacherName();
        String schoolName = recordDTO.getSchoolName();
        String subjectCode = recordDTO.getSubjectCode();
        String subjectName = recordDTO.getSubjectName();
        String taskName = recordDTO.getTaskName();

        if(isEmptyOrNull(projectName)){
            throw new ExcelAnalysisException(ExcelErrorType.NULL_PROJECTNAME.getType());
        }
        if(isEmptyOrNull(teacherName)){
            throw new ExcelAnalysisException(ExcelErrorType.NULL_TEACHERNAME.getType());
        }
        if(isEmptyOrNull(schoolName)){
            throw new ExcelAnalysisException(ExcelErrorType.NULL_SCHOOLNAME.getType());
        }
        if(isEmptyOrNull(subjectCode)){
            throw new ExcelAnalysisException(ExcelErrorType.NULL_SUBJECTCODE.getType());
        }
        if(isEmptyOrNull(subjectName)){
            throw new ExcelAnalysisException(ExcelErrorType.NULL_SUBJECTNAME.getType());
        }
        if(isEmptyOrNull(taskName)){
            throw new ExcelAnalysisException(ExcelErrorType.NULL_TASKNAME.getType());
        }
    }

    /**
     * 检查字符串是否为NULL或空
     * @param string
     * @return
     */
    public static boolean isEmptyOrNull(String string){
        return string==null||string.isEmpty();
    }


    /**
     * 检查字符串是否存在乱码
     * @param strName
     * @return
     */
//    public static boolean isMessyCode(String strName) {
//
//        Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
//        Matcher m = p.matcher(strName);
//        String after = m.replaceAll("");
//        String temp = after.replaceAll("\\p{P}", "");
//        char[] ch = temp.trim().toCharArray();
//
////        char[] indexs = {'①','②','③','④','⑤','⑥','⑦','⑧','⑨','⑩'};
//        int length = (ch != null) ? ch.length : 0;
//        for (int i = 0; i < length; i++) {
//            char c = ch[i];
//            //字母、数字、中文放行
//            if (!Character.isLetterOrDigit(c)) {
//                String str = "" + ch[i];
//                String regex = "[\u4e00-\u9fa5|'①'|'②'|'③'|'④'|'⑤'|'⑥'|'⑦'|'⑧'|'⑨'|'⑩'|'+'|'('|')" +
//                    "'|'-'|'（'|'）'|':'|'：'|'  '|'、'|'　'|'\"'|'“'|'”']+";
//                if (!str.matches(regex)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }


//    public static boolean isGB2312(String str){
//        char[] chars=str.toCharArray();
//        boolean isGB2312=false;
//        for(int i=0;i<chars.length;i++){
//            byte[] bytes=(""+chars[i]).getBytes();
//            if(bytes.length==2){
//                int[] ints=new int[2];
//                ints[0]=bytes[0]& 0xff;
//                ints[1]=bytes[1]& 0xff;
//                if(ints[0]>=0x81 && ints[0]<=0xFE && ints[1]>=0x40 && ints[1]<=0xFE){
//                    isGB2312=true;
//                    break;
//                }
//            }
//        }
//        return isGB2312;
//    }

    /**
     * 检查专业代码的位数是否合法 4、6、8
     * @param subjectCode
     * @return
     */
    private static boolean codeIsLeage(String subjectCode){
        subjectCode = subjectCode.trim();
        /**
         * subjectCode 可能是 111111/222222 这种类型 含多个code
         */
        String[] codes = subjectCode.split("[/|'\\\\'|;|；|,|，|、|' '|'  '|'   '|'\\n'|'\\r'|'\\t']");
        for(String code: codes){
            code = code.replace(" ","");
            int length = code.length();
            if(!(length==4||length==5||length==6||length==8)){
                return false;
            }
        }
        return true;
    }

}
