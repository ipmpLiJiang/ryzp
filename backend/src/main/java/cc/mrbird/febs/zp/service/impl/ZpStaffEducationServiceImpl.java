package cc.mrbird.febs.zp.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.zp.entity.ZpStaffEducation;
import cc.mrbird.febs.zp.dao.ZpStaffEducationMapper;
import cc.mrbird.febs.zp.entity.ZpStaffInfo;
import cc.mrbird.febs.zp.service.IZpStaffEducationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * @since 2022-01-03
 */
@Slf4j
@Service("IZpStaffEducationService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZpStaffEducationServiceImpl extends ServiceImpl<ZpStaffEducationMapper, ZpStaffEducation> implements IZpStaffEducationService {


    @Override
    public IPage<ZpStaffEducation> findZpStaffEducations(QueryRequest request, ZpStaffEducation zpStaffEducation) {
        try {
            LambdaQueryWrapper<ZpStaffEducation> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(ZpStaffEducation::getIsDeletemark, 1);//1是未删 0是已删


            Page<ZpStaffEducation> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<ZpStaffEducation> findZpStaffEducationList(QueryRequest request, ZpStaffEducation zpStaffEducation) {
        try {
            Page<ZpStaffEducation> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findZpStaffEducation(page, zpStaffEducation);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createZpStaffEducation(ZpStaffEducation zpStaffEducation) {
        zpStaffEducation.setId(UUID.randomUUID().toString());
//        zpStaffEducation.setCreateTime(new Date());
//        zpStaffEducation.setIsDeletemark(1);
        this.save(zpStaffEducation);
    }

    @Override
    @Transactional
    public void updateZpStaffEducation(ZpStaffEducation zpStaffEducation) {
//        zpStaffEducation.setModifyTime(new Date());
        this.baseMapper.updateZpStaffEducation(zpStaffEducation);
    }

    @Override
    @Transactional
    public void deleteZpStaffEducations(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public ZpStaffEducation saveInitStaffEducation(ZpStaffInfo zpStaffInfo, Date thisDate) {
        ZpStaffEducation education = new ZpStaffEducation();
        education.setId(UUID.randomUUID().toString());
        education.setUserid(zpStaffInfo.getUserid());
        education.setStaffId(zpStaffInfo.getId());
        education.setCreateTime(thisDate);
        this.save(education);
        return education;
    }

    @Override
    public List<ZpStaffEducation> findEducationByPosterId(String posterId, Integer state, List<String> idList) {
        return this.baseMapper.findEducationByPosterIdAndSs(posterId, state, idList);
    }

}