package cc.mrbird.febs.zp.dao;

import cc.mrbird.febs.zp.entity.ZpStaffAward;
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
 * @since 2022-08-18
 */
public interface ZpStaffAwardMapper extends BaseMapper<ZpStaffAward> {
    void updateZpStaffAward(ZpStaffAward zpStaffAward);

    IPage<ZpStaffAward> findZpStaffAward(Page page, @Param("zpStaffAward") ZpStaffAward zpStaffAward);

    List<ZpStaffAward> findAwardByPosterIdAndSs(@Param("posterId") String posterId,
                                                    @Param("state") Integer state,
                                                    @Param("idList") List<String> idList);
}
