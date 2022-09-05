package cc.mrbird.febs.zp.service.impl;

import cc.mrbird.febs.com.entity.ComType;
import cc.mrbird.febs.com.service.IComTypeService;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.zp.dao.ZpPosterStaffViewMapper;
import cc.mrbird.febs.zp.entity.*;
import cc.mrbird.febs.zp.service.*;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-01-08
 */
@Slf4j
@Service("IZpPosterStaffViewService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZpPosterStaffViewServiceImpl extends ServiceImpl<ZpPosterStaffViewMapper, ZpPosterStaffView> implements IZpPosterStaffViewService {

    @Autowired
    IZpStaffInfoService iZpStaffInfoService;

    @Autowired
    IZpStaffWorkService iZpStaffWorkService;

    @Autowired
    IZpStaffEducationService iZpStaffEducationService;

    @Autowired
    IZpStaffEssayService iZpStaffEssayService;

    @Autowired
    IZpStaffFamilyService iZpStaffFamilyService;

    @Autowired
    IZpStaffAwardService iZpStaffAwardService;

    @Autowired
    IZpStaffProjectService iZpStaffProjectService;

    @Autowired
    IComTypeService iComTypeService;

    @Override
    public IPage<ZpPosterStaffView> findZpPosterStaffViews(QueryRequest request, ZpPosterStaffView zpPosterStaffView) {
        try {
            Page<ZpPosterStaffView> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            IPage<ZpPosterStaffView> rpage = this.baseMapper.findZpPosterStaffView(page, zpPosterStaffView);
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (ZpPosterStaffView item : rpage.getRecords()) {
                if (item.getCsdat() != null) {
                    item.setCsdats(sdf.format(item.getCsdat()));
                }
            }
            return rpage;

        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public List<StaffInfoDataExport> excelData(String posterId, Integer state, List<String> idList) {
        List<StaffInfoDataExport> dataList = new ArrayList<>();
        List<ZpStaffInfo> staffList = iZpStaffInfoService.findStaffByPosterId(posterId, state, idList);

        if (staffList.size() > 0) {
            ComType qct = new ComType();
            qct.setIsDeletemark(1);
            List<ComType> typeList = iComTypeService.findComTypeList(qct);
            List<ComType> qCtlist = new ArrayList<>();

            List<ZpStaffEducation> edList = iZpStaffEducationService.findEducationByPosterId(posterId, state, idList);
            for (ZpStaffEducation item : edList) {
                if (StringUtils.isNotBlank(item.getXlxw())) {
                    qCtlist = typeList.stream().filter(s -> s.getCtType().equals(8) && s.getCtCode().equals(item.getXlxw())).collect(Collectors.toList());
                    if (qCtlist.size() > 0) {
                        item.setXlxwname(qCtlist.get(0).getCtName());
                    } else {
                        item.setXlxwname(item.getXlxw());
                    }
                }
                if (StringUtils.isNotBlank(item.getXklx())) {
                    qCtlist = typeList.stream().filter(s -> s.getCtType().equals(9) && s.getCtCode().equals(item.getXklx())).collect(Collectors.toList());
                    if (qCtlist.size() > 0) {
                        item.setXklxname(qCtlist.get(0).getCtName());
                    } else {
                        item.setXklxname(item.getXlxw());
                    }
                }
            }
            List<ZpStaffEssay> eyList = iZpStaffEssayService.findEssayByPosterId(posterId, state, idList);
            for (ZpStaffEssay item : eyList) {
                if (StringUtils.isNotBlank(item.getBrpm())) {
                    qCtlist = typeList.stream().filter(s -> s.getCtType().equals(11) && s.getCtCode().equals(item.getBrpm())).collect(Collectors.toList());
                    if (qCtlist.size() > 0) {
                        item.setBrpmname(qCtlist.get(0).getCtName());
                    } else {
                        item.setBrpmname(item.getBrpm());
                    }
                }
                if (StringUtils.isNotBlank(item.getKwjb())) {
                    qCtlist = typeList.stream().filter(s -> s.getCtType().equals(10) && s.getCtCode().equals(item.getKwjb())).collect(Collectors.toList());
                    if (qCtlist.size() > 0) {
                        item.setKwjbname(qCtlist.get(0).getCtName());
                    } else {
                        item.setKwjbname(item.getKwjb());
                    }
                }
                if (StringUtils.isNotBlank(item.getFbzt())) {
                    qCtlist = typeList.stream().filter(s -> s.getCtType().equals(12) && s.getCtCode().equals(item.getFbzt())).collect(Collectors.toList());
                    if (qCtlist.size() > 0) {
                        item.setFbztname(qCtlist.get(0).getCtName());
                    } else {
                        item.setFbztname(item.getFbzt());
                    }
                }
            }
            List<ZpStaffWork> wkList = iZpStaffWorkService.findWorkByPosterId(posterId, state, idList);
            for (ZpStaffWork item : wkList) {
                if (StringUtils.isNotBlank(item.getWkxl())) {
                    qCtlist = typeList.stream().filter(s -> s.getCtType().equals(8) && s.getCtCode().equals(item.getWkxl())).collect(Collectors.toList());
                    if (qCtlist.size() > 0) {
                        item.setWkxlname(qCtlist.get(0).getCtName());
                    } else {
                        item.setWkxlname(item.getWkxl());
                    }
                }
            }
            List<ZpStaffFamily> flList = iZpStaffFamilyService.findFamilyByPosterId(posterId, state, idList);
            for (ZpStaffFamily item : flList) {
                if (StringUtils.isNotBlank(item.getZzmm())) {
                    qCtlist = typeList.stream().filter(s -> s.getCtType().equals(6) && s.getCtCode().equals(item.getZzmm())).collect(Collectors.toList());
                    if (qCtlist.size() > 0) {
                        item.setZzmmname(qCtlist.get(0).getCtName());
                    } else {
                        item.setZzmmname(item.getZzmm());
                    }
                }
            }
            List<ZpStaffProject> pjList = iZpStaffProjectService.findProjectByPosterId(posterId, state, idList);
            List<ZpStaffAward> awList = iZpStaffAwardService.findAwardByPosterId(posterId, state, idList);


            List<ZpStaffEducation> edQuery = new ArrayList<>();
            List<ZpStaffEssay> eyQuery = new ArrayList<>();
            List<ZpStaffWork> wkQuery = new ArrayList<>();
            List<ZpStaffAward> awQuery = new ArrayList<>();
            List<ZpStaffProject> pjQuery = new ArrayList<>();
            List<ZpStaffFamily> flQuery = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            for (ZpStaffInfo item : staffList) {
                StaffInfoDataExport data = new StaffInfoDataExport();
                data.setRyname(item.getRyname()); // 姓名
                data.setSexName(item.getSex() == 0 ? "男" : "女"); // 性别
                if (item.getCsdat() != null) {
                    data.setCsdats(sdf.format(item.getCsdat())); // 出生年月
                }
                data.setIdnumber(item.getIdnumber()); // 身份证号
                data.setZhrjg(item.getZhrjg()); // 籍贯
                data.setJkzt(item.getJkzt()); // 健康状态
                data.setZhrsg(item.getZhrsg()); // 身高cm
                data.setZhrtz(item.getZhrtz()); // 体重kg
                if (StringUtils.isNotBlank(item.getZhrxx())) {
                    qCtlist = typeList.stream().filter(s -> s.getCtType().equals(1) && s.getCtCode().equals(item.getZhrxx())).collect(Collectors.toList());
                    if (qCtlist.size() > 0) {
                        data.setZhrxxName(qCtlist.get(0).getCtName()); // 血型
                    } else {
                        data.setZhrxxName(item.getZhrxx());
                    }
                }

                if (StringUtils.isNotBlank(item.getZhrmz())) {
                    qCtlist = typeList.stream().filter(s -> s.getCtType().equals(7) && s.getCtCode().equals(item.getZhrmz())).collect(Collectors.toList());
                    if (qCtlist.size() > 0) {
                        data.setZhrmzName(qCtlist.get(0).getCtName()); // 民族
                    } else {
                        data.setZhrmzName(item.getZhrmz());
                    }
                }
                data.setIsfcdjName(item.getIsfcdj() == 1 ? "是":"否" ); // 是否服从调剂

                if (StringUtils.isNotBlank(item.getZzmm())) {
                    qCtlist = typeList.stream().filter(s -> s.getCtType().equals(6) && s.getCtCode().equals(item.getZzmm())).collect(Collectors.toList());
                    if (qCtlist.size() > 0) {
                        data.setZzmmName(qCtlist.get(0).getCtName()); // 政治面貌
                    } else {
                        data.setZzmmName(item.getZhrmz());
                    }
                }

                if (StringUtils.isNotBlank(item.getHyzt())) {
                    qCtlist = typeList.stream().filter(s -> s.getCtType().equals(2) && s.getCtCode().equals(item.getHyzt())).collect(Collectors.toList());
                    if (qCtlist.size() > 0) {
                        data.setHyztName(qCtlist.get(0).getCtName()); // 婚姻状态
                    } else {
                        data.setHyztName(item.getZhrmz());
                    }
                }

                data.setZngs(item.getZngs()); // 子女个数
                data.setZyks1(item.getZyks1()); // 第一志愿科室
                data.setZyks2(item.getZyks2()); // 第二志愿科室

                // 最高学历
                if (StringUtils.isNotBlank(item.getZgxl())) {
                    qCtlist = typeList.stream().filter(s -> s.getCtType().equals(8) && s.getCtCode().equals(item.getZgxl())).collect(Collectors.toList());
                    if (qCtlist.size() > 0) {
                        data.setZgxlxxName(qCtlist.get(0).getCtName());
                    } else {
                        data.setZgxlxxName(item.getZgxl());
                    }
                }

                // 最高学历学校
                if (StringUtils.isNotBlank(item.getZgxl())) {
                    edQuery = edList.stream().filter(s -> s.getStaffId().equals(item.getId()) && s.getXlxw() != null && s.getXlxw().equals(item.getZgxl())).collect(Collectors.toList());
                    if (edQuery.size() > 0) {
                        data.setZgxlName(edQuery.get(0).getYxname());
                    }
                }

                if (StringUtils.isNotBlank(item.getWysp())) {
                    qCtlist = typeList.stream().filter(s -> s.getCtType().equals(3) && s.getCtCode().equals(item.getWysp())).collect(Collectors.toList());
                    if (qCtlist.size() > 0) {
                        data.setWyspName(qCtlist.get(0).getCtName()); // 外语水平
                    } else {
                        data.setWyspName(item.getWysp());
                    }
                }
                data.setWyspfs(item.getWyspfs()); // 外语水平分数

                if (StringUtils.isNotBlank(item.getJsjsp())) {
                    qCtlist = typeList.stream().filter(s -> s.getCtType().equals(4) && s.getCtCode().equals(item.getJsjsp())).collect(Collectors.toList());
                    if (qCtlist.size() > 0) {
                        data.setJsjspName(qCtlist.get(0).getCtName()); // 计算机水平
                    } else {
                        data.setJsjspName(item.getJsjsp());
                    }
                }
                data.setJtzz(item.getJtzz()); // 家庭住址
                data.setHjdz(item.getHjdz()); // 户籍地址
                data.setXjdz(item.getXjdz()); // 现居地址
                data.setJjlxr(item.getJjlxr()); // 紧急联系人
                data.setLxrtel(item.getLxrtel()); // 联系人手机号码
                data.setEmail(item.getEmail()); // 邮箱
                data.setTel(item.getTel()); // 手机号码
                data.setWechatNo(item.getWechatNo()); // 微信号
                data.setIsyszgzName(item.getIsyszgz() == 1 ? "是" : "否"); // 是否医师资格证

                if (StringUtils.isNotBlank(item.getZylx())) {
                    qCtlist = typeList.stream().filter(s -> s.getCtType().equals(5) && s.getCtCode().equals(item.getZylx())).collect(Collectors.toList());
                    if (qCtlist.size() > 0) {
                        data.setZylxName(qCtlist.get(0).getCtName()); // 职业类型
                    } else {
                        data.setZylxName(item.getZylx());
                    }
                }

                data.setIsbysqdzyysName(item.getIsbysqdzyys() == 1 ? "是" : "否");// 毕业时是否取得住院医师规范化培训合格证
                data.setIsssjdszhyName(item.getIsssjdszhy() == 1 ? "是" : "否");// 硕士阶段是否四证合一
                // 有无既往病史
                data.setYwjwbs(item.getYwjwbs());
                // 自我介绍

                // 家庭成员
                flQuery = flList.stream().filter(s -> s.getStaffId().equals(item.getId())).collect(Collectors.toList());
                data.setFl5(this.getFlName(flQuery, 4));
                data.setFl4(this.getFlName(flQuery, 3));
                data.setFl3(this.getFlName(flQuery, 2));
                data.setFl2(this.getFlName(flQuery, 1));
                data.setFl1(this.getFlName(flQuery, 0));
                // 教育经历
                edQuery = edList.stream().filter(s -> s.getStaffId().equals(item.getId())).collect(Collectors.toList());
                data.setEd5(this.getEdName(edQuery, 4));
                data.setEd4(this.getEdName(edQuery, 3));
                data.setEd3(this.getEdName(edQuery, 2));
                data.setEd2(this.getEdName(edQuery, 1));
                data.setEd1(this.getEdName(edQuery, 0));
                // 工作经历
                wkQuery = wkList.stream().filter(s -> s.getStaffId().equals(item.getId())).collect(Collectors.toList());
                data.setWk5(this.getWkName(wkQuery, 4));
                data.setWk4(this.getWkName(wkQuery, 3));
                data.setWk3(this.getWkName(wkQuery, 2));
                data.setWk2(this.getWkName(wkQuery, 1));
                data.setWk1(this.getWkName(wkQuery, 0));
                // 项目信息
                pjQuery = pjList.stream().filter(s -> s.getStaffId().equals(item.getId())).collect(Collectors.toList());
                data.setPj5(this.getPjName(pjQuery, 4));
                data.setPj4(this.getPjName(pjQuery, 3));
                data.setPj3(this.getPjName(pjQuery, 2));
                data.setPj2(this.getPjName(pjQuery, 1));
                data.setPj1(this.getPjName(pjQuery, 0));
                // 文章信息
                eyQuery = eyList.stream().filter(s -> s.getStaffId().equals(item.getId())).collect(Collectors.toList());
                data.setEy5(this.getEyName(eyQuery, 4));
                data.setEy4(this.getEyName(eyQuery, 3));
                data.setEy3(this.getEyName(eyQuery, 2));
                data.setEy2(this.getEyName(eyQuery, 1));
                data.setEy1(this.getEyName(eyQuery, 0));
                // 获奖情况
                awQuery = awList.stream().filter(s -> s.getStaffId().equals(item.getId())).collect(Collectors.toList());
                data.setAw5(this.getAwName(awQuery, 4));
                data.setAw4(this.getAwName(awQuery, 3));
                data.setAw3(this.getAwName(awQuery, 2));
                data.setAw2(this.getAwName(awQuery, 1));
                data.setAw1(this.getAwName(awQuery, 0));
                dataList.add(data);
            }
        }
        return dataList;
    }

    private String getFlName(List<ZpStaffFamily> list, int ng) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String value = "";
        String huanhang = "\n";
        if (list.size() > ng) {
            if (StrUtil.isNotBlank(list.get(ng).getWcname())) {
                value = "称       谓：" + list.get(ng).getWcname();
                if (StrUtil.isNotBlank(list.get(ng).getXmname())) {
                    value += huanhang + "姓       名：" + list.get(ng).getXmname();
                }
                if (StrUtil.isNotBlank(list.get(ng).getZzmmname())) {
                    value += huanhang + "政治面貌：" + list.get(ng).getZzmmname();
                }
                if (list.get(ng).getCsdat() != null) {
                    value += huanhang + "出生年月：" + sdf.format(list.get(ng).getCsdat());
                }
                if (StrUtil.isNotBlank(list.get(ng).getGzdwjzw())) {
                    value += huanhang + "工作单位及职务：" + list.get(ng).getGzdwjzw();
                }
            }
        }
        return value;
    }

    private String getEdName(List<ZpStaffEducation> list, int ng) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String value = "";
        String huanhang = "\n";
        if (list.size() > ng) {
            if (StrUtil.isNotBlank(list.get(ng).getYxname())) {
                value = "院       校：" + list.get(ng).getYxname();
                if (list.get(ng).getSrtdat() != null && list.get(ng).getEnddat() != null) {
                    value += huanhang + "教育时间：" + sdf.format(list.get(ng).getSrtdat()) + "-" + sdf.format(list.get(ng).getEnddat());
                } else if (list.get(ng).getSrtdat() != null) {
                    value += huanhang + "教育时间：" + sdf.format(list.get(ng).getSrtdat());
                } else if (list.get(ng).getEnddat() != null) {
                    value += huanhang + "教育时间：" + sdf.format(list.get(ng).getEnddat());
                }
                if (StrUtil.isNotBlank(list.get(ng).getXlxw())) {
                    value += huanhang + "学历：" + list.get(ng).getXlxwname();
                }
                if (StrUtil.isNotBlank(list.get(ng).getDsxx())) {
                    value += huanhang + "导师信息：" + list.get(ng).getDsxx();
                }
                if (StrUtil.isNotBlank(list.get(ng).getXkzy1())) {
                    value += huanhang + "学科专业1：" + list.get(ng).getXkzy1();
                }
                if (StrUtil.isNotBlank(list.get(ng).getXkzy2())) {
                    value += huanhang + "学科专业2：" + list.get(ng).getXkzy2();
                }
                if (StrUtil.isNotBlank(list.get(ng).getXklxname())) {
                    value += huanhang + "学科类型：" + list.get(ng).getXklxname();
                }
                if (StrUtil.isNotBlank(list.get(ng).getYjfx())) {
                    value += huanhang + "研究方向：" + list.get(ng).getYjfx();
                }
            }
        }
        return value;
    }

    private String getWkName(List<ZpStaffWork> list, int ng) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String value = "";
        String huanhang = "\n";
        if (list.size() > ng) {
            if (StrUtil.isNotBlank(list.get(ng).getWkdw())) {
                value = "工作单位：" + list.get(ng).getWkdw();
                if (list.get(ng).getSrtdat() != null && list.get(ng).getEnddat() != null) {
                    value += huanhang + "工作时间：" + sdf.format(list.get(ng).getSrtdat()) + "-" + sdf.format(list.get(ng).getEnddat());
                } else if (list.get(ng).getSrtdat() != null) {
                    value += huanhang + "工作时间：" + sdf.format(list.get(ng).getSrtdat());
                } else if (list.get(ng).getEnddat() != null) {
                    value += huanhang + "工作时间：" + sdf.format(list.get(ng).getEnddat());
                }
                if (StrUtil.isNotBlank(list.get(ng).getWkbm())) {
                    value += huanhang + "工作部门：" + list.get(ng).getWkbm();
                }
                if (StrUtil.isNotBlank(list.get(ng).getWkzw())) {
                    value += huanhang + "工作职务：" + list.get(ng).getWkzw();
                }
                if (StrUtil.isNotBlank(list.get(ng).getWkxl())) {
                    value += huanhang + "学历：" + list.get(ng).getWkxlname();
                }
            }
        }
        return value;
    }

    private String getPjName(List<ZpStaffProject> list, int ng) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String value = "";
        String huanhang = "\n";
        if (list.size() > ng) {
            if (StrUtil.isNotBlank(list.get(ng).getProjectname())) {
                value = "项目名称：" + list.get(ng).getProjectname();
                if (list.get(ng).getSrtdat() != null && list.get(ng).getEnddat() != null) {
                    value += huanhang + "项目时间：" + sdf.format(list.get(ng).getSrtdat()) + "-" + sdf.format(list.get(ng).getEnddat());
                } else if (list.get(ng).getSrtdat() != null) {
                    value += huanhang + "项目时间：" + sdf.format(list.get(ng).getSrtdat());
                } else if (list.get(ng).getEnddat() != null) {
                    value += huanhang + "项目时间：" + sdf.format(list.get(ng).getEnddat());
                }
                if (StrUtil.isNotBlank(list.get(ng).getXbly())) {
                    value += huanhang + "项目类别/来源：" + list.get(ng).getXbly();
                }
                if (StrUtil.isNotBlank(list.get(ng).getJf())) {
                    value += huanhang + "经费(万)：" + list.get(ng).getJf();
                }
                if (StrUtil.isNotBlank(list.get(ng).getBrpm())) {
                    value += huanhang + "本人排名：" + list.get(ng).getBrpm();
                }
            }
        }
        return value;
    }

    private String getEyName(List<ZpStaffEssay> list, int ng) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String value = "";
        String huanhang = "\n";
        if (list.size() > ng) {
            if (StrUtil.isNotBlank(list.get(ng).getWzname())) {
                value = "文章名称：" + list.get(ng).getWzname();
                if (StrUtil.isNotBlank(list.get(ng).getBrpm())) {
                    value += huanhang + "本人排名：" + list.get(ng).getBrpm();
                }
                if (StrUtil.isNotBlank(list.get(ng).getKwjb())) {
                    value += huanhang + "刊物级别：" + list.get(ng).getKwjb();
                }
                if (list.get(ng).getCbdat() != null) {
                    value += huanhang + "出版时间：" + sdf.format(list.get(ng).getCbdat());
                }
                if (StrUtil.isNotBlank(list.get(ng).getFbzt())) {
                    value += huanhang + "发布状态：" + list.get(ng).getFbzt();
                }
                if (StrUtil.isNotBlank(list.get(ng).getCbkw())) {
                    value += huanhang + "出版刊物：" + list.get(ng).getCbkw();
                }
                if (StrUtil.isNotBlank(list.get(ng).getCbkh())) {
                    value += huanhang + "出版刊号：" + list.get(ng).getCbkh();
                }
                if (StrUtil.isNotBlank(list.get(ng).getYxyz())) {
                    value += huanhang + "影响因子：" + list.get(ng).getYxyz();
                }
            }
        }
        return value;
    }

    private String getAwName(List<ZpStaffAward> list, int ng) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String value = "";
        String huanhang = "\n";
        if (list.size() > ng) {
            if (StrUtil.isNotBlank(list.get(ng).getJxname())) {
                value = "奖项名称：" + list.get(ng).getJxname();
                if (StrUtil.isNotBlank(list.get(ng).getMc())) {
                    value += huanhang + "名次：" + list.get(ng).getMc();
                }
                if (list.get(ng).getHjdat() != null) {
                    value += huanhang + "获奖时间：" + sdf.format(list.get(ng).getHjdat());
                }
                if (StrUtil.isNotBlank(list.get(ng).getRemark())) {
                    value += huanhang + "备注：" + list.get(ng).getRemark();
                }
            }
        }
        return value;
    }
}
