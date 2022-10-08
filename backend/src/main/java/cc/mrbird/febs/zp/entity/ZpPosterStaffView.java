package cc.mrbird.febs.zp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author viki
 * @since 2022-01-08
 */

@Data
public class ZpPosterStaffView {

    /**
     * Id
     */
    @ExcelField(value = "Id")
    private String id;

    /**
     * 人员Id
     */
    @ExcelField(value = "人员Id")
    private String staffId;

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
     * 最高学历Name
     */
    @ExcelField(value = "最高学历Name")
    private String zgxlName;

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
     * 院校
     */
    @ExcelField(value = "院校")
    private String yxname;


    /**
     * 招聘信息ID
     */
    @ExcelField(value = "招聘信息ID")
    private String posterId;

    /**
     * 申请类型
     */
    @ExcelField(value = "申请类型")
    private Integer applystate;

    /**
     * by
     */
    @ExcelField(value = "by")
    private String currencyField;

    /**
     * 创建日期
     */
    @ExcelField(value = "创建日期")
    private Date crtdat;


    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date CREATE_TIME;

    /**
     * SendState
     */
    @ExcelField(value = "SendState")
    private Integer SendState;

}
