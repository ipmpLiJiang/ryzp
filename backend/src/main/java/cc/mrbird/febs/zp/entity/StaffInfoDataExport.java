package cc.mrbird.febs.zp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author viki
 * @since 2022-01-03
 */

@Excel("StaffInfoDataExport")
@Data
public class StaffInfoDataExport {

    /**
     * 姓名
     */
    @ExcelField(value = "姓名")
    private String ryname;

    /**
     * 性别
     */
    @ExcelField(value = "性别")
    private String sexName;


    /**
     * 出生年月
     */
    @ExcelField(value = "出生年月")
    private String csdats;

    /**
     * 身份证号
     */
    @ExcelField(value = "身份证号")
    private String idnumber;

    /**
     * 籍贯
     */
    @ExcelField(value = "籍贯")
    private String zhrjg;

    /**
     * 健康状态
     */
    @ExcelField(value = "健康状态")
    private String jkzt;

    /**
     * 身高cm
     */
    @ExcelField(value = "身高cm")
    private Integer zhrsg;

    /**
     * 体重kg
     */
    @ExcelField(value = "体重kg")
    private Integer zhrtz;

    /**
     * 血型
     */
    @ExcelField(value = "血型")
    private String zhrxxName;

    /**
     * 民族
     */
    @ExcelField(value = "民族")
    private String zhrmzName;


    /**
     * 是否服从调剂
     */
    @ExcelField(value = "是否服从调剂")
    private String isfcdjName;

    /**
     * 政治面貌
     */
    @ExcelField(value = "政治面貌")
    private String zzmmName;


    /**
     * 婚姻状态
     */
    @ExcelField(value = "婚姻状态")
    private String hyztName;


    /**
     * 子女个数
     */
    @ExcelField(value = "子女个数")
    private Integer zngs;

    /**
     * 第一志愿科室
     */
    @ExcelField(value = "第一志愿科室")
    private String zyks1;

    /**
     * 第二志愿科室
     */
    @ExcelField(value = "第二志愿科室")
    private String zyks2;

    /**
     * 最高学历
     */
    @ExcelField(value = "最高学历")
    private String zgxlName;

    /**
     * 最高学历学校
     */
    @ExcelField(value = "最高学历学校")
    private String zgxlxxName;

    /**
     * 外语水平
     */
    @ExcelField(value = "外语水平")
    private String wyspName;


    /**
     * 外语水平分数
     */
    @ExcelField(value = "外语水平分数")
    private Integer wyspfs;

    /**
     * 计算机水平
     */
    @ExcelField(value = "计算机水平")
    private String jsjspName;


    /**
     * 家庭住址
     */
    @ExcelField(value = "家庭住址")
    private String jtzz;

    /**
     * 户籍地址
     */
    @ExcelField(value = "户籍地址")
    private String hjdz;

    /**
     * 现居地址
     */
    @ExcelField(value = "现居地址")
    private String xjdz;

    /**
     * 紧急联系人
     */
    @ExcelField(value = "紧急联系人")
    private String jjlxr;

    /**
     * 联系人手机号码
     */
    @ExcelField(value = "联系人手机号码")
    private String lxrtel;

    /**
     * 邮箱
     */
    @ExcelField(value = "邮箱")
    private String email;


    /**
     * 手机号码
     */
    @ExcelField(value = "手机号码")
    private String tel;

    /**
     * 微信号
     */
    @TableField("wechatNo")
    @ExcelField(value = "微信号")
    private String wechatNo;

    /**
     * 是否医师资格证
     */
    @ExcelField(value = "是否医师资格证")
    private String isyszgzName;

    /**
     * 职业类型
     */
    @ExcelField(value = "职业类型")
    private String zylxName;

    /**
     * 毕业时是否取得住院医师规范化培训合格证
     */
    @ExcelField(value = "毕业时是否取得住院医师规范化培训合格证")
    private String isbysqdzyysName;

    /**
     * 硕士阶段是否四证合一
     */
    @ExcelField(value = "硕士阶段是否四证合一")
    private String isssjdszhyName;

    /**
     * 有无既往病史
     */
    @ExcelField(value = "有无既往病史")
    private String ywjwbs;

    /**
     * 自我介绍
     */
//    @ExcelField(value = "自我介绍")
//    private String zwjs;

    /**
     * 在校获奖情况(院级以上)
     */
//    @ExcelField(value = "在校获奖情况(院级以上)")
//    private String zxhjqk;


    /**
     * 家庭成员1
     */
    @ExcelField(value = "家庭成员1")
    private String fl1;

    /**
     * 家庭成员2
     */
    @ExcelField(value = "家庭成员2")
    private String fl2;

    /**
     * 家庭成员3
     */
    @ExcelField(value = "家庭成员3")
    private String fl3;

    /**
     * 家庭成员4
     */
    @ExcelField(value = "家庭成员4")
    private String fl4;

    /**
     * 家庭成员5
     */
    @ExcelField(value = "家庭成员5")
    private String fl5;

    /**
     * 教育经历1
     */
    @ExcelField(value = "教育经历1")
    private String ed1;

    /**
     * 教育经历2
     */
    @ExcelField(value = "教育经历2")
    private String ed2;

    /**
     * 教育经历3
     */
    @ExcelField(value = "教育经历3")
    private String ed3;

    /**
     * 教育经历4
     */
    @ExcelField(value = "教育经历4")
    private String ed4;

    /**
     * 教育经历5
     */
    @ExcelField(value = "教育经历5")
    private String ed5;

    /**
     * 工作经历1
     */
    @ExcelField(value = "工作经历1")
    private String wk1;

    /**
     * 工作经历2
     */
    @ExcelField(value = "工作经历2")
    private String wk2;

    /**
     * 工作经历3
     */
    @ExcelField(value = "工作经历3")
    private String wk3;

    /**
     * 工作经历4
     */
    @ExcelField(value = "工作经历4")
    private String wk4;

    /**
     * 工作经历5
     */
    @ExcelField(value = "工作经历5")
    private String wk5;

    /**
     * 项目信息1
     */
    @ExcelField(value = "项目信息1")
    private String pj1;

    /**
     * 项目信息2
     */
    @ExcelField(value = "项目信息2")
    private String pj2;

    /**
     * 项目信息3
     */
    @ExcelField(value = "项目信息")
    private String pj3;

    /**
     * 项目信息4
     */
    @ExcelField(value = "项目信息4")
    private String pj4;

    /**
     * 项目信息5
     */
    @ExcelField(value = "项目信息5")
    private String pj5;

    /**
     * 文章信息1
     */
    @ExcelField(value = "文章信息1")
    private String ey1;

    /**
     * 文章信息2
     */
    @ExcelField(value = "文章信息2")
    private String ey2;

    /**
     * 文章信息3
     */
    @ExcelField(value = "文章信息3")
    private String ey3;

    /**
     * 文章信息4
     */
    @ExcelField(value = "文章信息4")
    private String ey4;

    /**
     * 文章信息5
     */
    @ExcelField(value = "文章信息5")
    private String ey5;

    /**
     * 获奖情况1
     */
    @ExcelField(value = "获奖情况1")
    private String aw1;

    /**
     * 获奖情况2
     */
    @ExcelField(value = "获奖情况2")
    private String aw2;

    /**
     * 获奖情况3
     */
    @ExcelField(value = "获奖情况3")
    private String aw3;

    /**
     * 获奖情况4
     */
    @ExcelField(value = "获奖情况4")
    private String aw4;

    /**
     * 获奖情况5
     */
    @ExcelField(value = "获奖情况5")
    private String aw5;

}
