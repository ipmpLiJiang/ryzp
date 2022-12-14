package cc.mrbird.febs.zp.controller;

import cc.mrbird.febs.com.entity.ComType;
import cc.mrbird.febs.com.service.IComTypeService;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.utils.MD5Util;
import cc.mrbird.febs.common.utils.PasswordUtil;
import cc.mrbird.febs.export.excel.ExportExcelUtils;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.zp.entity.QuertTab;
import cc.mrbird.febs.zp.entity.StaffInfo;
import cc.mrbird.febs.zp.entity.StaffInfoDataExport;
import cc.mrbird.febs.zp.entity.ZpPosterStaffView;
import cc.mrbird.febs.zp.service.IZpPosterStaffViewService;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author viki
 * @since 2022-01-08
 */
@Slf4j
@Validated
@RestController
@RequestMapping("zpPosterStaffView")

public class ZpPosterStaffViewController extends BaseController {

    private String message;
    @Autowired
    public IZpPosterStaffViewService iZpPosterStaffViewService;

    @Autowired
    IComTypeService iComTypeService;

    /**
     * ??????????????????
     *
     * @param request ????????????
     * @return
     */
    @GetMapping
    @RequiresPermissions("zpPosterStaffView:view")
    public Map<String, Object> List(QueryRequest request, ZpPosterStaffView zpPosterStaffView,String jsondata) {
        User currentUser = FebsUtil.getCurrentUser();
        List<QuertTab> quertTabList = new ArrayList<>();

        if(StringUtils.isNotBlank(jsondata)) {
            log.info(jsondata);
            jsondata = jsondata.replace("@", "+");
            String jStr = PasswordUtil.desEncrypt(jsondata);
            log.info(jStr);
            JSONArray queryTabJson = JSONObject.parseArray(jStr);
            quertTabList = queryTabJson.toJavaList(QuertTab.class);
        }
        for (QuertTab qt:quertTabList) {
            if(qt.getF().equals("sex")) {
                if(qt.getZ().equals("???")) {
                    qt.setZ("0");
                } else if(qt.getZ().equals("???")) {
                    qt.setZ("1");
                } else {
                    qt.setZ("2");
                }
            }
        }

        return getDataTable(this.iZpPosterStaffViewService.findZpPosterStaffViews(request, zpPosterStaffView,quertTabList));
    }

    @GetMapping("list")
    @RequiresPermissions("zpPosterStaffView:view")
    public Map<String, Object> Lists(QueryRequest request, ZpPosterStaffView zpPosterStaffView,String jsondata) {
        User currentUser = FebsUtil.getCurrentUser();
        List<QuertTab> quertTabList = new ArrayList<>();

        if(StringUtils.isNotBlank(jsondata)) {
            log.info(jsondata);
            jsondata = jsondata.replace("@", "+");
            String jStr = PasswordUtil.desEncrypt(jsondata);
            log.info(jStr);
            JSONArray queryTabJson = JSONObject.parseArray(jStr);
            quertTabList = queryTabJson.toJavaList(QuertTab.class);
        }
        for (QuertTab qt:quertTabList) {
            if(qt.getF().equals("sex")) {
                if(qt.getZ().equals("???")) {
                    qt.setZ("0");
                } else if(qt.getZ().equals("???")) {
                    qt.setZ("1");
                } else {
                    qt.setZ("2");
                }
            }
        }

        return getDataTable(this.iZpPosterStaffViewService.findZpPosterStaffLists(request, zpPosterStaffView,quertTabList));
    }

    @PostMapping("excel1")
    public void export1(QueryRequest request, HttpServletResponse response, String pid, Integer applystate, String ids) throws FebsException {
        try {
            List<StaffInfoDataExport> exportList = new ArrayList<>();
            List<String> idList = null;
            if (StrUtil.isNotBlank(ids)) {
                String[] arr_ids = ids.split(StringPool.COMMA);
                if (arr_ids.length > 0) {
                    idList = ListUtil.toList(arr_ids);
                }
            }
            exportList = this.iZpPosterStaffViewService.excelData(pid, applystate, idList);
            if (exportList.size() > 0) {
                ExportExcelUtils.exportExcel(response, StaffInfoDataExport.class, exportList, "????????????");
            }

        } catch (Exception e) {
            message = "??????Excel??????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("send")
    public FebsResponse sendMsg(String pid, Integer applystate, String sendContent, String ids) throws FebsException {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            List<StaffInfoDataExport> exportList = new ArrayList<>();
            List<String> idList = null;
            if (StrUtil.isNotBlank(ids)) {
                String[] arr_ids = ids.split(StringPool.COMMA);
                if (arr_ids.length > 0) {
                    idList = ListUtil.toList(arr_ids);
                }
            }
            this.iZpPosterStaffViewService.SubSmsData(pid, applystate,sendContent, idList);
            success = 1;

        } catch (Exception e) {
            message = "??????????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
        map.put("success", success);
        map.put("message", message);
        return new FebsResponse().data(map);
    }


}
