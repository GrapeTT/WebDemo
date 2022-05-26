package com.demo.web.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.demo.api.constant.ErrorCode;
import com.demo.api.model.Message;
import com.demo.web.base.BaseController;
import com.demo.common.tools.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：涛
 * @Descripition：文件上传下载接口类
 * @Date：2019/9/3 10:26
 */
@Controller
@RequestMapping("/file")
public class FileController extends BaseController {
    //上传目录
    @Value("${file.upload.path}")
    private String FILE_PATH;
    //文件大小
    @Value("${file.upload.maxsize}")
    private Long FILE_MAX_SIZE;
    //文件地址URL前缀
    @Value("${file.download.url.prefix}")
    private String URL_PREFIX;
    
    /**
     * @Description：文件上传（单个或批量）
     * @Author：涛
     * @Time：2019/9/3 10:26
     */
    @RequestMapping(value = "/upload", method = {RequestMethod.POST})
    public @ResponseBody Message<Map<String, String>> upload(HttpServletRequest request) {
        try {
            List<MultipartFile> fileList = ((MultipartHttpServletRequest) request).getFiles("file");
            if (CollectionUtil.isEmpty(fileList)) {
                return Message.failure("没有任何待上传的文件");
            }
            if(fileList.size() > 10) {
                return Message.failure("单次最多上传10个文件");
            }
            Message<Map<String, String>> message = Message.success();
            //key：文件名，value：文件url
            Map<String, String> fileMap = new HashMap<>();
            //记录上传成功文件数
            int successCount = 0;
            for (MultipartFile file : fileList) {
                //获取文件名
                String fileName = file.getOriginalFilename();
                if (file.isEmpty()) {
                    fileMap.put(fileName, "文件为空，上传失败");
                    continue;
                }
                //判断文件大小
                if(file.getSize() > FILE_MAX_SIZE) {
                    fileMap.put(fileName, "文件大于5M，上传失败");
                    continue;
                }
                //文件夹名称
                String folderName = DateUtils.format(new Date(), "yyyyMMddHHmmssSSS");
                //文件保存目录
                String filePath = FILE_PATH + folderName;
                File folder = new File(filePath);
                //判断文件目录是否存在
                if (!folder.exists()) {
                    //不存在，则创建
                    folder.mkdirs();
                }
                //文件保存路径
                filePath = filePath + "/" + fileName;
                try {
                    //保存文件
                    FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(filePath));
                } catch (Exception e) {
                    LOG.error("上传单个文件失败，fileName=" + fileName);
                    fileMap.put(fileName, "服务器内部错误，上传失败");
                }
                fileMap.put(fileName, URL_PREFIX + folderName + "/" + fileName);
                successCount++;
            }
            message.setData(fileMap);
            message.setMessage("文件总数：" + fileList.size() + "，上传成功：" + successCount + "，上传失败：" + (fileList.size() - successCount));
            return message;
        } catch (Exception e) {
            LOG.error(e, "文件上传失败");
            return Message.failure(ErrorCode.SERVER_ERROR);
        }
    }
    
    /**
     * @Description：文件下载
     * @Author：涛
     * @Time：2019/9/3 10:26
     */
    @RequestMapping(value = "/download/{folderName}/{fileName}", method = {RequestMethod.GET, RequestMethod.POST})
    public void upload(@PathVariable String folderName, @PathVariable String fileName, HttpServletResponse response) {
        if(StringUtils.isEmpty(folderName) || StringUtils.isEmpty(fileName)) {
            return;
        }
        InputStream inputStream = null;
        ServletOutputStream outputStream = null;
        try {
            //文件路径
            String filePath = FILE_PATH + folderName + "/" + fileName;
            File file = new File(filePath);
            if(!file.exists()) {
                return;
            }
            inputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            //设置响应头
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            //写出文件
            byte[] buffer = new byte[1024];
            int res = inputStream.read(buffer);
            while (res != -1) {
                outputStream.write(buffer, 0, res);
                res = inputStream.read(buffer);
            }
        } catch (Exception e) {
            LOG.error(e, "下载文件失败，folderName=" + folderName + "，fileName=" + fileName);
        } finally {
            try {
                if(inputStream != null) {
                    inputStream.close();
                }
                if(outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception e) {
                LOG.error(e, "关闭下载文件流失败");
            }
        }
    }
}
