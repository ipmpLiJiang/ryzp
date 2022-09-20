package cc.mrbird.febs.zp.dao;

import cc.mrbird.febs.zp.entity.ZpStaffInfo;
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
 * @since 2022-01-03
 */
public interface ZpStaffInfoMapper extends BaseMapper<ZpStaffInfo> {
    void updateZpStaffInfo(ZpStaffInfo zpStaffInfo);

    IPage<ZpStaffInfo> findZpStaffInfo(Page page, @Param("zpStaffInfo") ZpStaffInfo zpStaffInfo);

    List<ZpStaffInfo> findStaffByPosterIdAndSs(@Param("posterId") String posterId,
                                               @Param("state") Integer state,
                                               @Param("idList") List<String> idList);

    void updateStaffIdTel(@Param("id") String id,
                          @Param("idnumber") String idnumber,
                          @Param("tel") String tel);
}
