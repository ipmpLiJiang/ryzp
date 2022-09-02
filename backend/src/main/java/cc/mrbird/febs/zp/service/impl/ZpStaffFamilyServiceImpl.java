package cc.mrbird.febs.zp.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.zp.entity.ZpStaffFamily;
import cc.mrbird.febs.zp.dao.ZpStaffFamilyMapper;
import cc.mrbird.febs.zp.entity.ZpStaffInfo;
import cc.mrbird.febs.zp.service.IZpStaffFamilyService;
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
 * @since 2022-08-18
 */
@Slf4j
@Service("IZpStaffFamilyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZpStaffFamilyServiceImpl extends ServiceImpl<ZpStaffFamilyMapper, ZpStaffFamily> implements IZpStaffFamilyService {


    @Override
    public IPage<ZpStaffFamily> findZpStaffFamilys(QueryRequest request, ZpStaffFamily zpStaffFamily) {
        try {
            LambdaQueryWrapper<ZpStaffFamily> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(ZpStaffFamily::getIsDeletemark, 1);//1是未删 0是已删


            Page<ZpStaffFamily> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<ZpStaffFamily> findZpStaffFamilyList(QueryRequest request, ZpStaffFamily zpStaffFamily) {
        try {
            Page<ZpStaffFamily> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findZpStaffFamily(page, zpStaffFamily);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createZpStaffFamily(ZpStaffFamily zpStaffFamily) {
        zpStaffFamily.setId(UUID.randomUUID().toString());
        zpStaffFamily.setCreateTime(new Date());
//        zpStaffFamily.setIsDeletemark(1);
        this.save(zpStaffFamily);
    }

    @Override
    @Transactional
    public ZpStaffFamily saveInitStaffFamily(ZpStaffInfo zpStaffInfo, Date thisDate) {
        ZpStaffFamily family = new ZpStaffFamily();
        family.setId(UUID.randomUUID().toString());
        family.setUserid(zpStaffInfo.getUserid());
        family.setStaffId(zpStaffInfo.getId());
        family.setCreateTime(thisDate);
        this.save(family);
        return family;
    }

    @Override
    @Transactional
    public void updateZpStaffFamily(ZpStaffFamily zpStaffFamily) {
        zpStaffFamily.setModifyTime(new Date());
        this.baseMapper.updateZpStaffFamily(zpStaffFamily);
    }

    @Override
    @Transactional
    public void deleteZpStaffFamilys(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    public List<ZpStaffFamily> findFamilyByPosterId(String posterId, Integer state, List<String> idList) {
        return this.baseMapper.findFamilyByPosterIdAndSs(posterId,state,idList);
    }
}
