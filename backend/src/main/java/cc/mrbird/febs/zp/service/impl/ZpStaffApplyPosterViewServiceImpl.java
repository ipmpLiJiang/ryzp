package cc.mrbird.febs.zp.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.zp.dao.ZpStaffApplyPosterViewMapper;
import cc.mrbird.febs.zp.entity.ZpStaffApplyPosterView;
import cc.mrbird.febs.zp.entity.ZpStaffInfo;
import cc.mrbird.febs.zp.service.IZpStaffApplyPosterViewService;
import cc.mrbird.febs.zp.service.IZpStaffInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-01-08
 */
@Slf4j
@Service("IZpStaffApplyPosterViewService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZpStaffApplyPosterViewServiceImpl extends ServiceImpl<ZpStaffApplyPosterViewMapper, ZpStaffApplyPosterView> implements IZpStaffApplyPosterViewService {

    @Autowired
    IZpStaffInfoService iZpStaffInfoService;

    @Override
    public IPage<ZpStaffApplyPosterView> findStaffApplyPosterViews(QueryRequest request,String key, User user) {
        try {
            ZpStaffInfo zpStaffInfo = this.iZpStaffInfoService.findZpStaffInfoByUserId(user.getUserId());
            Page<ZpStaffApplyPosterView> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            IPage<ZpStaffApplyPosterView> rpage = this.baseMapper.findStaffApplyPosterView(page,key, zpStaffInfo == null ? null:zpStaffInfo.getId());
            return rpage;

        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<ZpStaffApplyPosterView> findMyStaffApplyPosterViews(QueryRequest request,String key, User user) {
        try {
            ZpStaffInfo zpStaffInfo = this.iZpStaffInfoService.findZpStaffInfoByUserId(user.getUserId());
            Page<ZpStaffApplyPosterView> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            IPage<ZpStaffApplyPosterView> rpage = this.baseMapper.findMyStaffApplyPosterView(page,key, zpStaffInfo == null ? null:zpStaffInfo.getId());
            return rpage;

        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }


}