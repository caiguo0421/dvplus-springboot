package cn.sf_soft.common.ftp;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class FtpClientHelper {
    final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FtpClientHelper.class);

    private String logInfo = "ftp客户端错误";

    /** 本地字符编码 */
    private String localCharset = "GBK";

    // FTP协议里面，规定文件名编码为iso-8859-1
    private String serverCharset = "ISO-8859-1";

    private String workDir;

    /**
     * 连接超时时间
     */
    private int connectTimeout = 5;
    /**
     * 默认超时时间
     */
    private int defaultTimeout = 5;
    /**
     * 请求数据超时时间
     */
    private int dataTimeout = 5;

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setDefaultTimeout(int defaultTimeout) {
        this.defaultTimeout = defaultTimeout;
    }

    public void setDataTimeout(int dataTimeout) {
        this.dataTimeout = dataTimeout;
    }

    public String getLocalCharset(){
        return this.localCharset;
    }
    public String getServerCharset(){
        return this.serverCharset;
    }

    public String getWorkDir(){return this.workDir;}

    /**
     * <p>
     * ftp登录
     * </p>
     *
     * @param ipAddress
     *            ftp服务器地址
     * @param uname
     *            登录名
     * @param pass
     *            密码
     * @throws Exception
     */
    public FTPClient login(String ipAddress,int port,String uname, String pass)
            throws Exception {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(connectTimeout * 1000);
        ftpClient.setDataTimeout(dataTimeout * 1000);
        ftpClient.setDefaultTimeout(defaultTimeout * 1000);

        logger.debug("FTP Connect, "+uname+" "+connectTimeout+" "+dataTimeout+" "+defaultTimeout);

        InetAddress inetAddress = InetAddress.getByName(ipAddress);
        try{
//            FTPClientConfig conf=new FTPClientConfig(FTPClientConfig.SYST_NT);
//            ftpClient.configure(conf);
//            ftpClient.setControlEncoding("UTF-8");
            ftpClient.connect(inetAddress,port);
        }catch(Exception e){

        }

        int reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            String stmp = ftpClient.getReplyString();
            this.closeConnection(ftpClient);
            throw new Exception("不能连接到ftp服务器: "+stmp);
        }

        if (!ftpClient.login(uname, pass)) {
            String stmp = ftpClient.getReplyString();
            closeConnection(ftpClient);
            throw new Exception("登录ftp服务器失败"+stmp);
        }else{
            if (FTPReply.isPositiveCompletion(ftpClient.sendCommand(
                    "OPTS UTF8", "ON"))) {// 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
                localCharset = "UTF-8";
            }
            ftpClient.setControlEncoding(localCharset);
            ftpClient.enterLocalPassiveMode();// 设置被动模式
            this.workDir = ftpClient.printWorkingDirectory();
            //ftpClient.setFileType(getTransforModule());// 设置传输的模式
        }
        ftpClient.enterLocalPassiveMode();
        return ftpClient;
    }
    /**
     * <p>
     * ftp上传文件
     * </p>
     *
     * @param srcUrl
     *            文件路径
     * @param targetFname
     *            目标路径
     * @throws Exception 操作异常
     */
    public void uploadFile(FTPClient ftpClient,String srcUrl, String targetFname)
            throws Exception {
        File srcFile = new File(srcUrl);
        if (!srcFile.exists()) {
            logger.error("路径为srcUrl的文件不存在！");
            throw new Exception("路径为srcUrl的文件不存在！");
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(srcFile);
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("GBK");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            boolean flag = ftpClient.storeFile(targetFname, fis);
            if(!flag){
                throw new Exception("ftp上传文件失败，文件名为："+srcUrl);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        finally{
            if(null != fis){
                fis.close();
            }

        }
    }

    /**
     * Description: 下载文件
     *
     * @param ftpFolder
     *            FTP文件路径
     * @param fileName
     *            文件名称
     * @param localFolder
     *            本地保存路径
     * @return
     * @throws IOException
     * @return 本地保存的文件名
     */
    public String downloadFile(FTPClient ftpClient, String ftpFolder, String fileName,
                               String localFolder) throws Exception {
        if(StringUtils.isNotEmpty(ftpFolder)){
            changeWorkingDirectory(ftpClient,ftpFolder);
        }

        File localFile = new File(localFolder + "/" + "~"+fileName);
        OutputStream is = new FileOutputStream(localFile);
        try{
            boolean flag=ftpClient.retrieveFile(fileName, is);
            is.close();
            if(!flag){
                throw new Exception("ftp下载文件失败, "+ftpClient.getReplyString()+", 文件："+ftpFolder+"/"+fileName);
            }
            //ftpClient.rename(fileName, fileName+".downloaded");
            //return renameFile(localFile, localFolder, fileName);
            return renameFile(localFile, localFolder, fileName);
        }catch(Exception e){
            throw e;
        }finally{
            try{
                is.close();
            }catch(Exception e1){}
        }
    }
    private static int maxVer = 99;
    /**
     * 将文件改名
     * @param localFile 改名前的文件
     * @param localPath 改名后的文件路径
     * @param fileName 改后的文件名 若存在改文件，则自动加"_1","_2"
     * @return 更改后的文件名
     * @throws Exception
     */
    private String renameFile(File localFile, String localPath, String fileName) throws Exception {
        File destFile =new File(localPath+"/"+fileName);
        if (!destFile.exists()){
            localFile.renameTo(destFile);
            return fileName;
        }
        int i=destFile.getName().lastIndexOf(".");
        String mainName=fileName.substring(0,i);
        String extName=fileName.substring(i);
        i = 1;
        while(destFile.exists()){
            fileName = mainName+"_" + i++ + extName;
            destFile=new File(localPath + "/" + fileName);
//            if(i>maxVer)
//                throw new Exception("未找到可用的转储文件，支持的最大转储版本："+maxVer);
        }
        localFile.renameTo(destFile);
        return fileName;
    }

    /**
     * Description: 读取文件列表
     * @param ftpClient
     * @param remotePath
     *            FTP文件路径
     * @throws Exception
     * @return 文件名列表 不包含"." 和".." 列表按String的自然排序
     */
    public List<String> list(FTPClient ftpClient, String remotePath) throws Exception {
        List<String> list = new ArrayList<>();

        changeWorkingDirectory(ftpClient,remotePath);

        FTPFile[] ftpFiles = ftpClient.listFiles();
        for (FTPFile ftpFile : ftpFiles) {
            String name = ftpFile.getName();
            if (!".".equals(name) && !"..".equals(name)) {
                list.add(name);
            }
        }
        //排序
        Collections.sort(list);
        return list;
    }

    /**
     * Description: 在服务器上下载文件
     *
     * @param srcFname
     *            文件路径
     * @return true || false
     * @throws Exception
     */
    public void removeFile(FTPClient ftpClient,String srcPath, String srcFname)
            throws Exception {
        boolean flag = false;
        try {
            changeWorkingDirectory(ftpClient,srcPath);

            if (ftpClient != null) {
                flag = ftpClient.deleteFile(srcFname);
                if(!flag){
                    throw new Exception("ftp删除文件失败，文件路径为："+srcPath+"/"+srcFname);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 改名
     *
     * @param fromFName
     * @param toFName
     * @return true || false
     * @throws IOException 网络异常
     * @throws Exception 操作异常
     */
    public void rename(FTPClient ftpClient,String fromFName, String toFName) throws Exception {
        boolean flag = false;
        try {
            if (ftpClient != null) {
                flag = ftpClient.rename(fromFName, toFName);
                if(!flag){
                    throw new Exception("ftp删除文件失败，文件名为："+fromFName);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     *
     * 新建路径
     *
     * @param pathname
     * @return true || false
     * @throws IOException 网络异常
     * @throws Exception 操作异常
     */
    public void makeDirectory(FTPClient ftpClient,String pathname) throws Exception {
        boolean flag = false;
        if (ftpClient != null) {
            try {
                flag = ftpClient.makeDirectory(pathname);
                if(!flag){
                    throw new Exception("ftp创建路径失败，路径为："+pathname);
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
    }
    /**
     * 切换工作目录
     * @param ftpClient
     * @param remotePath 服务端路径
     * @throws Exception
     */
    public void changeWorkingDirectory(FTPClient ftpClient,String remotePath) throws Exception{
        if(!remotePath.startsWith("/")){
            remotePath="/"+remotePath;
        }
        try {
            boolean flag= ftpClient.changeWorkingDirectory(remotePath);
            if(!flag){
                flag= ftpClient.changeWorkingDirectory(this.getWorkDir()+remotePath);
                if(!flag) {
                    throw new Exception("ftp服务器不存在该路径！" + remotePath);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }

    }
    /**
     * 关闭连接
     *
     * @throws IOException
     */
    public void closeConnection(FTPClient ftpClient) throws IOException {
        if (ftpClient != null) {
            if (ftpClient.isConnected()) {
                try {
                    logger.debug("FTP Disconnect,  Thread ID："+Thread.currentThread().getId());
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                    throw e;
                }
            }
        }
    }

}
