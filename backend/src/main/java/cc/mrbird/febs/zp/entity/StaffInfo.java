package cc.mrbird.febs.zp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
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

@Data
public class StaffInfo  {

    /**
     * Id
     */
    @ExcelField(value = "Id")
    private String id;

    /**
     * 创建人
     */
    @ExcelField(value = "创建人")
    private Long userid;

    /**
     * 姓名
     */
    @ExcelField(value = "姓名")
    private String ryname;

    /**
     * 性别
     */
    @ExcelField(value = "性别")
    private Integer sex;

    /**
     * 出生年月
     */
    @ExcelField(value = "出生年月")
    private Date csdat;

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
    private String zhrxx;

    /**
     * 血型Name
     */
    @ExcelField(value = "血型Name")
    private String zhrxxName;

    /**
     * 民族
     */
    @ExcelField(value = "民族")
    private String zhrmz;

    /**
     * 民族Name
     */
    @ExcelField(value = "民族Name")
    private String zhrmzName;

    /**
     * 是否服从调剂
     */
    @ExcelField(value = "是否服从调剂")
    private Integer isfcdj;

    /**
     * 政治面貌
     */
    @ExcelField(value = "政治面貌")
    private String zzmm;

    /**
     * 政治面貌Name
     */
    @ExcelField(value = "政治面貌Name")
    private String zzmmName;

    /**
     * 婚姻状态
     */
    @ExcelField(value = "婚姻状态")
    private String hyzt;

    /**
     * 婚姻状态Name
     */
    @ExcelField(value = "婚姻状态Name")
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
    private String zgxl;

    /**
     * 外语水平
     */
    @ExcelField(value = "外语水平")
    private String wysp;

    /**
     * 外语水平Name
     */
    @ExcelField(value = "外语水平Name")
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
    private String jsjsp;

    /**
     * 计算机水平Name
     */
    @ExcelField(value = "计算机水平Name")
    private String jsjspName;

    /**
     * 手机号码
     */
    @ExcelField(value = "手机号码")
    private String tel;

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
     * 现任单位
     */
    @ExcelField(value = "现任单位")
    private String indw;

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
    private Integer isyszgz;

    /**
     * 职业类型
     */
    @ExcelField(value = "职业类型")
    private String zylx;

    /**
     * 职业类型Name
     */
    @ExcelField(value = "职业类型Name")
    private String zylxName;

    /**
     * 毕业时是否取得住院医师规范化培训合格证
     */
    @ExcelField(value = "毕业时是否取得住院医师规范化培训合格证")
    private Integer isbysqdzyys;

    /**
     * 硕士阶段是否四证合一
     */
    @ExcelField(value = "硕士阶段是否四证合一")
    private Integer isssjdszhy;

    /**
     * 有无既往病史
     */
    @ExcelField(value = "有无既往病史")
    private String ywjwbs;

    /**
     * 自我介绍
     */
    @ExcelField(value = "自我介绍")
    private String zwjs;

    /**
     * 在校获奖情况(院级以上)
     */
    @ExcelField(value = "在校获奖情况(院级以上)")
    private String zxhjqk;

    /**
     * 是否提交
     */
    @ExcelField(value = "是否提交")
    private Integer issub;

    /**
     * 备注
     */
    @ExcelField(value = "备注")
    private String comments;


    List<StaffEducation> educations;

    List<StaffWork> works;

    List<StaffEssay> essays;

    List<StaffProject> projects;

    List<StaffFamily> familys;

    List<StaffAward> awards;
}
