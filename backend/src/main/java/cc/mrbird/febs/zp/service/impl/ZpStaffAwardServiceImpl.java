package cc.mrbird.febs.zp.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.zp.entity.ZpStaffAward;
import cc.mrbird.febs.zp.dao.ZpStaffAwardMapper;
import cc.mrbird.febs.zp.entity.ZpStaffInfo;
import cc.mrbird.febs.zp.service.IZpStaffAwardService;
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
@Service("IZpStaffAwardService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZpStaffAwardServiceImpl extends ServiceImpl<ZpStaffAwardMapper, ZpStaffAward> implements IZpStaffAwardService {


    @Override
    public IPage<ZpStaffAward> findZpStaffAwards(QueryRequest request, ZpStaffAward zpStaffAward) {
        try {
            LambdaQueryWrapper<ZpStaffAward> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(ZpStaffAward::getIsDeletemark, 1);//1是未删 0是已删


            Page<ZpStaffAward> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<ZpStaffAward> findZpStaffAwardList(QueryRequest request, ZpStaffAward zpStaffAward) {
        try {
            Page<ZpStaffAward> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findZpStaffAward(page, zpStaffAward);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createZpStaffAward(ZpStaffAward zpStaffAward) {
        zpStaffAward.setId(UUID.randomUUID().toString());
        zpStaffAward.setCreateTime(new Date());
//        zpStaffAward.setIsDeletemark(1);
        this.save(zpStaffAward);
    }

    @Override
    @Transactional
    public ZpStaffAward saveInitStaffAward(ZpStaffInfo zpStaffInfo, Date thisDate) {
        ZpStaffAward award = new ZpStaffAward();
        award.setId(UUID.randomUUID().toString());
        award.setUserid(zpStaffInfo.getUserid());
        award.setStaffId(zpStaffInfo.getId());
        award.setCreateTime(thisDate);
        this.save(award);
        return award;
    }

    @Override
    @Transactional
    public void updateZpStaffAward(ZpStaffAward zpStaffAward) {
        zpStaffAward.setModifyTime(new Date());
        this.baseMapper.updateZpStaffAward(zpStaffAward);
    }

    @Override
    @Transactional
    public void deleteZpStaffAwards(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    public List<ZpStaffAward> findAwardByPosterId(String posterId, Integer state, List<String> idList) {
        return this.baseMapper.findAwardByPosterIdAndSs(posterId,state,idList);
    }

}
