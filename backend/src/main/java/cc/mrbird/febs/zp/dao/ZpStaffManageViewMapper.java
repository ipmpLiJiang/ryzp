package cc.mrbird.febs.zp.dao;

import cc.mrbird.febs.zp.entity.QuertTab;
import cc.mrbird.febs.zp.entity.ZpStaffManageView;
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
public interface ZpStaffManageViewMapper extends BaseMapper<ZpStaffManageView> {
    IPage<ZpStaffManageView> findStaffManageView(Page page,
                                                 @Param("zpStaffManageView") ZpStaffManageView zpStaffManageView);


}
