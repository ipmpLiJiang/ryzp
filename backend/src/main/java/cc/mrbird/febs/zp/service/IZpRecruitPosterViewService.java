package cc.mrbird.febs.zp.service;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.zp.entity.ZpRecruitPosterView;
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
public interface IZpRecruitPosterViewService extends IService<ZpRecruitPosterView> {

    IPage<ZpRecruitPosterView> findZpRecruitPosterViews(QueryRequest request, ZpRecruitPosterView zpRecruitPosterView);

    IPage<ZpRecruitPosterView> findZpRecruitPosterOverdueViews(QueryRequest request, ZpRecruitPosterView zpRecruitPosterView);
}
