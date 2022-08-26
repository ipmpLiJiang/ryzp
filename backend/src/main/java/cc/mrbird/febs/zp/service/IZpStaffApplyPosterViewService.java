package cc.mrbird.febs.zp.service;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.zp.entity.ZpStaffApplyPosterView;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author viki
 * @since 2022-01-08
 */
public interface IZpStaffApplyPosterViewService extends IService<ZpStaffApplyPosterView> {

    IPage<ZpStaffApplyPosterView> findStaffApplyPosterViews(QueryRequest request,String key, User user);

    IPage<ZpStaffApplyPosterView> findMyStaffApplyPosterViews(QueryRequest request,String key, User user);
}
