package cc.mrbird.febs.zp.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.zp.service.IZpStaffEducationService;
import cc.mrbird.febs.zp.entity.ZpStaffEducation;

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
 * @since 2022-01-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("zpStaffEducation")

public class ZpStaffEducationController extends BaseController {

    private String message;
    @Autowired
    public IZpStaffEducationService iZpStaffEducationService;


    /**
     * 分页查询数据
     *
     * @param request          分页信息
     * @param zpStaffEducation 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("zpStaffEducation:view")
    public Map<String, Object> List(QueryRequest request, ZpStaffEducation zpStaffEducation) {
        return getDataTable(this.iZpStaffEducationService.findZpStaffEducations(request, zpStaffEducation));
    }

    /**
     * 添加
     *
     * @param zpStaffEducation
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("zpStaffEducation:add")
    public void addZpStaffEducation(@Valid ZpStaffEducation zpStaffEducation) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            zpStaffEducation.setUserid(currentUser.getUserId());
            this.iZpStaffEducationService.createZpStaffEducation(zpStaffEducation);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param zpStaffEducation
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("zpStaffEducation:update")
    public void updateZpStaffEducation(@Valid ZpStaffEducation zpStaffEducation) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
//      zpStaffEducation.setModifyUserId(currentUser.getUserId());
            this.iZpStaffEducationService.updateZpStaffEducation(zpStaffEducation);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("zpStaffEducation:delete")
    public void deleteZpStaffEducations(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iZpStaffEducationService.deleteZpStaffEducations(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("zpStaffEducation:export")
    public void export(QueryRequest request, ZpStaffEducation zpStaffEducation, HttpServletResponse response) throws FebsException {
        try {
            List<ZpStaffEducation> zpStaffEducations = this.iZpStaffEducationService.findZpStaffEducations(request, zpStaffEducation).getRecords();
            ExcelKit.$Export(ZpStaffEducation.class, response).downXlsx(zpStaffEducations, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public ZpStaffEducation detail(@NotBlank(message = "{required}") @PathVariable String id) {
        ZpStaffEducation zpStaffEducation = this.iZpStaffEducationService.getById(id);
        return zpStaffEducation;
    }
}