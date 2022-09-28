package cc.mrbird.febs.zp.service;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.zp.entity.QuertTab;
import cc.mrbird.febs.zp.entity.ZpStaffManageView;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author viki
 * @since 2022-01-08
 */
public interface IZpStaffManageViewService extends IService<ZpStaffManageView> {

    IPage<ZpStaffManageView> findZpPosterStaffViews(QueryRequest request, ZpStaffManageView zpPosterStaffView);

}
