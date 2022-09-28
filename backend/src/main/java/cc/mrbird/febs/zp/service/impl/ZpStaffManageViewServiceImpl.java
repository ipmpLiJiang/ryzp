package cc.mrbird.febs.zp.service.impl;

import cc.mrbird.febs.com.entity.ComType;
import cc.mrbird.febs.com.service.IComTypeService;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.zp.dao.ZpStaffManageViewMapper;
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


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-01-08
 */
@Slf4j
@Service("IZpStaffManageViewService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZpStaffManageViewServiceImpl extends ServiceImpl<ZpStaffManageViewMapper, ZpStaffManageView> implements IZpStaffManageViewService {
    @Override
    public IPage<ZpStaffManageView> findZpPosterStaffViews(QueryRequest request, ZpStaffManageView zpPosterStaffView) {
        try {
            Page<ZpPosterStaffView> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            IPage<ZpStaffManageView> rpage = this.baseMapper.findStaffManageView(page, zpPosterStaffView);
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (ZpStaffManageView item : rpage.getRecords()) {
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

}
