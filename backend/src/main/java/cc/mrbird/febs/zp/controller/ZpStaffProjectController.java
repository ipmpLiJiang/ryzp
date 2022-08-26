package cc.mrbird.febs.zp.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.zp.service.IZpStaffProjectService;
import cc.mrbird.febs.zp.entity.ZpStaffProject;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author viki
 * @since 2022-08-18
 */
@Slf4j
@Validated
@RestController
@RequestMapping("zpStaffProject")

public class ZpStaffProjectController extends BaseController {

    private String message;
    @Autowired
    public IZpStaffProjectService iZpStaffProjectService;


    /**
     * 分页查询数据
     *
     * @param request        分页信息
     * @param zpStaffProject 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("zpStaffProject:view")
    public Map<String, Object> List(QueryRequest request, ZpStaffProject zpStaffProject) {
        return getDataTable(this.iZpStaffProjectService.findZpStaffProjects(request, zpStaffProject));
    }

    /**
     * 添加
     *
     * @param zpStaffProject
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("zpStaffProject:add")
    public void addZpStaffProject(@Valid ZpStaffProject zpStaffProject) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
//        zpStaffProject.setCreateUserId(currentUser.getUserId());
            this.iZpStaffProjectService.createZpStaffProject(zpStaffProject);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param zpStaffProject
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("zpStaffProject:update")
    public void updateZpStaffProject(@Valid ZpStaffProject zpStaffProject) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
//      zpStaffProject.setModifyUserId(currentUser.getUserId());
            this.iZpStaffProjectService.updateZpStaffProject(zpStaffProject);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("zpStaffProject:delete")
    public void deleteZpStaffProjects(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iZpStaffProjectService.deleteZpStaffProjects(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("zpStaffProject:export")
    public void export(QueryRequest request, ZpStaffProject zpStaffProject, HttpServletResponse response) throws FebsException {
        try {
            List<ZpStaffProject> zpStaffProjects = this.iZpStaffProjectService.findZpStaffProjects(request, zpStaffProject).getRecords();
            ExcelKit.$Export(ZpStaffProject.class, response).downXlsx(zpStaffProjects, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public ZpStaffProject detail(@NotBlank(message = "{required}") @PathVariable String id) {
        ZpStaffProject zpStaffProject = this.iZpStaffProjectService.getById(id);
        return zpStaffProject;
    }
}
