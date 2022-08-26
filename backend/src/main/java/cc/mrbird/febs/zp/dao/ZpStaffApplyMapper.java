package cc.mrbird.febs.zp.dao;

import cc.mrbird.febs.zp.entity.ZpStaffApply;
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
public interface ZpStaffApplyMapper extends BaseMapper<ZpStaffApply> {
    void updateZpStaffApply(ZpStaffApply zpStaffApply);

    IPage<ZpStaffApply> findZpStaffApply(Page page, @Param("zpStaffApply") ZpStaffApply zpStaffApply);
}
