package cn.krl.visiteducationbackend.common.utils;

import cn.krl.visiteducationbackend.common.enums.ExcelErrorType;
import cn.krl.visiteducationbackend.common.enums.ProjectType;
import cn.krl.visiteducationbackend.dto.RecordDTO;

public class ExcelCheckUtil{

    private  static final char STAR = '*';

    public static RecordDTO check(RecordDTO recordDTO) throws Exception{

        checkEmptyOrNull(recordDTO);

        String teacherName = recordDTO.getTeacherName();
        String projectName = recordDTO.getProjectName();

        /**
         * 消除姓名中的空格
         */
        teacherName = teacherName.replace(" ","");


        /**
         * 青骨项目 导师姓名必须带*
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
            throw new Exception(ExcelErrorType.ILLEGAL_PROJECTNAME.getType());
        }


        recordDTO.setTeacherName(teacherName);
        return recordDTO;
    }


    /**
     * 检查recordDRTO是否存在空项
     * @param recordDTO
     * @throws Exception
     */
    public static void checkEmptyOrNull(RecordDTO recordDTO) throws Exception{
        String projectName = recordDTO.getProjectName();
        String teacherName = recordDTO.getTeacherName();
        String schoolName = recordDTO.getSchoolName();
        String subjectCode = recordDTO.getSubjectCode();
        String subjectName = recordDTO.getSubjectName();
        String taskName = recordDTO.getTaskName();

        if(isEmptyOrNull(projectName)){
            throw new Exception(ExcelErrorType.NULL_PROJECTNAME.getType());
        }
        if(isEmptyOrNull(teacherName)){
            throw new Exception(ExcelErrorType.NULL_TEACHERNAME.getType());
        }
        if(isEmptyOrNull(schoolName)){
            throw new Exception(ExcelErrorType.NULL_SCHOOLNAME.getType());
        }
        if(isEmptyOrNull(subjectCode)){
            throw new Exception(ExcelErrorType.NULL_SUBJECTCODE.getType());
        }
        if(isEmptyOrNull(subjectName)){
            throw new Exception(ExcelErrorType.NULL_SUBJECTNAME.getType());
        }
        if(isEmptyOrNull(taskName)){
            throw new Exception(ExcelErrorType.NULL_TASKNAME.getType());
        }

    }

    /**
     * 检查字符串是否为NULL或空
     * @param string
     * @return
     */
    public static boolean isEmptyOrNull(String string){
        return string.isEmpty()||string==null;
    }


}
