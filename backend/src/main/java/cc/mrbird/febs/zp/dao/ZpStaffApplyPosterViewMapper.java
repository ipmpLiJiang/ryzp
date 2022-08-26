package cc.mrbird.febs.zp.dao;

import cc.mrbird.febs.zp.entity.ZpStaffApplyPosterView;
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
public interface ZpStaffApplyPosterViewMapper extends BaseMapper<ZpStaffApplyPosterView> {
    IPage<ZpStaffApplyPosterView> findStaffApplyPosterView(Page page, @Param("key") String key, @Param("staffId") String staffId);

    IPage<ZpStaffApplyPosterView> findMyStaffApplyPosterView(Page page, @Param("key") String key, @Param("staffId") String staffId);
}
