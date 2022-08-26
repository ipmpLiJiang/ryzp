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
     * 分页查询数据
     *
     * @param comFile 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("comFile:view")
    public Map<String, Object> List(QueryRequest request, ComFile comFile) {
        return getDataTable(this.iComFileService.findComFiles(request, comFile));
    }

    /**
     * 跳转添加页面
     *
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("comFile:add")
    public void addComFile(@Valid ComFile comFile) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            //comFile.setCreateUserId(currentUser.getUserId());
            this.iComFileService.createComFile(comFile);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 跳转修改页面
     *
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("comFile:update")
    public void updateComFile(@Valid ComFile comFile) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            //comFile.setModifyUserId(currentUser.getUserId().toString());
            this.iComFileService.updateComFile(comFile);
        } catch (Exception e) {
            message = "修改成功";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("comFile:delete")
    public void deleteComFiles(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iComFileService.deleteComFiles(arr_ids);
        } catch (Exception e) {
            message = "删除成功";
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
            message = "导出Excel失败";
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
            writer.addHeaderAlias("clientName", "客户端名称");
            writer.addHeaderAlias("serverName", "服务端名称");

            writer.write(comFiles, false);
            //response为HttpServletResponse对象
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
            response.setHeader("Content-Disposition", "attachment;filename=test.xls");
            ServletOutputStream out = response.getOutputStream();

            writer.flush(out, true);
            // 关闭writer，释放内存
            writer.close();
            //此处记得关闭输出Servlet流
            IoUtil.close(out);
        } catch (Exception e) {
            message = "导出Excel失败";
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
            throw new FebsException("空文件");
        }

        Date thisDate = new Date();
        OutComFile outComFile = new OutComFile();
        String strId = inUploadFile.getId();
        String fileName2 = file.getOriginalFilename();  // 文件名
        String suffixName = fileName2.substring(fileName2.lastIndexOf("."));  // 后缀名
        String refType = inUploadFile.getRefType();
        suffixName = suffixName.toLowerCase();
        if (this.isSuffixTrue("pdf", suffixName)) {
            String filePath = febsProperties.getUploadPath(); // 上传后的路径
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
                cf.setClientName(fileName2);//客户端的名称
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
            outComFile.setMessage("上传文件的格式不正确，应上传PDF格式.");
        }
        return new FebsResponse().put("data", outComFile);
    }


    @PostMapping("upload")
    public FebsResponse Uploads(@RequestParam("file") MultipartFile file, InUploadFile inUploadFile) throws FebsException {
        if (file.isEmpty()) {
            throw new FebsException("空文件");
        }

        Date thisDate = new Date();
        OutComFile outComFile = new OutComFile();
        String strId = inUploadFile.getId();
        String fileName2 = file.getOriginalFilename();  // 文件名
        String suffixName = fileName2.substring(fileName2.lastIndexOf("."));  // 后缀名
        suffixName = suffixName.toLowerCase();
        if (this.isSuffixTrue(inUploadFile.getRefType(), suffixName)) {
            String filePath = febsProperties.getUploadPath(); // 上传后的路径
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
                cf.setClientName(fileName2);//客户端的名称
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
                message = "jpg，png";
            } else {
                message = "jpg，png，doc，docx，rar，zip，pdf";
            }
            outComFile.setSuccess(0);
            outComFile.setMessage("上传文件的格式不正确，应上传" + message + "格式.");
        }
        return new FebsResponse().put("data", outComFile);
    }

    @PostMapping("uploadImg")
    public FebsResponse UploadImgs(@RequestParam("file") MultipartFile file, InUploadFile inUploadFile) throws FebsException {
        if (file.isEmpty()) {
            throw new FebsException("空文件");
        }

        Date thisDate = new Date();
        OutComFile outComFile = new OutComFile();
        String strId = inUploadFile.getId();
        String fileName2 = file.getOriginalFilename();  // 文件名
        String suffixName = fileName2.substring(fileName2.lastIndexOf("."));  // 后缀名
        suffixName = suffixName.toLowerCase();
        if (this.isSuffixTrue(inUploadFile.getRefType(), suffixName)) {
            String filePath = febsProperties.getUploadPath(); // 上传后的路径
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
                cf.setClientName(fileName2);//客户端的名称
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
            outComFile.setMessage("上传文件的格式不正确，应上传jpg，png格式.");
        }
        return new FebsResponse().put("data", outComFile);
    }

    @Log("删除")
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
                    String filePath = febsProperties.getUploadPath(); // 上传后的路径
                    String fileUrl = filePath + comFile.getRefType() + "/" + comFile.getServerName();
                    deleteFile(fileUrl);
                }
            }
        } catch (Exception e) {
            message = "删除失败.";
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
            message = "导出失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    private void downFile(HttpServletResponse response, String filePath, String fileName, boolean isDel) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                InputStream ins = new FileInputStream(filePath);
                BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面
                OutputStream outs = response.getOutputStream();// 获取文件输出IO流
                BufferedOutputStream bouts = new BufferedOutputStream(outs);
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));

                int bytesRead = 0;
                byte[] buffer = new byte[512];
                //开始向网络传输文件流
                while ((bytesRead = bins.read(buffer, 0, 512)) != -1) {
                    bouts.write(buffer, 0, bytesRead);
                }
                bouts.flush();// 这里一定要调用flush()方法
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
                // 分开查询是因为 顺序问题， 主页简历附件要在文章之前
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
                if (fileList.size() > 0) {
                    StaffInfoPdf pdf = new StaffInfoPdf();
//                    staff photo essay
                    String filePath = febsProperties.getUploadPath(); // 上传后的路径
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
                                suffixName = item.getServerName().substring(item.getServerName().lastIndexOf("."));  // 后缀名
                                suffixName = suffixName.toLowerCase();
                                if (suffixName.equals(".pdf")) {
                                    mergeAddPdfList.add(filePath + item.getRefType() + "/" + item.getServerName());
                                }
                            }
                        } else {
                            suffixName = item.getServerName().substring(item.getServerName().lastIndexOf("."));  // 后缀名
                            suffixName = suffixName.toLowerCase();
                            if (suffixName.equals(".pdf")) {
                                mergeAddPdfList.add(filePath + item.getRefType() + "/" + item.getServerName());
                            }
                        }
                    }
                    pdf.writeStaffPdf(fileName, mergeFileName, mergeAddPdfList, imgUrl, info);
                    if (mergeAddPdfList.size() == 1) {
                        mergeFileName = mergeAddPdfList.get(0);
                    }
                    this.downFile(response, mergeFileName, name, true);

                    this.deleteFile(fileName);
                } else {
                    throw new FebsException("文件不存在下载失败.");
                }
            }
        } catch (Exception e) {
            message = "下载失败.";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除")
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
                    String filePath = febsProperties.getUploadPath(); // 上传后的路径
                    String fileUrl = filePath + inUploadFile.getRefType() + "/" + comFile.getServerName();
                    deleteFile(fileUrl);
                    message = "删除文件成功.";
                } else {
                    message = "删除文件失败.";
                }

            } else {
                message = "不存在文件数据.";
            }
        } catch (Exception e) {
            message = "删除文件异常.";
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
            throw new FebsException("空文件");
        }
        Date thisDate = new Date();
        OutComFile outComFile = new OutComFile();
//        User currentUser = FebsUtil.getCurrentUser();
        String upFileName = file.getOriginalFilename();  // 文件名
        String suffixName = upFileName.substring(upFileName.lastIndexOf("."));  // 后缀名
        String filePath = febsProperties.getUploadPath(); // 上传后的路径
        String SerFileName = UUID.randomUUID().toString() + suffixName; // 新文件名
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
            cf.setClientName(upFileName);//客户端的名称
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
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
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
            // 获取项目路径
            String realPath = request.getSession().getServletContext()
                    .getRealPath("");
            InputStream inputStream = multipartFile.getInputStream();
            String path = febsProperties.getUploadPath(); // 上传后的路径

            // 根目录下新建文件夹upload，存放上传图片
            String uploadPath = path + "upload";
            // 获取文件名称
            String upFileName = multipartFile.getOriginalFilename();  // 文件名
            String suffixName = upFileName.substring(upFileName.lastIndexOf("."));  // 后缀名
            String filename = UUID.randomUUID().toString() + suffixName; // 新文件名
            // 将文件上传的服务器根目录下的upload文件夹
            File file = new File(uploadPath, filename);
            FileUtils.copyInputStreamToFile(inputStream, file);
            // 返回图片访问路径
            String url = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort() + "/uploadFile/upload/" + filename;
            String[] strs = {url};
            WangEditor we = new WangEditor(strs);
            return we;
        } catch (IOException e) {
            log.error("上传文件失败", e);
        }
        return null;

    }

    @PostMapping("weUploadFiles")
    public WangEditor uploadFile(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
        try {
            List list = new ArrayList();

            for (MultipartFile multipartFile : files) {
                // 获取项目路径
                String realPath = request.getSession().getServletContext()
                        .getRealPath("");
                InputStream inputStream = multipartFile.getInputStream();

                //String contextPath = request.getServletContext().getContextPath();
                //logger.info(contextPath);
                // 服务器根目录的路径
                String path = realPath;
                // 根目录下新建文件夹upload，存放上传图片
                String uploadPath = path + "upload";
                // 获取文件名称
                String filename = Calendar.getInstance().getTimeInMillis() + "image";
                // 将文件上传的服务器根目录下的upload文件夹
                File file = new File(uploadPath, filename);
                FileUtils.copyInputStreamToFile(inputStream, file);
                // 返回图片访问路径
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
            log.error("上传文件失败", e);
        }
        return null;
    }

}
