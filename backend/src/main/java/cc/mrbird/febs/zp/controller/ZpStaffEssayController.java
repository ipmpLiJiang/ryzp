package cc.mrbird.febs.zp.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.zp.service.IZpStaffEssayService;
import cc.mrbird.febs.zp.entity.ZpStaffEssay;

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
@RequestMapping("zpStaffEssay")

public class ZpStaffEssayController extends BaseController {

    private String message;
    @Autowired
    public IZpStaffEssayService iZpStaffEssayService;


    /**
     * 分页查询数据
     *
     * @param request      分页信息
     * @param zpStaffEssay 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("zpStaffEssay:view")
    public Map<String, Object> List(QueryRequest request, ZpStaffEssay zpStaffEssay) {
        return getDataTable(this.iZpStaffEssayService.findZpStaffEssays(request, zpStaffEssay));
    }

    /**
     * 添加
     *
     * @param zpStaffEssay
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("zpStaffEssay:add")
    public void addZpStaffEssay(@Valid ZpStaffEssay zpStaffEssay) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            zpStaffEssay.setUserid(currentUser.getUserId());
            this.iZpStaffEssayService.createZpStaffEssay(zpStaffEssay);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param zpStaffEssay
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("zpStaffEssay:update")
    public void updateZpStaffEssay(@Valid ZpStaffEssay zpStaffEssay) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
//      zpStaffEssay.setModifyUserId(currentUser.getUserId());
            this.iZpStaffEssayService.updateZpStaffEssay(zpStaffEssay);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("zpStaffEssay:delete")
    public void deleteZpStaffEssays(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iZpStaffEssayService.deleteZpStaffEssays(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("zpStaffEssay:export")
    public void export(QueryRequest request, ZpStaffEssay zpStaffEssay, HttpServletResponse response) throws FebsException {
        try {
            List<ZpStaffEssay> zpStaffEssays = this.iZpStaffEssayService.findZpStaffEssays(request, zpStaffEssay).getRecords();
            ExcelKit.$Export(ZpStaffEssay.class, response).downXlsx(zpStaffEssays, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public ZpStaffEssay detail(@NotBlank(message = "{required}") @PathVariable String id) {
        ZpStaffEssay zpStaffEssay = this.iZpStaffEssayService.getById(id);
        return zpStaffEssay;
    }
}