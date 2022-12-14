package cc.mrbird.febs.com.controller;

import cc.mrbird.febs.com.entity.*;
import cc.mrbird.febs.com.service.IComFileService;
import cc.mrbird.febs.com.service.IComTypeService;
import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.properties.FebsProperties;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.export.pdf.StaffInfoPdf;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.zp.entity.StaffEssay;
import cc.mrbird.febs.zp.entity.StaffInfo;
import cc.mrbird.febs.zp.entity.ZpStaffInfo;
import cc.mrbird.febs.zp.service.IZpStaffInfoService;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.net.multipart.UploadFile;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;

/**
 * @author viki
 * @since 2019-11-14
 */
@Slf4j
@Validated
@RestController
@RequestMapping("comFile")

public class ComFileController extends BaseController {

    private String message;
    @Autowired
    public IComFileService iComFileService;
    @Autowired
    private FebsProperties febsProperties;

    @Autowired
    IComTypeService iComTypeService;

    @Autowired
    IZpStaffInfoService iZpStaffInfoService;

    /**
     * ??????????????????
     *
     * @param comFile ????????????
     * @return
     */
    @GetMapping
    @RequiresPermissions("comFile:view")
    public Map<String, Object> List(QueryRequest request, ComFile comFile) {
        return getDataTable(this.iComFileService.findComFiles(request, comFile));
    }

