package cc.mrbird.febs.zp.service;

import cc.mrbird.febs.zp.entity.ZpStaffProject;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author viki
 * @since 2022-08-18
 */
public interface IZpStaffProjectService extends IService<ZpStaffProject> {

        IPage<ZpStaffProject> findZpStaffProjects(QueryRequest request, ZpStaffProject zpStaffProject);

        IPage<ZpStaffProject> findZpStaffProjectList(QueryRequest request, ZpStaffProject zpStaffProject);

        void createZpStaffProject(ZpStaffProject zpStaffProject);

        void updateZpStaffProject(ZpStaffProject zpStaffProject);

        void deleteZpStaffProjects(String[]Ids);
        }
