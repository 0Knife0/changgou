package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

public class FastDFSClient {

    /**
     * 初始化tracker信息
     */
    static {
        try {
            String fileName = new ClassPathResource("fdfs_client.conf").getPath();
            ClientGlobal.init(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     *
     * @param fastDFSFile
     * @return
     */
    public static String[] upload(FastDFSFile fastDFSFile) throws Exception {
        // 获取作者
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author", fastDFSFile.getAuthor());

        String[] uploadResults = null;
        /**
         * 文件上传后的返回值
         * uploadResults[0] 文件上传的组信息
         * uploadResults[1] 文件存储路径
         */
        try {
            // 创建TrackerClient对象
            TrackerClient trackerClient = new TrackerClient();

            // 通过TrackerClient对象拿到TrackerServer信息
            TrackerServer trackerServer = trackerClient.getConnection();

            // 通过TrackerServer对象获取StorageClient
            StorageClient storageClient = new StorageClient(trackerServer, null);
            // 实现文件上传
            uploadResults = storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), meta_list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadResults;
    }


}
