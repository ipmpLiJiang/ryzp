package cc.mrbird.febs.zp.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.zp.service.IZpStaffFamilyService;
import cc.mrbird.febs.zp.entity.ZpStaffFamily;

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
@RequestMapping("zpStaffFamily")

public class ZpStaffFamilyController extends BaseController {

    private String message;
    @Autowired
    public IZpStaffFamilyService iZpStaffFamilyService;


    /**
     * 分页查询数据
     *
     * @param request       分页信息
     * @param zpStaffFamily 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("zpStaffFamily:view")
    public Map<String, Object> List(QueryRequest request, ZpStaffFamily zpStaffFamily) {
        return getDataTable(this.iZpStaffFamilyService.findZpStaffFamilys(request, zpStaffFamily));
    }

    /**
     * 添加
     *
     * @param zpStaffFamily
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("zpStaffFamily:add")
    public void addZpStaffFamily(@Valid ZpStaffFamily zpStaffFamily) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
//        zpStaffFamily.setCreateUserId(currentUser.getUserId());
            this.iZpStaffFamilyService.createZpStaffFamily(zpStaffFamily);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param zpStaffFamily
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("zpStaffFamily:update")
    public void updateZpStaffFamily(@Valid ZpStaffFamily zpStaffFamily) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
//      zpStaffFamily.setModifyUserId(currentUser.getUserId());
            this.iZpStaffFamilyService.updateZpStaffFamily(zpStaffFamily);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("zpStaffFamily:delete")
    public void deleteZpStaffFamilys(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iZpStaffFamilyService.deleteZpStaffFamilys(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("zpStaffFamily:export")
    public void export(QueryRequest request, ZpStaffFamily zpStaffFamily, HttpServletResponse response) throws FebsException {
        try {
            List<ZpStaffFamily> zpStaffFamilys = this.iZpStaffFamilyService.findZpStaffFamilys(request, zpStaffFamily).getRecords();
            ExcelKit.$Export(ZpStaffFamily.class, response).downXlsx(zpStaffFamilys, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public ZpStaffFamily detail(@NotBlank(message = "{required}") @PathVariable String id) {
        ZpStaffFamily zpStaffFamily = this.iZpStaffFamilyService.getById(id);
        return zpStaffFamily;
    }
}
