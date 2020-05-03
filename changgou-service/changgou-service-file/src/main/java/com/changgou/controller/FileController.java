package com.changgou.controller;

import com.changgou.file.FastDFSFile;
import com.changgou.util.FastDFSClient;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
public class FileController {

    /**
     * 文件上传
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
        // 封装FastDFSFile
        FastDFSFile fastDFSFile = new FastDFSFile(
                file.getOriginalFilename(),
                file.getBytes(),
                StringUtils.getFilenameExtension(file.getOriginalFilename()));
        // 上传文件
        String[] uploads = FastDFSClient.upload(fastDFSFile);
        return "192.168.0.222"+"/"+uploads[0]+"/"+uploads[1];
    }
}
