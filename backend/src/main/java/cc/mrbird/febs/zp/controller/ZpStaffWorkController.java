package cc.mrbird.febs.zp.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.zp.service.IZpStaffWorkService;
import cc.mrbird.febs.zp.entity.ZpStaffWork;

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
@RequestMapping("zpStaffWork")

public class ZpStaffWorkController extends BaseController {

    private String message;
    @Autowired
    public IZpStaffWorkService iZpStaffWorkService;


    /**
     * 分页查询数据
     *
     * @param request     分页信息
     * @param zpStaffWork 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("zpStaffWork:view")
    public Map<String, Object> List(QueryRequest request, ZpStaffWork zpStaffWork) {
        return getDataTable(this.iZpStaffWorkService.findZpStaffWorks(request, zpStaffWork));
    }

    /**
     * 添加
     *
     * @param zpStaffWork
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("zpStaffWork:add")
    public void addZpStaffWork(@Valid ZpStaffWork zpStaffWork) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            zpStaffWork.setUserid(currentUser.getUserId());
            this.iZpStaffWorkService.createZpStaffWork(zpStaffWork);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param zpStaffWork
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("zpStaffWork:update")
    public void updateZpStaffWork(@Valid ZpStaffWork zpStaffWork) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
//      zpStaffWork.setModifyUserId(currentUser.getUserId());
            this.iZpStaffWorkService.updateZpStaffWork(zpStaffWork);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("zpStaffWork:delete")
    public void deleteZpStaffWorks(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iZpStaffWorkService.deleteZpStaffWorks(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("zpStaffWork:export")
    public void export(QueryRequest request, ZpStaffWork zpStaffWork, HttpServletResponse response) throws FebsException {
        try {
            List<ZpStaffWork> zpStaffWorks = this.iZpStaffWorkService.findZpStaffWorks(request, zpStaffWork).getRecords();
            ExcelKit.$Export(ZpStaffWork.class, response).downXlsx(zpStaffWorks, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public ZpStaffWork detail(@NotBlank(message = "{required}") @PathVariable String id) {
        ZpStaffWork zpStaffWork = this.iZpStaffWorkService.getById(id);
        return zpStaffWork;
    }
}