    /**
     * ??????????????????
     *
     * @return
     */
    @Log("??????/??????")
    @PostMapping
    @RequiresPermissions("comFile:add")
    public void addComFile(@Valid ComFile comFile) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            //comFile.setCreateUserId(currentUser.getUserId());
            this.iComFileService.createComFile(comFile);
        } catch (Exception e) {
            message = "??????/????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * ??????????????????
     *
     * @return
     */
    @Log("??????")
    @PutMapping
    @RequiresPermissions("comFile:update")
    public void updateComFile(@Valid ComFile comFile) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            //comFile.setModifyUserId(currentUser.getUserId().toString());
            this.iComFileService.updateComFile(comFile);
        } catch (Exception e) {
            message = "????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("??????")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("comFile:delete")
    public void deleteComFiles(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iComFileService.deleteComFiles(arr_ids);
        } catch (Exception e) {
            message = "????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("comFile:export")
    public void export(QueryRequest request, ComFile comFile, HttpServletResponse response) throws FebsException {
        try {
            List<ComFile> comFiles = this.iComFileService.findComFiles(request, comFile).getRecords();
            ExcelKit.$Export(ComFile.class, response).downXlsx(comFiles, false);
        } catch (Exception e) {
            message = "??????Excel??????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel1")
    public void export1(QueryRequest request, ComFile comFile, HttpServletResponse response) throws FebsException {
        try {
            List<ComFile> comFiles = this.iComFileService.findListComFile(comFile.getId(), null);
            ExcelWriter writer = ExcelUtil.getWriter(true);
            writer.addHeaderAlias("id", "ID");
            writer.addHeaderAlias("clientName", "???????????????");
            writer.addHeaderAlias("serverName", "???????????????");

            writer.write(comFiles, false);
            //response???HttpServletResponse??????
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            //test.xls??????????????????????????????????????????????????????????????????????????????
            response.setHeader("Content-Disposition", "attachment;filename=test.xls");
            ServletOutputStream out = response.getOutputStream();

            writer.flush(out, true);
            // ??????writer???????????????
            writer.close();
            //????????????????????????Servlet???
            IoUtil.close(out);
        } catch (Exception e) {
            message = "??????Excel??????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public ComFile detail(@NotBlank(message = "{required}") @PathVariable String id) {
        ComFile comFile = this.iComFileService.getById(id);
        return comFile;
    }

    private boolean isSuffixTrue(String refType, String suffix) {
        if (refType != "" && !refType.equals("")) {
            if (refType.equals("photo")) {
                if (suffix.equals(".jpg") || suffix.equals(".jpeg") || suffix.equals(".png")) {
                    return true;
                } else {
                    return false;
                }
            } else if (refType.equals("pdf")) {
                if (suffix.equals(".pdf")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (suffix.equals(".jpg") || suffix.equals(".jpeg") || suffix.equals(".png")
                        || suffix.equals(".doc") || suffix.equals(".docx") || suffix.equals(".rar") ||
                        suffix.equals(".zip") || suffix.equals(".pdf")) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    @PostMapping("uploadPdf")
    public FebsResponse UploadPdfs(@RequestParam("file") MultipartFile file, InUploadFile inUploadFile) throws FebsException {
        if (file.isEmpty()) {
            throw new FebsException("?????????");
        }

        Date thisDate = new Date();
        OutComFile outComFile = new OutComFile();
        String strId = inUploadFile.getId();
        String fileName2 = file.getOriginalFilename();  // ?????????
        String suffixName = fileName2.substring(fileName2.lastIndexOf("."));  // ?????????
        String refType = inUploadFile.getRefType();
        suffixName = suffixName.toLowerCase();
        if (this.isSuffixTrue("pdf", suffixName)) {
            String filePath = febsProperties.getUploadPath(); // ??????????????????
            String fileName = UUID.randomUUID().toString() + suffixName;
            File dest = new File(filePath + refType + "/" + fileName);
            String Id = UUID.randomUUID().toString();
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
                ComFile cf = new ComFile();
                cf.setId(Id);
                cf.setCreateTime(thisDate);
                cf.setClientName(fileName2);//??????????????????
                cf.setServerName(fileName);
                cf.setRefTabId(strId);
                cf.setRefType(refType);
                cf.setRefTabTable(inUploadFile.getRefTab());
                iComFileService.createComFile(cf);

            } catch (IOException e) {
                throw new FebsException(e.getMessage());
            }
            String fileUrl = febsProperties.getBaseUrl() + "/uploadFile/" + refType + "/" + fileName;

            outComFile.setSuccess(1);
            outComFile.setUid(Id);
            outComFile.setName(fileName2);
            outComFile.setStatus("done");
            outComFile.setUrl(fileUrl);
            outComFile.setThumbUrl(fileUrl);
            outComFile.setSerName(fileName);
        } else {
            outComFile.setSuccess(0);
            outComFile.setMessage("??????????????????????????????????????????PDF??????.");
        }
        return new FebsResponse().put("data", outComFile);
    }


    @PostMapping("upload")
    public FebsResponse Uploads(@RequestParam("file") MultipartFile file, InUploadFile inUploadFile) throws FebsException {
        if (file.isEmpty()) {
            throw new FebsException("?????????");
        }

        Date thisDate = new Date();
        OutComFile outComFile = new OutComFile();
        String strId = inUploadFile.getId();
        String fileName2 = file.getOriginalFilename();  // ?????????
        String suffixName = fileName2.substring(fileName2.lastIndexOf("."));  // ?????????
        suffixName = suffixName.toLowerCase();
        if (this.isSuffixTrue(inUploadFile.getRefType(), suffixName)) {
            String filePath = febsProperties.getUploadPath(); // ??????????????????
            String fileName = UUID.randomUUID().toString() + suffixName;
            File dest = new File(filePath + inUploadFile.getRefType() + "/" + fileName);
            String Id = UUID.randomUUID().toString();
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
                ComFile cf = new ComFile();
                cf.setId(Id);
                cf.setCreateTime(thisDate);
                cf.setClientName(fileName2);//??????????????????
                cf.setServerName(fileName);
                cf.setRefTabId(strId);
                cf.setRefType(inUploadFile.getRefType());
                cf.setRefTabTable(inUploadFile.getRefTab());
                iComFileService.createComFile(cf);

            } catch (IOException e) {
                throw new FebsException(e.getMessage());
            }
            String fileUrl = febsProperties.getBaseUrl() + "/uploadFile/" + inUploadFile.getRefType() + "/" + fileName;

            outComFile.setSuccess(1);
            outComFile.setUid(Id);
            outComFile.setName(fileName2);
            outComFile.setStatus("done");
            outComFile.setUrl(fileUrl);
            outComFile.setThumbUrl(fileUrl);
            outComFile.setSerName(fileName);
        } else {
            if (inUploadFile.getRefType().equals("photo")) {
                message = "jpg???png";
            } else {
                message = "jpg???png???doc???docx???rar???zip???pdf";
            }
            outComFile.setSuccess(0);
            outComFile.setMessage("??????????????????????????????????????????" + message + "??????.");
        }
        return new FebsResponse().put("data", outComFile);
    }

    @PostMapping("uploadImg")
    public FebsResponse UploadImgs(@RequestParam("file") MultipartFile file, InUploadFile inUploadFile) throws FebsException {
        if (file.isEmpty()) {
            throw new FebsException("?????????");
        }

        Date thisDate = new Date();
        OutComFile outComFile = new OutComFile();
        String strId = inUploadFile.getId();
        String fileName2 = file.getOriginalFilename();  // ?????????
        String suffixName = fileName2.substring(fileName2.lastIndexOf("."));  // ?????????
        suffixName = suffixName.toLowerCase();
        if (this.isSuffixTrue(inUploadFile.getRefType(), suffixName)) {
            String filePath = febsProperties.getUploadPath(); // ??????????????????
            String fileName = UUID.randomUUID().toString() + suffixName;
            File dest = new File(filePath + inUploadFile.getRefType() + "/" + fileName);
            String Id = UUID.randomUUID().toString();
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                List<ComFile> fileList = this.iComFileService.findListComFile(strId, inUploadFile.getRefType());
                if (fileList.size() > 0) {
                    int count = this.iComFileService.deleteComFile(fileList.get(0).getId());
                    if (count > 0) {
                        String fileUrl = filePath + fileList.get(0).getRefType() + "/" + fileList.get(0).getServerName();
                        deleteFile(fileUrl);
                    }
                }
                file.transferTo(dest);
                ComFile cf = new ComFile();
                cf.setId(Id);
                cf.setCreateTime(thisDate);
                cf.setClientName(fileName2);//??????????????????
                cf.setServerName(fileName);
                cf.setRefTabId(strId);
                cf.setRefType(inUploadFile.getRefType());
                cf.setRefTabTable(inUploadFile.getRefTab());
                iComFileService.createComFile(cf);

            } catch (IOException e) {
                throw new FebsException(e.getMessage());
            }
            String fileUrl = febsProperties.getBaseUrl() + "/uploadFile/" + inUploadFile.getRefType() + "/" + fileName;

            outComFile.setSuccess(1);
            outComFile.setUid(Id);
            outComFile.setName(fileName2);
            outComFile.setStatus("done");
            outComFile.setUrl(fileUrl);
            outComFile.setThumbUrl(fileUrl);
            outComFile.setSerName(fileName);
        } else {
            outComFile.setSuccess(0);
            outComFile.setMessage("??????????????????????????????????????????jpg???png??????.");
        }
        return new FebsResponse().put("data", outComFile);
    }

    @Log("??????")
    @PostMapping("deleteImg")
    public FebsResponse deleteImgComFile(InUploadFile inUploadFile) {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            String strId = inUploadFile.getId();
            ComFile comFile = this.iComFileService.findComFileById(strId);
            if (comFile != null) {
                int count = this.iComFileService.deleteComFile(inUploadFile.getId());
                if (count > 0) {
                    success = 1;
                    String filePath = febsProperties.getUploadPath(); // ??????????????????
                    String fileUrl = filePath + comFile.getRefType() + "/" + comFile.getServerName();
                    deleteFile(fileUrl);
                }
            }
        } catch (Exception e) {
            message = "????????????.";
            log.error(message, e);
        }
        map.put("message", message);
        map.put("success", success);
        return new FebsResponse().data(map);
    }

    @PostMapping("fileImgZip")
//    @RequiresPermissions("comFile:imgExport")
    public void fileImgZip(QueryRequest request, InUploadFile inUploadFile, HttpServletResponse response) throws FebsException {
        String path = febsProperties.getUploadPath();
        String address = path + "/";
        String fileName = UUID.randomUUID().toString() + ".zip";
        Random r = new Random();
        int nxt = r.nextInt(10000);
        String filePath = address + nxt + ".zip";
        try {
            List<ComFile> list = this.iComFileService.findAppealResultComFiles(inUploadFile);
            if (list.size() > 0) {
                File[] fileUtils = new File[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    ComFile comFile = list.get(i);
                    String t = comFile.getRefTabTable();
                    File file = new File(address + comFile.getRefType() + "/" + comFile.getServerName());
                    fileUtils[i] = file;
                }
                ZipUtil.zip(FileUtil.file(filePath), false, fileUtils);
                //ZipUtil.zip(address, filePath);
                this.downFile(response, filePath, fileName, true);
            }
        } catch (Exception e) {
            message = "????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    private void downFile(HttpServletResponse response, String filePath, String fileName, boolean isDel) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                InputStream ins = new FileInputStream(filePath);
                BufferedInputStream bins = new BufferedInputStream(ins);// ?????????????????????
                OutputStream outs = response.getOutputStream();// ??????????????????IO???
                BufferedOutputStream bouts = new BufferedOutputStream(outs);
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));

                int bytesRead = 0;
                byte[] buffer = new byte[512];
                //??????????????????????????????
                while ((bytesRead = bins.read(buffer, 0, 512)) != -1) {
                    bouts.write(buffer, 0, bytesRead);
                }
                bouts.flush();// ?????????????????????flush()??????
                ins.close();
                bins.close();
                outs.close();
                bouts.close();
            } else {
//                response.sendRedirect("../error.jsp");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (isDel) {
                this.deleteFile(filePath);
            }
        }
    }

    @PostMapping("downloadFile")
    public void findFiles(QueryRequest request, String id, HttpServletResponse response) throws FebsException {
        try {
            ZpStaffInfo obj = iZpStaffInfoService.getById(id);
            if (obj != null) {
                StaffInfo info = iZpStaffInfoService.getStaffInfo(obj);
                List<ComFile> fileList = new ArrayList<>();
                List<ComFile> essayList = new ArrayList<>();
                // ????????????????????? ??????????????? ????????????????????????????????????
                List<String> refTabIdList = new ArrayList<>();
                refTabIdList.add(id);

                List<ComFile> mainList = this.iComFileService.findFileByInRefTabId(refTabIdList);
                if (mainList.size() > 0) {
                    fileList.addAll(mainList);
                }
                refTabIdList = new ArrayList<>();
                for (StaffEssay item : info.getEssays()) {
                    refTabIdList.add(item.getId());
                }
                if (refTabIdList.size() > 0) {
                    essayList = this.iComFileService.findFileByInRefTabId(refTabIdList);
                }
                if (essayList.size() > 0) {
                    fileList.addAll(essayList);
                }
                StaffInfoPdf pdf = new StaffInfoPdf();
//                    staff photo essay
                String filePath = febsProperties.getUploadPath(); // ??????????????????
                String fileName = filePath + "down/" + UUID.randomUUID().toString() + ".pdf";
                String name = UUID.randomUUID().toString() + ".pdf";
                String mergeFileName = filePath + "down/" + name;
                File dest = new File(fileName);
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                String imgUrl = filePath;
                List<String> mergeAddPdfList = new ArrayList<>();
                mergeAddPdfList.add(fileName);
                String suffixName = "";
                for (ComFile item : fileList) {
                    if (item.getRefTabId().equals(id)) {
                        if (item.getRefType().equals("photo")) {
                            imgUrl = imgUrl + item.getRefType() + "/" + item.getServerName();
                        }
                        if (item.getRefType().equals("staff")) {
                            suffixName = item.getServerName().substring(item.getServerName().lastIndexOf("."));  // ?????????
                            suffixName = suffixName.toLowerCase();
                            if (suffixName.equals(".pdf")) {
                                mergeAddPdfList.add(filePath + item.getRefType() + "/" + item.getServerName());
                            }
                        }
                    } else {
                        suffixName = item.getServerName().substring(item.getServerName().lastIndexOf("."));  // ?????????
                        suffixName = suffixName.toLowerCase();
                        if (suffixName.equals(".pdf")) {
                            mergeAddPdfList.add(filePath + item.getRefType() + "/" + item.getServerName());
                        }
                    }
                }
                if(fileList.size() > 0) {
                    long imgcount = fileList.stream().filter(s -> s.getRefType().equals("photo")).count();
                    if(imgcount == 0) {
                        imgUrl = "";
                    }
                } else {
                    imgUrl = "";
                }
                pdf.writeStaffPdf(fileName, mergeFileName, mergeAddPdfList, imgUrl, info);
                if (mergeAddPdfList.size() == 1) {
                    mergeFileName = mergeAddPdfList.get(0);
                }
                this.downFile(response, mergeFileName, name, true);

                this.deleteFile(fileName);
            }
        } catch (Exception e) {
            message = "????????????.";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("??????")
    @PostMapping("delFile")
    public FebsResponse deleteComFile(InUploadFile inUploadFile) {
        int success = 0;
        ModelMap map = new ModelMap();
        try {
            ComFile comFile = this.iComFileService.findComFileById(inUploadFile.getId());
            if (comFile != null) {
                int count = this.iComFileService.deleteComFile(inUploadFile.getId());
                if (count > 0) {
                    success = 1;
                    String filePath = febsProperties.getUploadPath(); // ??????????????????
                    String fileUrl = filePath + inUploadFile.getRefType() + "/" + comFile.getServerName();
                    deleteFile(fileUrl);
                    message = "??????????????????.";
                } else {
                    message = "??????????????????.";
                }

            } else {
                message = "?????????????????????.";
            }
        } catch (Exception e) {
            message = "??????????????????.";
        }
        map.put("success", success);
        map.put("message", message);
        return new FebsResponse().data(map);
    }

    @GetMapping("findFileList")
    public FebsResponse findFileList(String refTabId, String refType) {
        List<OutComFile> outList = new ArrayList<>();
        List<ComFile> list = this.iComFileService.findListComFile(refTabId, refType);
        for (ComFile item : list) {
            OutComFile outComFile = new OutComFile();
            outComFile.setUid(item.getId());
            outComFile.setName(item.getClientName());
            outList.add(outComFile);
        }
        return new FebsResponse().data(outList);
    }

    @PostMapping("listImgComFile")
    public FebsResponse findImgListComFiles(InUploadFile inUploadFile) {
        List<OutComFile> outList = new ArrayList<>();
        try {
            List<ComFile> list = this.iComFileService.findListComFile(inUploadFile.getId(), inUploadFile.getRefType());
            if (list.size() > 0) {
                for (ComFile item : list) {
                    String fileUrl = "uploadFile/" + item.getRefType() + "/" + item.getServerName();
                    OutComFile outComFile = new OutComFile();
                    outComFile.setUid(item.getId());
                    outComFile.setName(item.getClientName());
                    outComFile.setStatus("done");
                    outComFile.setUrl(fileUrl);
                    outComFile.setSerName(item.getServerName());
                    outComFile.setThumbUrl(fileUrl);
                    outList.add(outComFile);
                }
            }
        } catch (Exception e) {
            log.error(message, e);
        }
        return new FebsResponse().data(outList);
    }

    @PostMapping("uploadFile")
    public FebsResponse uploadPubFile(@RequestParam("file") MultipartFile file, InUploadFile inUploadFile) throws FebsException {
        if (file.isEmpty()) {
            throw new FebsException("?????????");
        }
        Date thisDate = new Date();
        OutComFile outComFile = new OutComFile();
//        User currentUser = FebsUtil.getCurrentUser();
        String upFileName = file.getOriginalFilename();  // ?????????
        String suffixName = upFileName.substring(upFileName.lastIndexOf("."));  // ?????????
        String filePath = febsProperties.getUploadPath(); // ??????????????????
        String SerFileName = UUID.randomUUID().toString() + suffixName; // ????????????
        File dest = new File(filePath + inUploadFile.getRefType() + "/" + SerFileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        String Id = UUID.randomUUID().toString();
        try {
            file.transferTo(dest);
            ComFile cf = new ComFile();
            cf.setId(Id);
            cf.setCreateTime(thisDate);
            cf.setClientName(upFileName);//??????????????????
            cf.setServerName(SerFileName);
            cf.setRefTabId(inUploadFile.getId());
            cf.setRefType(inUploadFile.getRefType());
            cf.setRefTabTable(inUploadFile.getRefTab());
            iComFileService.createComFile(cf);
        } catch (IOException e) {
            throw new FebsException(e.getMessage());
        }
        outComFile.setSuccess(1);
        outComFile.setUid(Id);
        outComFile.setName(upFileName);

        return new FebsResponse().data(outComFile);
    }


    /**
     * ??????????????????
     *
     * @param sPath ???????????????????????????
     * @return ??????????????????????????????true???????????????false
     */
    public boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // ??????????????????????????????????????????
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
                flag = true;
            }
        } else {
            flag = true;
        }
        return flag;
    }

    @PostMapping("weUploadFile")
    public WangEditor weUploadFileImg(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        try {
            // ??????????????????
            String realPath = request.getSession().getServletContext()
                    .getRealPath("");
            InputStream inputStream = multipartFile.getInputStream();
            String path = febsProperties.getUploadPath(); // ??????????????????

            // ???????????????????????????upload?????????????????????
            String uploadPath = path + "upload";
            // ??????????????????
            String upFileName = multipartFile.getOriginalFilename();  // ?????????
            String suffixName = upFileName.substring(upFileName.lastIndexOf("."));  // ?????????
            String filename = UUID.randomUUID().toString() + suffixName; // ????????????
            // ??????????????????????????????????????????upload?????????
            File file = new File(uploadPath, filename);
            FileUtils.copyInputStreamToFile(inputStream, file);
            // ????????????????????????
            String url = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort() + "/uploadFile/upload/" + filename;
            String[] strs = {url};
            WangEditor we = new WangEditor(strs);
            return we;
        } catch (IOException e) {
            log.error("??????????????????", e);
        }
        return null;

    }

    @PostMapping("weUploadFiles")
    public WangEditor uploadFile(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
        try {
            List list = new ArrayList();

            for (MultipartFile multipartFile : files) {
                // ??????????????????
                String realPath = request.getSession().getServletContext()
                        .getRealPath("");
                InputStream inputStream = multipartFile.getInputStream();

                //String contextPath = request.getServletContext().getContextPath();
                //logger.info(contextPath);
                // ???????????????????????????
                String path = realPath;
                // ???????????????????????????upload?????????????????????
                String uploadPath = path + "upload";
                // ??????????????????
                String filename = Calendar.getInstance().getTimeInMillis() + "image";
                // ??????????????????????????????????????????upload?????????
                File file = new File(uploadPath, filename);
                FileUtils.copyInputStreamToFile(inputStream, file);
                // ????????????????????????
                String url = request.getScheme() + "://" + request.getServerName()
                        + ":" + request.getServerPort() + "/upload/" + filename;
                list.add(url);

            }
            //ArrayList<String> list=new ArrayList<String>();

            String[] strings = new String[list.size()];

            list.toArray(strings);
            WangEditor we = new WangEditor(strings);
            return we;
        } catch (IOException e) {
            log.error("??????????????????", e);
        }
        return null;
    }

}
