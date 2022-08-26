package cc.mrbird.febs.zp.dao;

import cc.mrbird.febs.zp.entity.ZpRecruitPosterView;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-01-08
 */
public interface ZpRecruitPosterViewMapper extends BaseMapper<ZpRecruitPosterView> {
    IPage<ZpRecruitPosterView> findZpRecruitPosterView(Page page, @Param("zpRecruitPosterView") ZpRecruitPosterView zpRecruitPosterView);

    IPage<ZpRecruitPosterView> findZpRecruitPosterOverdueView(Page page, @Param("zpRecruitPosterView") ZpRecruitPosterView zpRecruitPosterView);

}
