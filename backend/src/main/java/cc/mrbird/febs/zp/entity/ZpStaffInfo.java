package cc.mrbird.febs.zp.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 *
 * </p>
 *
 * @author viki
 * @since 2022-08-23
 */

@Excel("zp_staff_info")
@Data
@Accessors(chain = true)
public class ZpStaffInfo implements Serializable, Comparable<ZpStaffInfo> {

    private static final long serialVersionUID = 1L;

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
    private transient String csdatFrom;
    private transient String csdatTo;

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
     * 民族
     */
    @ExcelField(value = "民族")
    private String zhrmz;

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
     * 婚姻状态
     */
    @ExcelField(value = "婚姻状态")
    private String hyzt;

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

    /**
     * by
     */
    @TableField("currencyField")
    @ExcelField(value = "by")
    private String currencyField;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    @ExcelField(value = "修改时间")
    private Date modifyTime;
    private transient String modifyTimeFrom;
    private transient String modifyTimeTo;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @ExcelField(value = "创建时间")
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;


    public static final String ID = "id";

    public static final String USERID = "userid";

    public static final String RYNAME = "ryname";

    public static final String SEX = "sex";

    public static final String CSDAT = "csdat";

    public static final String IDNUMBER = "idnumber";

    public static final String ZHRJG = "zhrjg";

    public static final String JKZT = "jkzt";

    public static final String ZHRSG = "zhrsg";

    public static final String ZHRTZ = "zhrtz";

    public static final String ZHRXX = "zhrxx";

    public static final String ZHRMZ = "zhrmz";

    public static final String ISFCDJ = "isfcdj";

    public static final String ZZMM = "zzmm";

    public static final String HYZT = "hyzt";

    public static final String ZNGS = "zngs";

    public static final String ZYKS1 = "zyks1";

    public static final String ZYKS2 = "zyks2";

    public static final String WYSP = "wysp";

    public static final String WYSPFS = "wyspfs";

    public static final String JSJSP = "jsjsp";

    public static final String TEL = "tel";

    public static final String JTZZ = "jtzz";

    public static final String XJDZ = "xjdz";

    public static final String JJLXR = "jjlxr";

    public static final String LXRTEL = "lxrtel";

    public static final String EMAIL = "email";

    public static final String INDW = "indw";

    public static final String WECHATNO = "wechatNo";

    public static final String ISYSZGZ = "isyszgz";

    public static final String ZYLX = "zylx";

    public static final String ISBYSQDZYYS = "isbysqdzyys";

    public static final String ISSSJDSZHY = "isssjdszhy";

    public static final String YWJWBS = "ywjwbs";

    public static final String ZWJS = "zwjs";

    public static final String ZXHJQK = "zxhjqk";

    public static final String ISSUB = "issub";

    public static final String COMMENTS = "comments";

    public static final String CURRENCYFIELD = "currencyField";

    public static final String MODIFY_TIME = "MODIFY_TIME";

    public static final String CREATE_TIME = "CREATE_TIME";

    @Override
    public int compareTo(ZpStaffInfo o) {
        if (this.getId() != null && o.getId() != null) {
            return this.getId().compareTo(o.getId());
        } else if (this.getId() != null) {
            return 1;
        } else {
            return 0;
        }
    }
}
