package cc.mrbird.febs.zp.service.impl;

import cc.mrbird.febs.com.entity.ComFile;
import cc.mrbird.febs.com.entity.ComType;
import cc.mrbird.febs.com.service.IComFileService;
import cc.mrbird.febs.com.service.IComTypeService;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.properties.FebsProperties;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.zp.entity.*;
import cc.mrbird.febs.zp.dao.ZpStaffInfoMapper;
import cc.mrbird.febs.zp.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-01-03
 */
@Slf4j
@Service("IZpStaffInfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZpStaffInfoServiceImpl extends ServiceImpl<ZpStaffInfoMapper, ZpStaffInfo> implements IZpStaffInfoService {

    @Autowired
    IZpStaffEducationService iZpStaffEducationService;

    @Autowired
    IZpStaffWorkService iZpStaffWorkService;

    @Autowired
    IZpStaffEssayService iZpStaffEssayService;

    @Autowired
    IComFileService iComFileService;

    @Autowired
    FebsProperties febsProperties;

    @Autowired
    IZpStaffApplyService iZpStaffApplyService;

    @Autowired
    IComTypeService iComTypeService;

    @Autowired
    IZpStaffFamilyService iZpStaffFamilyService;

    @Autowired
    IZpStaffProjectService iZpStaffProjectService;

    @Autowired
    IZpStaffAwardService iZpStaffAwardService;

    @Override
    public IPage<ZpStaffInfo> findZpStaffInfos(QueryRequest request, ZpStaffInfo zpStaffInfo) {
        try {
            LambdaQueryWrapper<ZpStaffInfo> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(ZpStaffInfo::getIsDeletemark, 1);//1是未删 0是已删
            queryWrapper.eq(ZpStaffInfo::getIssub,1);

            Page<ZpStaffInfo> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public ZpStaffInfo findZpStaffInfoById(String Id) {
        return this.getById(Id);
    }

    @Override
    public ZpStaffInfo findZpStaffInfoByUserId(Integer userId) {
        LambdaQueryWrapper<ZpStaffInfo> staffInfoWrapper = new LambdaQueryWrapper<>();
        staffInfoWrapper.eq(ZpStaffInfo::getUserid, userId);
        List<ZpStaffInfo> staffInfoList = this.list(staffInfoWrapper);
        if (staffInfoList.size() > 0) {
            return staffInfoList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public ZpStaffInfo findZpStaffInfoByUserId(long userId) {
        LambdaQueryWrapper<ZpStaffInfo> staffInfoWrapper = new LambdaQueryWrapper<>();
        staffInfoWrapper.eq(ZpStaffInfo::getUserid, userId);
        List<ZpStaffInfo> staffInfoList = this.list(staffInfoWrapper);
        if (staffInfoList.size() > 0) {
            return staffInfoList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public ZpStaffInfo findZpStaffInfoByXmAndIdNumber(String ryName, String idNumber, String email) {
        LambdaQueryWrapper<ZpStaffInfo> staffInfoWrapper = new LambdaQueryWrapper<>();
        staffInfoWrapper.eq(ZpStaffInfo::getRyname, ryName);
        staffInfoWrapper.eq(ZpStaffInfo::getIdnumber, idNumber);
        staffInfoWrapper.eq(ZpStaffInfo::getEmail, email);
        List<ZpStaffInfo> staffInfoList = this.list(staffInfoWrapper);
        if (staffInfoList.size() > 0) {
            return staffInfoList.get(0);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void updateStaffIdTel(String id, String idnumber, String tel){
        this.baseMapper.updateStaffIdTel(id,idnumber,tel);
    }

    @Override
    @Transactional
    public ZpStaffInfo initStaff(ZpStaffInfo initStaff) {
        Date thisDate = new Date();
        ZpStaffInfo zpStaffInfo = new ZpStaffInfo();
        zpStaffInfo.setId(UUID.randomUUID().toString());
        zpStaffInfo.setUserid(initStaff.getUserid());
        zpStaffInfo.setRyname(initStaff.getRyname());
        zpStaffInfo.setEmail(initStaff.getEmail());
        zpStaffInfo.setTel(initStaff.getTel());
        zpStaffInfo.setHyzt("0");
        zpStaffInfo.setSex(0);
        zpStaffInfo.setIssub(0);
        zpStaffInfo.setIsyszgz(0); // 是否医师资格证
        zpStaffInfo.setIsssjdszhy(0);
        zpStaffInfo.setIsbysqdzyys(0);
        zpStaffInfo.setIsfcdj(0);
        zpStaffInfo.setZngs(0);
        zpStaffInfo.setCreateTime(thisDate);
        zpStaffInfo.setIdnumber(initStaff.getIdnumber());
        this.save(zpStaffInfo);

//        this.iZpStaffEducationService.saveInitStaffEducation(zpStaffInfo, thisDate);

//        this.iZpStaffWorkService.saveInitStaffWork(zpStaffInfo, thisDate);

//        this.iZpStaffEssayService.saveInitStaffEssay(zpStaffInfo, thisDate);


        return zpStaffInfo;
    }

    @Override
    @Transactional
    public String createStaffMx(String staffId, String type) {
        ZpStaffInfo zpStaffInfo = this.getById(staffId);
        String id = null;
        if (zpStaffInfo != null) {
            Date thisDate = new Date();
            if (type.equals("E")) {
                ZpStaffEducation item = this.iZpStaffEducationService.saveInitStaffEducation(zpStaffInfo, thisDate);
                id = item.getId();
            }
            if (type.equals("W")) {
                ZpStaffWork item = this.iZpStaffWorkService.saveInitStaffWork(zpStaffInfo, thisDate);
                id = item.getId();
            }
            if (type.equals("Y")) {
                ZpStaffEssay item = this.iZpStaffEssayService.saveInitStaffEssay(zpStaffInfo, thisDate);
                id = item.getId();
            }
            if (type.equals("F")) {
                ZpStaffFamily item = this.iZpStaffFamilyService.saveInitStaffFamily(zpStaffInfo, thisDate);
                id = item.getId();
            }
            if (type.equals("A")) {
                ZpStaffAward item = this.iZpStaffAwardService.saveInitStaffAward(zpStaffInfo, thisDate);
                id = item.getId();
            }
            if (type.equals("P")) {
                ZpStaffProject item = this.iZpStaffProjectService.saveInitStaffProject(zpStaffInfo, thisDate);
                id = item.getId();
            }
            LambdaQueryWrapper<ZpStaffInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ZpStaffInfo::getId, zpStaffInfo.getId());
            ZpStaffInfo update = new ZpStaffInfo();
            update.setIssub(0);
            this.update(update, queryWrapper);
        }
        return id;

    }

    @Override
    @Transactional
    public void deleteStaffMx(String id, String type) {
        if (type.equals("E")) {
            this.iZpStaffEducationService.removeById(id);
        }
        if (type.equals("W")) {
            this.iZpStaffWorkService.removeById(id);
        }
        if (type.equals("Y")) {
            this.iZpStaffEssayService.removeById(id);
        }
        if (type.equals("F")) {
            this.iZpStaffFamilyService.removeById(id);
        }
        if (type.equals("A")) {
            this.iZpStaffAwardService.removeById(id);
        }
        if (type.equals("P")) {
            this.iZpStaffProjectService.removeById(id);
        }
        List<ComFile> fileList = iComFileService.findListComFile(id, null);

        if (fileList.size() > 0) {
            String filePath = febsProperties.getUploadPath(); // 上传后的路径
            for (ComFile comFile : fileList) {
                int count = this.iComFileService.deleteComFile(comFile.getId());
                if (count > 0) {
                    String fileUrl = filePath + comFile.getRefType() + "/" + comFile.getServerName();
                    deleteFile(fileUrl);
                }
            }
        }
    }

    @Override
    public ZpStaffInfo findByIdnumber(String idnumber) {
        List<ZpStaffInfo> list = baseMapper.selectList(new LambdaQueryWrapper<ZpStaffInfo>().eq(ZpStaffInfo::getIdnumber, idnumber));
        if(list.size() >0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public ZpStaffInfo findByTel(String tel) {
        List<ZpStaffInfo> list = baseMapper.selectList(new LambdaQueryWrapper<ZpStaffInfo>().eq(ZpStaffInfo::getTel, tel));
        if(list.size() >0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public StaffInfo getStaffInfo(ZpStaffInfo zpStaffInfo) {
        ComType qct = new ComType();
        qct.setIsDeletemark(1);
        List<ComType> typeList = iComTypeService.findComTypeList(qct);
        List<ComType> qCtlist = new ArrayList<>();
        StaffInfo staffInfo = new StaffInfo();
//        LambdaQueryWrapper<ZpStaffInfo> staffInfoWrapper = new LambdaQueryWrapper<>();
//        staffInfoWrapper.eq(ZpStaffInfo::getUserid, user.getUserId());
//        List<ZpStaffInfo> staffInfoList = this.list(staffInfoWrapper);

        List<ZpStaffEducation> educationList = new ArrayList<>();
        List<ZpStaffWork> workList = new ArrayList<>();
        List<ZpStaffEssay> essayList = new ArrayList<>();
        List<ZpStaffFamily> familyList = new ArrayList<>();
        List<ZpStaffProject> projectList = new ArrayList<>();
        List<ZpStaffAward> awardList = new ArrayList<>();
//        if (staffInfoList.size() > 0) {
//            ZpStaffInfo zpStaffInfo = staffInfoList.get(0);
        staffInfo.setId(zpStaffInfo.getId());
        staffInfo.setRyname(zpStaffInfo.getRyname()); //姓名
        staffInfo.setEmail(zpStaffInfo.getEmail()); // 邮箱
        staffInfo.setSex(zpStaffInfo.getSex()); //性别
        staffInfo.setCsdat(zpStaffInfo.getCsdat()); //出生
        if (zpStaffInfo.getCsdat() != null) {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            staffInfo.setCsdats(sdf.format(zpStaffInfo.getCsdat()));
        }
        staffInfo.setIdnumber(zpStaffInfo.getIdnumber());
        staffInfo.setZhrjg(zpStaffInfo.getZhrjg()); // 籍贯
        staffInfo.setJkzt(zpStaffInfo.getJkzt()); //健康状态
        staffInfo.setZhrsg(zpStaffInfo.getZhrsg()); // 身高
        staffInfo.setZhrtz(zpStaffInfo.getZhrtz()); // 体重
        staffInfo.setZhrxx(zpStaffInfo.getZhrxx()); // 血型
        if (StringUtils.isNotBlank(zpStaffInfo.getZhrxx())) {
            qCtlist = typeList.stream().filter(s -> s.getCtType().equals(1) && s.getCtCode().equals(zpStaffInfo.getZhrxx())).collect(Collectors.toList());
            if (qCtlist.size() > 0) {
                staffInfo.setZhrxxName(qCtlist.get(0).getCtName());
            } else {
                staffInfo.setZhrxxName(zpStaffInfo.getZhrxx());
            }
        }
        staffInfo.setZhrmz(zpStaffInfo.getZhrmz()); // 民族
        if (StringUtils.isNotBlank(zpStaffInfo.getZhrmz())) {
            qCtlist = typeList.stream().filter(s -> s.getCtType().equals(7) && s.getCtCode().equals(zpStaffInfo.getZhrmz())).collect(Collectors.toList());
            if (qCtlist.size() > 0) {
                staffInfo.setZhrmzName(qCtlist.get(0).getCtName());
            } else {
                staffInfo.setZhrmzName(zpStaffInfo.getZhrmz());
            }
        }
        staffInfo.setIsfcdj(zpStaffInfo.getIsfcdj());// 是否服从调剂
        staffInfo.setZzmm(zpStaffInfo.getZzmm()); // 政治面貌
        if (StringUtils.isNotBlank(zpStaffInfo.getZzmm())) {
            qCtlist = typeList.stream().filter(s -> s.getCtType().equals(6) && s.getCtCode().equals(zpStaffInfo.getZzmm())).collect(Collectors.toList());
            if (qCtlist.size() > 0) {
                staffInfo.setZzmmName(qCtlist.get(0).getCtName());
            } else {
                staffInfo.setZzmmName(zpStaffInfo.getZzmm());
            }
        }
        staffInfo.setHyzt(zpStaffInfo.getHyzt()); // 婚姻状态
        if (StringUtils.isNotBlank(zpStaffInfo.getHyzt())) {
            qCtlist = typeList.stream().filter(s -> s.getCtType().equals(2) && s.getCtCode().equals(zpStaffInfo.getHyzt())).collect(Collectors.toList());
            if (qCtlist.size() > 0) {
                staffInfo.setHyztName(qCtlist.get(0).getCtName());
            } else {
                staffInfo.setHyztName(zpStaffInfo.getHyzt());
            }
        }
        staffInfo.setZngs(zpStaffInfo.getZngs()); //子女个数
        staffInfo.setZyks1(zpStaffInfo.getZyks1());  // 第一志愿科室
        staffInfo.setZyks2(zpStaffInfo.getZyks2());  // 第二志愿科室
        staffInfo.setZgxl(zpStaffInfo.getZgxl()); //最高学历
        if (StringUtils.isNotBlank(zpStaffInfo.getZgxl())) {
            qCtlist = typeList.stream().filter(s -> s.getCtType().equals(8) && s.getCtCode().equals(zpStaffInfo.getZgxl())).collect(Collectors.toList());
            if (qCtlist.size() > 0) {
                staffInfo.setZgxlname(qCtlist.get(0).getCtName());
            } else {
                staffInfo.setZgxlname(zpStaffInfo.getZgxl());
            }
        }
        staffInfo.setWysp(zpStaffInfo.getWysp()); // 外语水平
        if (StringUtils.isNotBlank(zpStaffInfo.getWysp())) {
            qCtlist = typeList.stream().filter(s -> s.getCtType().equals(3) && s.getCtCode().equals(zpStaffInfo.getWysp())).collect(Collectors.toList());
            if (qCtlist.size() > 0) {
                staffInfo.setWyspName(qCtlist.get(0).getCtName());
            } else {
                staffInfo.setWyspName(zpStaffInfo.getWysp());
            }
        }
        staffInfo.setWyspfs(zpStaffInfo.getWyspfs()); // 外语水平分数
        staffInfo.setJsjsp(zpStaffInfo.getJsjsp()); // 计算机水平
        if (StringUtils.isNotBlank(zpStaffInfo.getJsjsp())) {
            qCtlist = typeList.stream().filter(s -> s.getCtType().equals(4) && s.getCtCode().equals(zpStaffInfo.getJsjsp())).collect(Collectors.toList());
            if (qCtlist.size() > 0) {
                staffInfo.setJsjspName(qCtlist.get(0).getCtName());
            } else {
                staffInfo.setJsjspName(zpStaffInfo.getJsjsp());
            }
        }
        staffInfo.setTel(zpStaffInfo.getTel()); //手机联系电话
        staffInfo.setWechatNo(zpStaffInfo.getWechatNo()); //微信号码
        staffInfo.setJtzz(zpStaffInfo.getJtzz()); //家庭住址
        staffInfo.setHjdz(zpStaffInfo.getHjdz()); //户籍地址
        staffInfo.setXjdz(zpStaffInfo.getXjdz()); //现居地址
        staffInfo.setJjlxr(zpStaffInfo.getJjlxr()); //紧急联系人
        staffInfo.setLxrtel(zpStaffInfo.getLxrtel()); //联系人电话
        staffInfo.setIsyszgz(zpStaffInfo.getIsyszgz()); // 是否医师资格证
        staffInfo.setZylx(zpStaffInfo.getZylx()); //职业类型
        if (StringUtils.isNotBlank(zpStaffInfo.getZylx())) {
            qCtlist = typeList.stream().filter(s -> s.getCtType().equals(5) && s.getCtCode().equals(zpStaffInfo.getZylx())).collect(Collectors.toList());
            if (qCtlist.size() > 0) {
                staffInfo.setZylxName(qCtlist.get(0).getCtName());
            } else {
                staffInfo.setZylxName(zpStaffInfo.getZylx());
            }
        }
        staffInfo.setIsbysqdzyys(zpStaffInfo.getIsbysqdzyys()); //毕业时是否取得住院医师规范化培训合格证
        staffInfo.setIsssjdszhy(zpStaffInfo.getIsssjdszhy()); //硕士阶段是否四证合一
        staffInfo.setYwjwbs(zpStaffInfo.getYwjwbs()); //有无既往病史
        staffInfo.setZwjs(zpStaffInfo.getZwjs()); //自我介绍
        staffInfo.setZxhjqk(zpStaffInfo.getZxhjqk()); // 在校获奖情况(院级以上)
        staffInfo.setComments(zpStaffInfo.getComments()); //备注
//        staffInfo.setSblx(zpStaffInfo.getSblx()); //申报类型


        LambdaQueryWrapper<ZpStaffEducation> staffEducationWrapper = new LambdaQueryWrapper<>();
        staffEducationWrapper.eq(ZpStaffEducation::getStaffId, zpStaffInfo.getId());
        educationList = this.iZpStaffEducationService.list(staffEducationWrapper);

        for (ZpStaffEducation item : educationList) {
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

        LambdaQueryWrapper<ZpStaffWork> staffWorkWrapper = new LambdaQueryWrapper<>();
        staffWorkWrapper.eq(ZpStaffWork::getStaffId, zpStaffInfo.getId());
        workList = this.iZpStaffWorkService.list(staffWorkWrapper);

        for (ZpStaffWork item : workList) {
            if (StringUtils.isNotBlank(item.getWkxl())) {
                qCtlist = typeList.stream().filter(s -> s.getCtType().equals(8) && s.getCtCode().equals(item.getWkxl())).collect(Collectors.toList());
                if (qCtlist.size() > 0) {
                    item.setWkxlname(qCtlist.get(0).getCtName());
                } else {
                    item.setWkxlname(item.getWkxl());
                }
            }
        }

        LambdaQueryWrapper<ZpStaffEssay> staffEssayWrapper = new LambdaQueryWrapper<>();
        staffEssayWrapper.eq(ZpStaffEssay::getStaffId, zpStaffInfo.getId());
        essayList = this.iZpStaffEssayService.list(staffEssayWrapper);

        for (ZpStaffEssay item : essayList) {
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

        LambdaQueryWrapper<ZpStaffFamily> staffFamilyWrapper = new LambdaQueryWrapper<>();
        staffFamilyWrapper.eq(ZpStaffFamily::getStaffId, zpStaffInfo.getId());
        familyList = this.iZpStaffFamilyService.list(staffFamilyWrapper);

        for (ZpStaffFamily item : familyList) {
            if (StringUtils.isNotBlank(item.getZzmm())) {
                qCtlist = typeList.stream().filter(s -> s.getCtType().equals(6) && s.getCtCode().equals(item.getZzmm())).collect(Collectors.toList());
                if (qCtlist.size() > 0) {
                    item.setZzmmname(qCtlist.get(0).getCtName());
                } else {
                    item.setZzmmname(item.getZzmm());
                }
            }
        }

        LambdaQueryWrapper<ZpStaffProject> staffProjectWrapper = new LambdaQueryWrapper<>();
        staffProjectWrapper.eq(ZpStaffProject::getStaffId, zpStaffInfo.getId());
        projectList = this.iZpStaffProjectService.list(staffProjectWrapper);

        LambdaQueryWrapper<ZpStaffAward> staffAwardWrapper = new LambdaQueryWrapper<>();
        staffAwardWrapper.eq(ZpStaffAward::getStaffId, zpStaffInfo.getId());
        awardList = this.iZpStaffAwardService.list(staffAwardWrapper);

//        } else {
//            staffInfo.setId(UUID.randomUUID().toString());
//            staffInfo.setRyname(user.getXmname());
//            staffInfo.setEmail(user.getUsername());
//        }

//        this.getDefMx(educationList, workList, essayList);
        if (educationList.size() > 0) {
            educationList = educationList.stream().sorted(Comparator.comparing(ZpStaffEducation::getCreateTime)).collect(Collectors.toList());
        }
        if (workList.size() > 0) {
            workList = workList.stream().sorted(Comparator.comparing(ZpStaffWork::getCreateTime)).collect(Collectors.toList());
        }
        if (essayList.size() > 0) {
            essayList = essayList.stream().sorted(Comparator.comparing(ZpStaffEssay::getCreateTime)).collect(Collectors.toList());
        }
        if (familyList.size() > 0) {
            familyList = familyList.stream().sorted(Comparator.comparing(ZpStaffFamily::getCreateTime)).collect(Collectors.toList());
        }
        if (projectList.size() > 0) {
            projectList = projectList.stream().sorted(Comparator.comparing(ZpStaffProject::getCreateTime)).collect(Collectors.toList());
        }
        if (awardList.size() > 0) {
            awardList = awardList.stream().sorted(Comparator.comparing(ZpStaffAward::getCreateTime)).collect(Collectors.toList());
        }
        staffInfo.setEducations(this.getStaffEducation(educationList));
        staffInfo.setWorks(this.getStaffWork(workList));
        staffInfo.setEssays(this.getStaffEssay(essayList));
        staffInfo.setFamilys(this.getStaffFamily(familyList));
        staffInfo.setProjects(this.getStaffProject(projectList));
        staffInfo.setAwards(this.getStaffAward(awardList));
        return staffInfo;
    }

    @Override
    public List<StaffEducation> getEducationList(String staffId) {
        LambdaQueryWrapper<ZpStaffEducation> staffEducationWrapper = new LambdaQueryWrapper<>();
        staffEducationWrapper.eq(ZpStaffEducation::getStaffId, staffId);
        List<ZpStaffEducation> educationList = this.iZpStaffEducationService.list(staffEducationWrapper);
        if (educationList.size() > 0) {
            educationList = educationList.stream().sorted(Comparator.comparing(ZpStaffEducation::getCreateTime)).collect(Collectors.toList());
        }
        return this.getStaffEducation(educationList);
    }

    @Override
    public List<StaffWork> getWorkList(String staffId) {
        LambdaQueryWrapper<ZpStaffWork> staffWorkWrapper = new LambdaQueryWrapper<>();
        staffWorkWrapper.eq(ZpStaffWork::getStaffId, staffId);
        List<ZpStaffWork> workList = this.iZpStaffWorkService.list(staffWorkWrapper);
        if (workList.size() > 0) {
            workList = workList.stream().sorted(Comparator.comparing(ZpStaffWork::getCreateTime)).collect(Collectors.toList());
        }
        return this.getStaffWork(workList);
    }


    @Override
    public List<StaffEssay> getEssayList(String staffId) {
        LambdaQueryWrapper<ZpStaffEssay> staffEssayWrapper = new LambdaQueryWrapper<>();
        staffEssayWrapper.eq(ZpStaffEssay::getStaffId, staffId);
        List<ZpStaffEssay> essayList = this.iZpStaffEssayService.list(staffEssayWrapper);
        if (essayList.size() > 0) {
            essayList = essayList.stream().sorted(Comparator.comparing(ZpStaffEssay::getCreateTime)).collect(Collectors.toList());
        }
        return this.getStaffEssay(essayList);
    }

    void getDefMx(List<ZpStaffEducation> educationList, List<ZpStaffWork> workList, List<ZpStaffEssay> essayList) {
        if (educationList.size() == 0) {
            ZpStaffEducation education = new ZpStaffEducation();
            education.setId(UUID.randomUUID().toString());
            educationList.add(education);
        }

        if (workList.size() == 0) {
            ZpStaffWork work = new ZpStaffWork();
            work.setId(UUID.randomUUID().toString());
            workList.add(work);
        }

        if (essayList.size() == 0) {
            ZpStaffEssay essay = new ZpStaffEssay();
            essay.setId(UUID.randomUUID().toString());
            essayList.add(essay);
        }
    }

    @Override
    public List<StaffFamily> getFamilyList(String staffId) {
        LambdaQueryWrapper<ZpStaffFamily> staffFamilyWrapper = new LambdaQueryWrapper<>();
        staffFamilyWrapper.eq(ZpStaffFamily::getStaffId, staffId);
        List<ZpStaffFamily> familyList = this.iZpStaffFamilyService.list(staffFamilyWrapper);
        if (familyList.size() > 0) {
            familyList = familyList.stream().sorted(Comparator.comparing(ZpStaffFamily::getCreateTime)).collect(Collectors.toList());
        }
        return this.getStaffFamily(familyList);
    }

    @Override
    public List<StaffProject> getProjectList(String staffId) {
        LambdaQueryWrapper<ZpStaffProject> staffProjectWrapper = new LambdaQueryWrapper<>();
        staffProjectWrapper.eq(ZpStaffProject::getStaffId, staffId);
        List<ZpStaffProject> projectList = this.iZpStaffProjectService.list(staffProjectWrapper);
        if (projectList.size() > 0) {
            projectList = projectList.stream().sorted(Comparator.comparing(ZpStaffProject::getCreateTime)).collect(Collectors.toList());
        }
        return this.getStaffProject(projectList);
    }

    @Override
    public List<StaffAward> getAwardList(String staffId) {
        LambdaQueryWrapper<ZpStaffAward> staffAwardWrapper = new LambdaQueryWrapper<>();
        staffAwardWrapper.eq(ZpStaffAward::getStaffId, staffId);
        List<ZpStaffAward> awardList = this.iZpStaffAwardService.list(staffAwardWrapper);
        if (awardList.size() > 0) {
            awardList = awardList.stream().sorted(Comparator.comparing(ZpStaffAward::getCreateTime)).collect(Collectors.toList());
        }
        return this.getStaffAward(awardList);
    }

    @Override
    public void updateStaffInfo(StaffInfo staffInfo) throws ParseException {
        Date thisDate = new Date();
        ZpStaffInfo update = new ZpStaffInfo();
        // 不可修改
//        update.setRyname(staffInfo.getRyname()); //姓名
        update.setSex(staffInfo.getSex());
        update.setCsdat(staffInfo.getCsdat()); //出生
        if (StringUtils.isNotBlank(staffInfo.getCsdats())) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            update.setCsdat(sdf.parse(staffInfo.getCsdats()));
        }
        update.setIdnumber(staffInfo.getIdnumber());
        update.setZhrjg(staffInfo.getZhrjg()); //籍贯
        update.setJkzt(staffInfo.getJkzt()); //健康状态
        update.setZhrsg(staffInfo.getZhrsg()); // 身高
        update.setZhrtz(staffInfo.getZhrtz()); // 体重
        update.setZhrxx(staffInfo.getZhrxx()); // 血型
        update.setZhrmz(staffInfo.getZhrmz()); // 民族
        update.setIsfcdj(staffInfo.getIsfcdj());// 是否服从调剂
        update.setZzmm(staffInfo.getZzmm()); // 政治面貌
        update.setHyzt(staffInfo.getHyzt()); // 婚姻状态
        update.setZngs(staffInfo.getZngs()); //子女个数
        update.setZyks1(staffInfo.getZyks1());  // 第一志愿科室
        update.setZyks2(staffInfo.getZyks2());  // 第二志愿科室
        update.setZgxl(staffInfo.getZgxl()); //最高学历
        update.setWysp(staffInfo.getWysp()); // 外语水平
        update.setWyspfs(staffInfo.getWyspfs()); // 外语水平分数
        update.setJsjsp(staffInfo.getJsjsp()); // 计算机水平
//        update.setTel(staffInfo.getTel()); //手机联系电话 只读
//        update.setEmail(staffInfo.getEmail());//电子邮箱 只读
        update.setWechatNo(staffInfo.getWechatNo()); //微信号码
        update.setJtzz(staffInfo.getJtzz()); //家庭住址
        update.setHjdz(staffInfo.getHjdz()); //户籍地址
        update.setXjdz(staffInfo.getXjdz()); //现居地址
        update.setJjlxr(staffInfo.getJjlxr()); //紧急联系人
        update.setLxrtel(staffInfo.getLxrtel()); //联系人电话
        update.setIsyszgz(staffInfo.getIsyszgz()); // 是否医师资格证
        update.setZylx(staffInfo.getZylx()); //职业类型
        update.setIsbysqdzyys(staffInfo.getIsbysqdzyys()); //毕业时是否取得住院医师规范化培训合格证
        update.setIsssjdszhy(staffInfo.getIsssjdszhy()); //硕士阶段是否四证合一
        update.setYwjwbs(staffInfo.getYwjwbs()); //有无既往病史
        update.setZwjs(staffInfo.getZwjs()); //自我介绍
        update.setZxhjqk(staffInfo.getZxhjqk()); // 在校获奖情况(院级以上)
        update.setComments(staffInfo.getComments()); //备注
//        update.setSblx(staffInfo.getSblx()); //申报类型

        update.setIssub(1);

        ZpStaffInfo info = this.getById(staffInfo.getId());
        if (info == null) {
            update.setId(staffInfo.getId());
            update.setCreateTime(thisDate);
            this.save(update);
        } else {
            update.setModifyTime(thisDate);
            LambdaQueryWrapper<ZpStaffInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ZpStaffInfo::getId, info.getId());
            this.update(update, queryWrapper);
        }
        /*
        if (staffInfo.getFamilys() !=null && staffInfo.getFamilys().size() > 0) {
            List<ZpStaffFamily> list = this.getZpStaffFamily(staffInfo, thisDate);
            for (ZpStaffFamily item : list) {
                iZpStaffFamilyService.saveOrUpdate(item);
            }
        }

        if (staffInfo.getEducations() !=null && staffInfo.getEducations().size() > 0) {
            List<ZpStaffEducation> list = this.getZpStaffEducation(staffInfo, thisDate);
            for (ZpStaffEducation item : list) {
                iZpStaffEducationService.saveOrUpdate(item);
            }
        }

        if (staffInfo.getWorks() !=null && staffInfo.getWorks().size() > 0) {
            List<ZpStaffWork> list = this.getZpStaffWork(staffInfo, thisDate);
            for (ZpStaffWork item : list) {
                iZpStaffWorkService.saveOrUpdate(item);
            }
        }

        if (staffInfo.getEssays() !=null && staffInfo.getEssays().size() > 0) {
            List<ZpStaffEssay> list = this.getZpStaffEssay(staffInfo, thisDate);
            for (ZpStaffEssay item : list) {
                iZpStaffEssayService.saveOrUpdate(item);
            }
        }

        if (staffInfo.getProjects() !=null && staffInfo.getProjects().size() > 0) {
            List<ZpStaffProject> list = this.getZpStaffProject(staffInfo, thisDate);
            for (ZpStaffProject item : list) {
                iZpStaffProjectService.saveOrUpdate(item);
            }
        }

        if (staffInfo.getAwards() !=null && staffInfo.getAwards().size() > 0) {
            List<ZpStaffAward> list = this.getZpStaffAward(staffInfo, thisDate);
            for (ZpStaffAward item : list) {
                iZpStaffAwardService.saveOrUpdate(item);
            }
        }
         */
    }

    private List<ZpStaffFamily> getZpStaffFamily(StaffInfo staffInfo, Date thisDate) throws ParseException {
        List<ZpStaffFamily> familyList = new ArrayList<>();
        for (StaffFamily item : staffInfo.getFamilys()) {
            ZpStaffFamily update = new ZpStaffFamily();
            update.setId(item.getId());
            update.setStaffId(staffInfo.getId());
            update.setUserid(staffInfo.getUserid());
            update.setWcname(item.getWcname());
            update.setXmname(item.getXmname());
            update.setZzmm(item.getZzmm());
            if (StringUtils.isNotBlank(item.getCsdats())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                update.setCsdat(sdf.parse(item.getCsdats()));
            }
            update.setGzdwjzw(item.getGzdwjzw());
            update.setModifyTime(thisDate);
            familyList.add(update);
        }
        return familyList;
    }

    private List<ZpStaffEducation> getZpStaffEducation(StaffInfo staffInfo, Date thisDate) {
        List<ZpStaffEducation> educationList = new ArrayList<>();
        for (StaffEducation item : staffInfo.getEducations()) {
            ZpStaffEducation update = new ZpStaffEducation();
            update.setId(item.getId());
            update.setStaffId(staffInfo.getId());
            update.setUserid(staffInfo.getUserid());
            update.setXlxw(item.getXlxw());// 学历/学位
            update.setSrtdat(item.getSrtdat()); // 起始时间
            update.setEnddat(item.getEnddat()); // 终止时间
            update.setYxname(item.getYxname()); // 院校
            update.setXkzy1(item.getXkzy1()); // 学科专业1
            update.setXkzy2(item.getXkzy2()); // 学科专业2
            update.setXklx(item.getXklx()); // 学科类型
            update.setYjfx(item.getYjfx()); // 研究方向
            update.setDsxx(item.getDsxx()); // 导师信息
            update.setModifyTime(thisDate);
            educationList.add(update);
        }
        return educationList;
    }

    private List<StaffEducation> getStaffEducation(List<ZpStaffEducation> list) {
        List<StaffEducation> educationList = new ArrayList<>();
        for (ZpStaffEducation item : list) {
            StaffEducation insert = new StaffEducation();
            insert.setId(item.getId());
            insert.setXlxw(item.getXlxw());// 学历/学位
            insert.setXlxwname(item.getXlxwname());
            insert.setSrtdat(item.getSrtdat()); // 起始时间
            insert.setEnddat(item.getEnddat()); // 终止时间
            insert.setYxname(item.getYxname()); // 院校
            insert.setXkzy1(item.getXkzy1()); // 学科专业1
            insert.setXkzy2(item.getXkzy2()); // 学科专业2
            insert.setXklx(item.getXklx()); // 学科类型
            insert.setXklxname(item.getXklxname());
            insert.setYjfx(item.getYjfx()); // 研究方向
            insert.setDsxx(item.getDsxx()); // 导师信息
            educationList.add(insert);
        }
        return educationList;
    }

    private List<ZpStaffWork> getZpStaffWork(StaffInfo staffInfo, Date thisDate) {
        List<ZpStaffWork> workList = new ArrayList<>();
        for (StaffWork item : staffInfo.getWorks()) {
            ZpStaffWork update = new ZpStaffWork();
            update.setId(item.getId());
            update.setStaffId(staffInfo.getId());
            update.setUserid(staffInfo.getUserid());
            update.setSrtdat(item.getSrtdat()); // 起始时间
            update.setEnddat(item.getEnddat()); // 终止时间
            update.setWkdw(item.getWkdw()); // 工作单位
            update.setWkzw(item.getWkzw()); // 工作职务
            update.setWkxl(item.getWkxl()); // 工作学历
            update.setWkbm(item.getWkbm()); // 工作部门
            update.setRemark(item.getRemark());
            update.setModifyTime(thisDate);
            workList.add(update);
        }
        return workList;
    }

    private List<StaffWork> getStaffWork(List<ZpStaffWork> list) {
        List<StaffWork> workList = new ArrayList<>();
        for (ZpStaffWork item : list) {
            StaffWork insert = new StaffWork();
            insert.setId(item.getId());
            insert.setSrtdat(item.getSrtdat()); // 起始时间
            insert.setEnddat(item.getEnddat()); // 终止时间
            insert.setWkdw(item.getWkdw()); // 工作单位
            insert.setWkzw(item.getWkzw()); // 工作职务
            insert.setWkxl(item.getWkxl()); // 工作学历
            insert.setWkxlname(item.getWkxlname());
            insert.setWkbm(item.getWkbm()); // 工作部门
            insert.setRemark(item.getRemark());
            workList.add(insert);
        }
        return workList;
    }

    private List<ZpStaffEssay> getZpStaffEssay(StaffInfo staffInfo, Date thisDate) {
        List<ZpStaffEssay> essayList = new ArrayList<>();
        for (StaffEssay item : staffInfo.getEssays()) {
            ZpStaffEssay update = new ZpStaffEssay();
            update.setId(item.getId());
            update.setStaffId(staffInfo.getId());
            update.setUserid(staffInfo.getUserid());
            update.setWzname(item.getWzname()); // 文章名称
            update.setBrpm(item.getBrpm()); // 本人排名
            update.setKwjb(item.getKwjb()); // 刊物级别
            update.setCbdat(item.getCbdat()); // 出版时间
            update.setFbzt(item.getFbzt()); // 发布状态
            update.setCbkw(item.getCbkw()); // 出版刊物
            update.setCbkh(item.getCbkh()); // 出版刊号
            update.setYxyz(item.getYxyz()); // 影响因子
            update.setModifyTime(thisDate);
            essayList.add(update);
        }
        return essayList;
    }

    private List<ZpStaffProject> getZpStaffProject(StaffInfo staffInfo, Date thisDate) {
        List<ZpStaffProject> projectList = new ArrayList<>();
        for (StaffProject item : staffInfo.getProjects()) {
            ZpStaffProject update = new ZpStaffProject();
            update.setId(item.getId());
            update.setStaffId(staffInfo.getId());
            update.setUserid(staffInfo.getUserid());
            update.setProjectname(item.getProjectname());
            update.setSrtdat(item.getSrtdat()); // 起始时间
            update.setEnddat(item.getEnddat()); // 终止时间
            update.setXbly(item.getXbly());
            update.setJf(item.getJf());
            update.setBrpm(item.getBrpm());
            update.setModifyTime(thisDate);
            projectList.add(update);
        }
        return projectList;
    }

    private List<ZpStaffAward> getZpStaffAward(StaffInfo staffInfo, Date thisDate) {
        List<ZpStaffAward> awardList = new ArrayList<>();
        for (StaffAward item : staffInfo.getAwards()) {
            ZpStaffAward update = new ZpStaffAward();
            update.setId(item.getId());
            update.setStaffId(staffInfo.getId());
            update.setUserid(staffInfo.getUserid());
            update.setJxname(item.getJxname());
            update.setMc(item.getMc());
            update.setHjdat(item.getHjdat());
            update.setRemark(item.getRemark());
            update.setModifyTime(thisDate);
            awardList.add(update);
        }
        return awardList;
    }

    private List<StaffEssay> getStaffEssay(List<ZpStaffEssay> list) {
        List<StaffEssay> essayList = new ArrayList<>();
        for (ZpStaffEssay item : list) {
            StaffEssay insert = new StaffEssay();
            insert.setId(item.getId());
            insert.setWzname(item.getWzname()); // 文章名称
            insert.setBrpm(item.getBrpm()); // 本人排名
            insert.setBrpmname(item.getBrpmname());
            insert.setKwjb(item.getKwjb()); // 刊物级别
            insert.setKwjbname(item.getKwjbname());
            insert.setCbdat(item.getCbdat()); // 出版时间
            insert.setFbzt(item.getFbzt()); // 发布状态
            insert.setFbztname(item.getFbztname());
            insert.setCbkw(item.getCbkw()); // 出版刊物
            insert.setCbkh(item.getCbkh()); // 出版刊号
            insert.setYxyz(item.getYxyz()); // 影响因子
            essayList.add(insert);
        }
        return essayList;
    }

    private List<StaffFamily> getStaffFamily(List<ZpStaffFamily> list) {
        List<StaffFamily> familyList = new ArrayList<>();
        for (ZpStaffFamily item : list) {
            StaffFamily insert = new StaffFamily();
            insert.setId(item.getId());
            insert.setWcname(item.getWcname());
            insert.setXmname(item.getXmname());
            insert.setZzmm(item.getZzmm());
            insert.setZzmmname(item.getZzmmname());
            insert.setCsdat(item.getCsdat());
            if (item.getCsdat() != null) {
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                insert.setCsdats(sdf.format(item.getCsdat()));
            }
            insert.setGzdwjzw(item.getGzdwjzw());

            familyList.add(insert);
        }
        return familyList;
    }

    private List<StaffProject> getStaffProject(List<ZpStaffProject> list) {
        List<StaffProject> projectList = new ArrayList<>();
        for (ZpStaffProject item : list) {
            StaffProject insert = new StaffProject();
            insert.setId(item.getId());
            insert.setProjectname(item.getProjectname());
            insert.setSrtdat(item.getSrtdat());
            insert.setEnddat(item.getEnddat());
            insert.setXbly(item.getXbly());
            insert.setJf(item.getJf());
            insert.setBrpm(item.getBrpm());
            projectList.add(insert);
        }
        return projectList;
    }

    private List<StaffAward> getStaffAward(List<ZpStaffAward> list) {
        List<StaffAward> awardList = new ArrayList<>();
        for (ZpStaffAward item : list) {
            StaffAward insert = new StaffAward();
            insert.setId(item.getId());
            insert.setJxname(item.getJxname());
            insert.setMc(item.getMc());
            insert.setHjdat(item.getHjdat());
            insert.setRemark(item.getRemark());
            awardList.add(insert);
        }
        return awardList;
    }

    @Override
    @Transactional
    public void editZpStaffFamily(StaffFamily staffFamily, User user) {
        if (StringUtils.isNotBlank(staffFamily.getId())) {
            ZpStaffFamily obj = iZpStaffFamilyService.getById(staffFamily.getId());
            ZpStaffFamily entity = new ZpStaffFamily();
            if (obj != null) {
                entity.setModifyTime(new Date());
            } else {
                entity.setId(UUID.randomUUID().toString());
                entity.setUserid(user.getUserId());
                entity.setStaffId(staffFamily.getStaffId());
                entity.setCreateTime(new Date());
            }

            entity.setId(staffFamily.getId());
            entity.setWcname(staffFamily.getWcname());
            entity.setXmname(staffFamily.getXmname());
            entity.setZzmm(staffFamily.getZzmm());
            entity.setGzdwjzw(staffFamily.getGzdwjzw());
            if (StringUtils.isNotBlank(staffFamily.getCsdats())) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    entity.setCsdat(sdf.parse(staffFamily.getCsdats()));
                } catch (Exception e) {
                }
            }

            if (obj != null) {
                iZpStaffFamilyService.updateById(entity);
            } else {
                iZpStaffFamilyService.save(entity);
            }
        }
    }

    @Override
    @Transactional
    public void editZpStaffEducation(StaffEducation staffEducation, User user) {
        if (StringUtils.isNotBlank(staffEducation.getId())) {
            ZpStaffEducation obj = iZpStaffEducationService.getById(staffEducation.getId());
            ZpStaffEducation entity = new ZpStaffEducation();
            if (obj != null) {
                entity.setModifyTime(new Date());
            } else {
                entity.setId(UUID.randomUUID().toString());
                entity.setUserid(user.getUserId());
                entity.setStaffId(staffEducation.getStaffId());
                entity.setCreateTime(new Date());
            }

            entity.setId(staffEducation.getId());
            entity.setYxname(staffEducation.getYxname());
            entity.setXlxw(staffEducation.getXlxw());
            entity.setSrtdat(staffEducation.getSrtdat());
            entity.setEnddat(staffEducation.getEnddat());
            entity.setXkzy1(staffEducation.getXkzy1());
            entity.setXkzy2(staffEducation.getXkzy2());
            entity.setXklx(staffEducation.getXklx());
            entity.setYjfx(staffEducation.getYjfx());
            entity.setDsxx(staffEducation.getDsxx());

            if (obj != null) {
                iZpStaffEducationService.updateById(entity);
            } else {
                iZpStaffEducationService.save(entity);
            }
        }
    }

    @Override
    @Transactional
    public void editZpStaffWork(StaffWork staffWork, User user) {
        if (StringUtils.isNotBlank(staffWork.getId())) {
            ZpStaffWork obj = iZpStaffWorkService.getById(staffWork.getId());
            ZpStaffWork entity = new ZpStaffWork();
            if (obj != null) {
                entity.setModifyTime(new Date());
            } else {
                entity.setUserid(user.getUserId());
                entity.setStaffId(staffWork.getStaffId());
                entity.setCreateTime(new Date());
            }
            entity.setId(staffWork.getId());
            entity.setSrtdat(staffWork.getSrtdat()); // 起始时间
            entity.setEnddat(staffWork.getEnddat()); // 终止时间
            entity.setWkdw(staffWork.getWkdw()); // 工作单位
            entity.setWkzw(staffWork.getWkzw()); // 工作职务
            entity.setWkxl(staffWork.getWkxl()); // 工作学历
            entity.setWkbm(staffWork.getWkbm()); // 工作部门
            entity.setRemark(staffWork.getRemark());

            if (obj != null) {
                iZpStaffWorkService.updateById(entity);
            } else {
                iZpStaffWorkService.save(entity);
            }
        }
    }

    @Override
    @Transactional
    public void editZpStaffEssay(StaffEssay staffEssay, User user) {
        if (StringUtils.isNotBlank(staffEssay.getId())) {
            ZpStaffEssay obj = iZpStaffEssayService.getById(staffEssay.getId());
            ZpStaffEssay entity = new ZpStaffEssay();
            if (obj != null) {
                entity.setModifyTime(new Date());
            } else {
                entity.setUserid(user.getUserId());
                entity.setStaffId(staffEssay.getStaffId());
                entity.setCreateTime(new Date());
            }

            entity.setId(staffEssay.getId());
            entity.setWzname(staffEssay.getWzname());
            entity.setKwjb(staffEssay.getKwjb());
            entity.setBrpm(staffEssay.getBrpm());

            entity.setCbdat(staffEssay.getCbdat());
            entity.setFbzt(staffEssay.getFbzt());
            entity.setCbkw(staffEssay.getCbkw());
            entity.setCbkh(staffEssay.getCbkh());
            entity.setYxyz(staffEssay.getYxyz()); // 影响因子

            if (obj != null) {
                iZpStaffEssayService.updateById(entity);
            } else {
                iZpStaffEssayService.save(entity);
            }
        }
    }

    @Override
    @Transactional
    public void editZpStaffProject(StaffProject staffProject, User user) {
        if (StringUtils.isNotBlank(staffProject.getId())) {
            ZpStaffProject obj = iZpStaffProjectService.getById(staffProject.getId());
            ZpStaffProject entity = new ZpStaffProject();
            if (obj != null) {
                entity.setModifyTime(new Date());
            } else {
                entity.setUserid(user.getUserId());
                entity.setStaffId(staffProject.getStaffId());
                entity.setCreateTime(new Date());
            }

            entity.setId(staffProject.getId());
            entity.setProjectname(staffProject.getProjectname());
            entity.setSrtdat(staffProject.getSrtdat());
            entity.setEnddat(staffProject.getEnddat());
            entity.setXbly(staffProject.getXbly());
            entity.setJf(staffProject.getJf());
            entity.setBrpm(staffProject.getBrpm());

            if (obj != null) {
                iZpStaffProjectService.updateById(entity);
            } else {
                iZpStaffProjectService.save(entity);
            }
        }
    }

    @Override
    @Transactional
    public void editZpStaffAward(StaffAward staffAward, User user) {
        if (StringUtils.isNotBlank(staffAward.getId())) {
            ZpStaffAward obj = iZpStaffAwardService.getById(staffAward.getId());
            ZpStaffAward entity = new ZpStaffAward();
            if (obj != null) {
                entity.setModifyTime(new Date());
            } else {
                entity.setUserid(user.getUserId());
                entity.setStaffId(staffAward.getStaffId());
                entity.setCreateTime(new Date());
            }

            entity.setId(staffAward.getId());
            entity.setJxname(staffAward.getJxname());
            entity.setMc(staffAward.getMc());
            entity.setHjdat(staffAward.getHjdat());
            entity.setRemark(staffAward.getRemark());

            if (obj != null) {
                iZpStaffAwardService.updateById(entity);
            } else {
                iZpStaffAwardService.save(entity);
            }
        }
    }

    @Override
    @Transactional
    public boolean applyStateUpdate(String applyId, int state) {
        boolean isUpdate = false;
        ZpStaffApply obj = iZpStaffApplyService.getById(applyId);
        if (obj != null) {
            ZpStaffApply update = new ZpStaffApply();
            update.setId(obj.getId());
            update.setState(state);
            isUpdate = iZpStaffApplyService.updateById(update);
        }

        return isUpdate;
    }

    @Override
    @Transactional
    public boolean applyStateUpdates(String[] ids, int state) {
        boolean isUpdate = false;
        LambdaQueryWrapper<ZpStaffApply> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ZpStaffApply::getId, ids);
        List<ZpStaffApply> list = iZpStaffApplyService.list(wrapper);
        if (list.size() > 0) {
            List<ZpStaffApply> updateList = new ArrayList<>();
            for (ZpStaffApply item : list) {
                ZpStaffApply update = new ZpStaffApply();
                update.setId(item.getId());
                update.setState(state);
                updateList.add(update);
            }
            if (updateList.size() > 0) {
                isUpdate = iZpStaffApplyService.updateBatchById(updateList);
            }
        }

        return isUpdate;
    }

    @Override
    public List<ZpStaffInfo> findStaffByPosterId(String posterId, Integer state, List<String> idList) {
        return this.baseMapper.findStaffByPosterIdAndSs(posterId, state, idList);
    }

    @Override
    public IPage<ZpStaffInfo> findZpStaffInfoList(QueryRequest request, ZpStaffInfo zpStaffInfo) {
        try {
            Page<ZpStaffInfo> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findZpStaffInfo(page, zpStaffInfo);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createZpStaffInfo(ZpStaffInfo zpStaffInfo) {
        zpStaffInfo.setId(UUID.randomUUID().toString());
        zpStaffInfo.setCreateTime(new Date());
//        zpStaffInfo.setIsDeletemark(1);
        this.save(zpStaffInfo);
    }

    @Override
    @Transactional
    public void updateZpStaffInfo(ZpStaffInfo zpStaffInfo) {
        zpStaffInfo.setModifyTime(new Date());
        this.baseMapper.updateZpStaffInfo(zpStaffInfo);
    }

    @Override
    @Transactional
    public void deleteZpStaffInfos(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    public boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
                flag = true;
            }
        } else {
            flag = true;
        }
        return flag;
    }
}
