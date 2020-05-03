package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;

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
    public static String[] upload(FastDFSFile fastDFSFile) {
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

    /**
     * 获取文件信息
     *
     * @param groupName      组名
     * @param remoteFileName 路径名
     * @return 文件信息
     */
    public static FileInfo getFile(String groupName, String remoteFileName) throws Exception {
        // 创建StorageClient对象
        StorageClient storageClient = getStorageClient();
        // 通过StorageClient对象获取文件信息
        return storageClient.get_file_info(groupName, remoteFileName);
    }

    /**
     * 文件下载
     *
     * @param groupName      组名
     * @param remoteFileName 路径名
     * @return
     * @throws Exception
     */
    public static ByteArrayInputStream downFile(String groupName, String remoteFileName) throws Exception {
        // 创建StorageClient对象
        StorageClient storageClient = getStorageClient();
        // 通过StorageClient进行文件下载
        byte[] bytes = storageClient.download_file(groupName, remoteFileName);
        return new ByteArrayInputStream(bytes);
    }

    /**
     * 文件删除
     *
     * @param groupName      组名
     * @param remoteFileName 路径名
     */
    public static void deleteFile(String groupName, String remoteFileName) throws Exception {
        // 创建StorageClient对象
        StorageClient storageClient = getStorageClient();
        // 通过StorageClient进行文件删除
        storageClient.delete_file(groupName, remoteFileName);
    }

    /**
     * 获取组信息
     *
     * @param groupName
     * @return
     * @throws Exception
     */
    public static StorageServer getStorages(String groupName) throws Exception {
        // 创建TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        // 通过TrackerClient对象拿到TrackerServer信息
        TrackerServer trackerServer = trackerClient.getConnection();
        // 通过TrackerClient获取Storage信息
        return trackerClient.getStoreStorage(trackerServer, groupName);
    }

    /**
     * 根据文件组名和文件存储路径获取Storage服务的IP、端口信息
     *
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static ServerInfo[] getServerInfo(String groupName, String remoteFileName) throws Exception {
        // 创建TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        // 通过TrackerClient对象拿到TrackerServer信息
        TrackerServer trackerServer = trackerClient.getConnection();
        // 获取服务信息
        return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
    }

    /**
     * 获取Tracker服务地址
     *
     * @return
     */
    public static String getTrackerUrl() throws Exception {
        // 创建TrackerServer对象
        TrackerServer trackerServer = getTrackerServer();
        // 获取服务地址
        return "http://" + trackerServer.getInetSocketAddress().getHostString() + ":" +
                ClientGlobal.getG_tracker_http_port();
    }

    /**
     * 获取TrackerServer
     *
     * @return
     * @throws Exception
     */
    public static TrackerServer getTrackerServer() throws Exception {
        // 创建TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        // 通过TrackerClient对象拿到TrackerServer信息
        return trackerClient.getConnection();
    }

    /**
     * 获取StorageClient
     *
     * @return
     * @throws Exception
     */
    public static StorageClient getStorageClient() throws Exception {
        // 创建TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        // 通过TrackerServer创建StorageClient
        return new StorageClient(trackerClient.getConnection(), null);
    }

    public static void main(String[] args) throws Exception {
        /*FileInfo fileInfo = getFile("group1", "M00/00/00/wKgA3l6tmi-AXtt-ACH9jSOXClM913.png");
        System.out.println(fileInfo.getSourceIpAddr());
        System.out.println(fileInfo.getFileSize());*/

        /*ByteArrayInputStream is = downFile("group1", "M00/00/00/wKgA3l6tmi-AXtt-ACH9jSOXClM913.png");
        FileOutputStream fos = new FileOutputStream("D://1.png");
        byte[] buffer = new byte[1024];
        while (is.read(buffer) != -1) {
            fos.write(buffer);
        }
        fos.flush();
        fos.close();
        is.close();*/

        deleteFile("group1", "M00/00/00/wKgA3l6tmi-AXtt-ACH9jSOXClM913.png");
    }
}
