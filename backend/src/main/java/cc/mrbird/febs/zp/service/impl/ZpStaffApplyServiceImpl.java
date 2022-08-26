package cc.mrbird.febs.zp.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.zp.entity.ZpRecruitPoster;
import cc.mrbird.febs.zp.entity.ZpStaffApply;
import cc.mrbird.febs.zp.dao.ZpStaffApplyMapper;
import cc.mrbird.febs.zp.entity.ZpStaffInfo;
import cc.mrbird.febs.zp.service.IZpRecruitPosterService;
import cc.mrbird.febs.zp.service.IZpStaffApplyService;
import cc.mrbird.febs.zp.service.IZpStaffInfoService;
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

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-01-08
 */
@Slf4j
@Service("IZpStaffApplyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZpStaffApplyServiceImpl extends ServiceImpl<ZpStaffApplyMapper, ZpStaffApply> implements IZpStaffApplyService {

    @Autowired
    IZpStaffInfoService iZpStaffInfoService;

    @Autowired
    IZpRecruitPosterService iZpRecruitPosterService;

    @Override
    public IPage<ZpStaffApply> findZpStaffApplys(QueryRequest request, ZpStaffApply zpStaffApply) {
        try {
            LambdaQueryWrapper<ZpStaffApply> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(ZpStaffApply::getIsDeletemark, 1);//1是未删 0是已删

            Page<ZpStaffApply> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<ZpStaffApply> findZpStaffApplyList(QueryRequest request, ZpStaffApply zpStaffApply) {
        try {
            Page<ZpStaffApply> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findZpStaffApply(page, zpStaffApply);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createZpStaffApply(ZpStaffApply zpStaffApply) {
        zpStaffApply.setId(UUID.randomUUID().toString());
        zpStaffApply.setCreateTime(new Date());
        this.save(zpStaffApply);
    }

    @Override
    @Transactional
    public String staffApplyPoster(ZpStaffApply zpStaffApply) {
        String message = "";
        ZpRecruitPoster recruitPoster = iZpRecruitPosterService.getById(zpStaffApply.getPosterId());
        if (recruitPoster != null) {
            // state 0 未发布 1 已发布 ispub 0 未公开 1已公开 isend 0 未结束 已结束
            if (recruitPoster.getState() == 1 && recruitPoster.getIsend() == 0) {
                ZpStaffInfo staffInfo = iZpStaffInfoService.findZpStaffInfoByUserId(zpStaffApply.getUserid());
                if (staffInfo != null && staffInfo.getIssub() == 1) {
                    LambdaQueryWrapper<ZpStaffApply> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(ZpStaffApply::getStaffId, staffInfo.getId());
                    queryWrapper.eq(ZpStaffApply::getPosterId, zpStaffApply.getPosterId());
                    List<ZpStaffApply> list = this.list(queryWrapper);
                    if (list.size() == 0) {
                        zpStaffApply.setState(0);
                        zpStaffApply.setId(UUID.randomUUID().toString());
                        zpStaffApply.setStaffId(staffInfo.getId());
                        zpStaffApply.setCrtdat(new Date());
                        zpStaffApply.setCreateTime(new Date());
                        this.save(zpStaffApply);
                        message = "ok";
                    } else {
                        message = "您已申请了该招聘信息，请耐心等待结果.";
                    }
                } else {
                    message = "请填写完整个人简历，再到招聘主页中进行简历投递.";
                }
            } else {
                message = "无法申请该招聘信息，该招聘信息已取消，或请稍后再申请.";
            }
        } else {
            message = "无法申请该招聘信息，该招聘信息已取消.";
        }
        return message;
    }

    @Override
    @Transactional
    public void updateZpStaffApply(ZpStaffApply zpStaffApply) {
        zpStaffApply.setModifyTime(new Date());
        this.baseMapper.updateZpStaffApply(zpStaffApply);
    }

    @Override
    @Transactional
    public void deleteZpStaffApplys(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }


}