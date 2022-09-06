package cc.mrbird.febs.zp.dao;

import cc.mrbird.febs.zp.entity.QuertTab;
import cc.mrbird.febs.zp.entity.ZpPosterStaffView;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-01-08
 */
public interface ZpPosterStaffViewMapper extends BaseMapper<ZpPosterStaffView> {
    IPage<ZpPosterStaffView> findZpPosterStaffView(Page page,
                                                   @Param("zpPosterStaffView") ZpPosterStaffView zpPosterStaffView,
                                                   @Param("quertTabList") List<QuertTab> quertTabList);


}
