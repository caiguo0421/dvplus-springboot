package cn.sf_soft.file.service;


import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.ftp.FtpClientHelper;
import cn.sf_soft.common.util.ImageUtil;
import cn.sf_soft.user.model.SysUsers;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.rmi.ServerException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <ul>
 * <li>
 * 上传的文件统一上传到ftp服务器
 * </li>
 * <li>
 * 如果是图片文件，会在本地缓存文件夹生成缩略图
 * </li>
 * <li>
 * 当查看图片时，判断ftp服务器上是否已生成压缩过的图片文件，如果没有则下载原图压缩并上传到ftp，
 * 压缩图片命名：COMPRS.原图名称
 * </li>
 * </ul>
 */
@Service("fileService")
public class FileService {

    final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    /**
     * 附件的临时路径
     * caigx
     */
    private static final String ATTACHMENT_TMP_FOLDER_PREFIX = "/attachment_cache/";

    /**
     * FTP的upload目录
     */
    private static final String FTP_UPLOAD_PATH = "/Uploads/";

    private static final String COMPRS = "[COMPRS]";

    @Autowired
    FtpClientHelper ftpClientHelper;

//    @Autowired
//    private ServletContext servletContext;

    /**
     * 程序中兼容了FTP处理和本地文件处理，现在默认为FTP处理;不在配置文件中体现,注解删除
     */
    private String ftpService = "on";
    private String attachmentPath;

    //ftp config
    @Value("${ftp.hostname}")
    private String hostName;
    @Value("${ftp.port}")
    private int port;
    @Value("${ftp.username}")
    private String userName;
    @Value("${ftp.password}")
    private String password;
    @Value("${ftp.attachment.path}")
    private String ftpAttachmentPath;

    private static final String THUMBNAIL = "thumbnail";
    public static final String TMP_DIR = System.getProperty("java.io.tmpdir");
    private static String separatorChar = "/";


    /**
     * 将pic放入FTP服务器中
     *
     * @param user
     * @param prefix 上传到FTP文件名前缀：审批单：dcoumentNo，其他： 主键字段
     * @param pics
     * @return
     */
    public String addPicsToFtp(SysUsers user, String prefix, String pics) {
        return addAttachmentsToFtp(user, prefix, pics);
    }


    /**
     * 添加附件到FTP
     *
     * @param user
     * @param prefix      上传到FTP文件名前缀：审批单：dcoumentNo，其他： 主键字段
     * @param attachments
     * @return
     */
    public String addAttachmentsToFtp(SysUsers user, String prefix, String attachments) {
        if (StringUtils.isBlank(attachments)) {
            return null;
        }
        if (StringUtils.isBlank(prefix)) {
            throw new ServiceException("添加附件到FTP出错：主键字段的值为空");
        }

        String[] attachmentArray = StringUtils.split(attachments, ";");
        String tmpFolder = getAttachmentTmpFolder(user.getUserNo());

        FTPClient ftpClient = null;
        try {
            ftpClient = ftpClientHelper.login(hostName, port, userName, password);
            for (int i = 0; i < attachmentArray.length; i++) {
                String attachment = attachmentArray[i];
                File picFile = new File(tmpFolder + "/" + attachment);
                if (picFile.exists()) {
                    String targetName = getValidAttachmentFileName(ftpClient, prefix, attachment);
                    String ftpFileName = StringUtils.isBlank(prefix) ? targetName : prefix + targetName;
                    //必须先切换FTP工作目录
                    ftpClientHelper.changeWorkingDirectory(ftpClient, ftpAttachmentPath + FTP_UPLOAD_PATH);
                    //推送到FTP
                    ftpClientHelper.uploadFile(ftpClient, picFile.getAbsolutePath(), new String(ftpFileName.getBytes(ftpClientHelper.getLocalCharset()), ftpClientHelper.getServerCharset()));
                    //处理完成后，删除临时文件
                    picFile.delete();
                    attachmentArray[i] = targetName;
                    logger.debug(String.format("附件上传到ftp：源=%s，FTP文件名=%s", picFile.getAbsolutePath(), targetName));
                } else {
                    //查看一下FTP中是否有此文件
                    String filePath = FTP_UPLOAD_PATH + attachment;
                    String path = getAbsolutePath(filePath);
                    FTPFile file = ftpClient.mlistFile(path);
                    if (file == null) {
                        //文件既不在临时文件夹下，也不在FTP下，先暂时不处理，只记录一个warn日志
                        logger.warn(String.format("文件%s既不在临时文件夹下，也不在FTP下", attachment));
                    }
                }
            }
        } catch (Exception ex) {
            throw new ServiceException("文件放入FTP时出现问题", ex);
        } finally {
            try {
                ftpClientHelper.closeConnection(ftpClient);
            } catch (Exception e2) {
                //do nothing
            }
        }
        return joinWithoutBlank(attachmentArray, ";");
    }

    private static String joinWithoutBlank(String[] array, String separator) {
        if (array == null) {
            return null;
        } else {
            if (separator == null) {
                separator = "";
            }
            StringBuilder buf = new StringBuilder();
            for (int i = 0; i < array.length; i++) {
                if (StringUtils.isNotBlank(array[i])) {
                    buf.append(array[i]);
                    if (i < array.length - 1) {
                        buf.append(separator);
                    }
                }
            }
            return buf.toString();
        }
    }

    private String getValidAttachmentFileName(FTPClient ftpClient, String prefix, String attachmentFileName) throws Exception {
        if (StringUtils.isEmpty(attachmentFileName)) {
            throw new ServiceException("文件名为空");
        }

        int i = attachmentFileName.lastIndexOf(".");
        String mainName = prefix + attachmentFileName.substring(0, i);
        String extName = attachmentFileName.substring(i);

        final String filePrefixName = StringUtils.isBlank(prefix) ? mainName : prefix + mainName;

        //必须先切换FTP工作目录
        ftpClientHelper.changeWorkingDirectory(ftpClient, ftpAttachmentPath + FTP_UPLOAD_PATH);

        FTPFile[] ftpFiles = ftpClient.listFiles(null, new FTPFileFilter() {
            @Override
            public boolean accept(FTPFile ftpFile) {

                if (ftpFile.getName().startsWith(filePrefixName)) {
                    return true;
                }
                return false;
            }
        });

        return recusionGetValidAttachmentFileName(ftpFiles, mainName, extName, prefix, 0);
    }

    private String recusionGetValidAttachmentFileName(FTPFile[] ftpFiles, String mainName, String extName, String prefix, int index) {
        String newAttachmentFileName = "";
        if (index == 0) {
            newAttachmentFileName = mainName + extName;
        } else {
            newAttachmentFileName = String.format("%s_%d%s", mainName, index, extName);
        }

        if (ftpFiles == null || ftpFiles.length == 0) {
            return newAttachmentFileName;
        }
        for (FTPFile ftpFile : ftpFiles) {
            String newFileName = StringUtils.isBlank(prefix) ? newAttachmentFileName : prefix + newAttachmentFileName;
            if (newFileName.equalsIgnoreCase(ftpFile.getName())) {
                return recusionGetValidAttachmentFileName(ftpFiles, mainName, extName, prefix, ++index);
            }
        }

        return newAttachmentFileName;
    }


    /**
     * 生成ImgTmp的缩略图
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public InputStream getThumbnailFromImgTmp(String fileName, String userNo) throws Exception {
        if (StringUtils.isEmpty(fileName)) {
            throw new ServiceException("获取文件失败：文件名为空");
        }
        String tmpImgPath = getAttachmentTmpFolder(userNo) + "//" + fileName;
        String thumbnailPath = getAttachmentTmpFolder(userNo) + "//" + fileName + "-thumbnail";
        String floderName = thumbnailImage(tmpImgPath, thumbnailPath);
        InputStream is = new FileInputStream(thumbnailPath);
        return is;
    }


    /**
     * 附件缓存文件夹路径 $tomcat_home/temp/attachment_cache/项目名/userNo
     *
     * @param userNo
     * @return
     */
    public String getAttachmentTmpFolder(String userNo) {
        File root = new File(ServletActionContext.getServletContext().getRealPath("/"));
        //图片临时文件夹路径 放到 tmp目录下
        String destRootPath = replaceSeparator(TMP_DIR + File.separator + ATTACHMENT_TMP_FOLDER_PREFIX + File.separator + root.getName() + File.separator + userNo);
        File folder = new File(destRootPath);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdirs();
        }
        return folder.getAbsolutePath();
    }


    private boolean ftpServiceRunning() {
        if ("ON".equalsIgnoreCase(this.ftpService)) {
            return true;
        }
        return false;
    }


    /**
     * 获取文件的最后更新时间
     *
     * @param filePath 文件的相对路径（/Uploads/xxxx.png）
     * @return
     * @throws Exception
     */
    public Long getLastModifyTime(String filePath) throws Exception {
        if (ftpServiceRunning()) {
            FTPClient ftpClient = null;
            try {
                ftpClient = ftpClientHelper.login(hostName, port, userName, password);
                logger.debug("获取文件最后更新时间");
                logger.debug("hostname:{}", hostName);
                logger.debug("userName:{}", userName);
                logger.debug("filePath:{}", filePath);
                //ftp上的绝对路径(/A4S/Server/Uploads/xxxx.png)
                String path = getAbsolutePath(filePath);
                logger.debug("ftp路径，filePath:{}", path);
                //编码路径
                path = new String(path.getBytes(ftpClientHelper.getLocalCharset()), ftpClientHelper.getServerCharset());

                logger.debug("LocalCharset:{}", ftpClientHelper.getLocalCharset());
                logger.debug("ServerCharset:{}", ftpClientHelper.getServerCharset());
                logger.debug("编码后的path:{}", path);


                FTPFile file = ftpClient.mlistFile(path);
                if (null == file) {
                    throw new ServerException(String.format("未找到指定的文件(%s)", filePath));
                }
                logger.debug("FTPFile:{}", file);
                logger.debug("file.getTimestamp():{}", file.getTimestamp());
                /*logger.debug("file.getTimestamp().getTime():{}", file.getTimestamp().getTime());
                logger.debug("file.getTimestamp().getTime().getTime():{}", file.getTimestamp().getTime().getTime());*/
                //在特殊环境中file.getTimestamp()返回空
                if (null != file.getTimestamp()) {
                    return file.getTimestamp().getTime().getTime();
                } else {
                    //返回的时间有两种格式：'20190318025124.328'和'213 20190318062027'。第二种格式的213是ftp回复代码
                    String modifyTime = ftpClient.getModificationTime(path);
                    logger.debug("modifyTime:{}", modifyTime);
                    String[] str = modifyTime.split(" ");
                    String time = null;
                    if (str.length == 1) {
                        time = str[0];
                    } else {
                        time = str[1];
                    }
                    if (time.indexOf(".") == -1) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                        Date modificationTime = dateFormat.parse(time);
                        return modificationTime.getTime();
                    } else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
                        Date modificationTime = dateFormat.parse(time);
                        return modificationTime.getTime();
                    }
                }

            } finally {
                ftpClientHelper.closeConnection(ftpClient);
            }
        } else {
            String path = getAbsolutePath(filePath);
            File file = new File(path);
            if (file.isFile() && file.exists()) {
                return file.lastModified();
            }
        }
        return null;
    }

    /**
     * 获取完整的相对路径
     *
     * @param filePath 文件的相对路径(/Uploads/xxxx.png)
     * @return 完整相对路径(/ A4S / Server / Uploads / xxxx.png)
     */
    private String getAbsolutePath(String filePath) {
        StringBuffer buffer = new StringBuffer();
        if (this.ftpServiceRunning()) {
            buffer.append(this.ftpAttachmentPath);
        } else {
            buffer.append(this.attachmentPath);
        }
        buffer.append(filePath);
        String path = replaceSeparator(buffer.toString());
        if (path.indexOf(separatorChar) == 0) {
            path = path.substring(1);
        }
        return path;
    }


    /**
     * 格式化路径，将\和/做相应的转换
     *
     * @param path 路径
     * @return 转换后的路径
     */
    private String replaceSeparator(String path) {
        return replaceSeparator(path, separatorChar);
    }

    private String replaceSeparator(String path, String separatorChar) {
        return path.replaceAll("(\\\\+)|(/+)", separatorChar);
    }

    /**
     * 获取原图缩略图的路径
     *
     * @param filePath 原图路径及其文件名（缩略图命名规则：'原图路径及其文件名'+'~'+'thumbnail'+'原图文件名最后更新时间'）
     * @return
     */
    public String getThumbnailAbsolutePath(String filePath, String thumbnailFolder) throws Exception {
        Long lastModifyTime = getLastModifyTime(filePath);
        if (null != lastModifyTime) {
            String fileName = getFileName(filePath);
            int index = fileName.lastIndexOf(".");
            StringBuffer buffer = new StringBuffer();
            buffer.append(thumbnailFolder);
            buffer.append(separatorChar).append(fileName.substring(0, index));
            buffer.append("~").append(THUMBNAIL).append("~").append(lastModifyTime).append(fileName.substring(index));
            return buffer.toString();
        }
        return null;
    }


    /**
     * 创建或更改缩略图<p>
     * /1、首先判断服务器上是否已经生成了缩略图
     * a、存在：直接下载
     * b、不存在：删除原缩略图并生成缩略图，保存到服务端并下载
     *
     * @param oriFileRelativePath 文件的相对路径(/Uploads/xxxx.png)
     */
    public InputStream createOrUpdateThumbnail(String oriFileRelativePath) throws Exception {
        File root = new File(ServletActionContext.getServletContext().getRealPath("/"));
        String tmpFolder = replaceSeparator(TMP_DIR + File.separator + root.getName());
        File tmpFolderFile = new File(tmpFolder);
        if (!tmpFolderFile.exists()) {
            tmpFolderFile.mkdirs();
        }
        //替换路径分隔符
        oriFileRelativePath = replaceSeparator(oriFileRelativePath);
        //获取缩略图的绝对路径(本地)
        String thumbnailPath = this.getThumbnailAbsolutePath(oriFileRelativePath, tmpFolder);
        File thumbFile = new File(thumbnailPath);
        if (thumbFile.isFile() && thumbFile.exists()) {
            return new FileInputStream(thumbFile);
        }
        //原图在ftp上的绝对路径(/A4S/Server/Uploads/xxxx.png)
        String oriFilePath = this.getAbsolutePath(oriFileRelativePath);
        if (ftpServiceRunning()) {
            FTPClient ftpClient = null;
            String localFilePath = null;
            try {
                ftpClient = ftpClientHelper.login(hostName, port, userName, password);
                //编码路径
                oriFilePath = new String(oriFilePath.getBytes(ftpClientHelper.getLocalCharset()), ftpClientHelper.getServerCharset());
                //下载原文件并生成缩略图
                try {
                    String fileFolder = getFolder(oriFilePath);
                    String fileName = getFileName(oriFilePath);
                    String localFileName = ftpClientHelper.downloadFile(ftpClient, fileFolder, fileName, tmpFolder);
                    localFilePath = tmpFolder + File.separator + localFileName;
                    String p = thumbnailImage(localFilePath, thumbnailPath);
                    if (StringUtils.isNotEmpty(p)) {
                        File file = new File(p);
                        InputStream in = new FileInputStream(file);
                        return in;
                    }
                } finally {
                    //删除原图
                    deleteFile(localFilePath);
                }
                return null;
            } finally {
                ftpClientHelper.closeConnection(ftpClient);
            }
        } else {
            String p = thumbnailImage(oriFilePath, thumbnailPath);
            if (StringUtils.isNotEmpty(p)) {
                return new FileInputStream(new File(p));
            }
        }
        return null;
    }

    private String thumbnailImage(String fileAbsolutePath, String thumbnailPath) throws Exception {
        return ImageUtil.thumbnailImage(fileAbsolutePath, thumbnailPath, 500, 400, "", false);
    }

    private String getFolder(String filePath) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("获取%s的目录", filePath));
        }

        int index = filePath.lastIndexOf(separatorChar);
        return filePath.substring(0, index);
    }

    private String getFileName(String filePath) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("获取%s的文件名", filePath));
        }
        int index = filePath.lastIndexOf(separatorChar);
        return filePath.substring(index + 1);
    }

    public InputStream getInputStream(String filepath) throws Exception {
        if (this.ftpServiceRunning()) {
            //首先判断是否是图片文件，如果是图片文件则下载压缩后的图
            if (ImageUtil.isImage(filepath)) { //如果是图片文件
                String compressImagePath = createCompressImageIfExists(filepath);
                if (StringUtils.isNotEmpty(compressImagePath)) {
                    filepath = compressImagePath;
                }
            }
            FTPClient ftpClient = null;
            try {
                ftpClient = ftpClientHelper.login(hostName, port, userName, password);
                String path = this.getAbsolutePath(filepath);
                //判断FTP上是否存在压缩图片
                FTPFile file = ftpClient.mlistFile(new String(path.getBytes(ftpClientHelper.getLocalCharset()), ftpClientHelper.getServerCharset()));
                if (null != file) {   //如果ftp上不存在压缩图片则需要将原图压缩上传
                    InputStream in = ftpClient.retrieveFileStream(new String(path.getBytes(ftpClientHelper.getLocalCharset()), ftpClientHelper.getServerCharset()));
                    return new ByteArrayInputStream(cloneInputStream(in).toByteArray());
                }else{
                    logger.info("ftp上未找到文件：{}", path);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(String.format("获取ftp文件流出错（userName:%s;password:%s;hostName:%s;port:%s;ftpAttachmentPath:%s;filePath:%s）", userName, password, hostName, port, ftpAttachmentPath, filepath), e);
            } finally {
                ftpClientHelper.closeConnection(ftpClient);
            }
            /*URL u = null;
            try {
                String ftpUrl = "ftp://" + userName + ":" + password + "@" + hostName + ":" + port + "/" + ftpAttachmentPath + "/" + java.net.URLEncoder.encode(new String(filepath.getBytes("GBK")));
                logger.debug("ftpUrl:{}", ftpUrl);
                u = new URL(ftpUrl);
                return u.openStream();
            } catch (IOException e) {
                logger.error(String.format("获取ftp文件流出错（userName:%s;password:%s;hostName:%s;port:%s;ftpAttachmentPath:%s;filePath:%s）", userName, password, hostName, port, ftpAttachmentPath, filepath), e);
            }*/
        } else {
            File file = new File(attachmentPath + File.separator + filepath);
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                logger.error(String.format("获取文件流出错（attachmentPath：%s;filePath:%s）", attachmentPath, filepath));
            }
        }
        return null;
    }

    private static ByteArrayOutputStream cloneInputStream(InputStream input) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private String getAbsoluteWithCompressPath(String filePath) {
        String path = getAbsolutePath(filePath);
        return getCompressImagePath(path);
    }

    /**
     * 获取压缩图片的路径字符串
     *
     * @param imagePath 图片路径
     * @return
     */
    private String getCompressImagePath(String imagePath) {
        StringBuffer buffer = new StringBuffer(imagePath);
        int index = buffer.lastIndexOf(".");
        if (index >= 0) {
            buffer.insert(index, COMPRS);
        } else {
            buffer.append(COMPRS);
        }
        return buffer.toString();
    }

    /**
     * 如果压缩图片不存在则生成并上传压缩图片到ftp
     *
     * @param imagePath 原图在ftp上的相对路径（例如/Uploads/xxxx.png）
     * @return 压缩图片在ftp上的相对路径
     * @throws Exception
     */
    private String createCompressImageIfExists(String imagePath) throws Exception {
        String compressImagePath = this.getCompressImagePath(imagePath);
        FTPClient ftpClient = null;
        try {
            ftpClient = ftpClientHelper.login(hostName, port, userName, password);
            //获取压缩图片在ftp上的相对路径(/A4S6/Server/Uploads/xxxx[COMPRS].png)
            String path = getAbsoluteWithCompressPath(imagePath);
            //判断FTP上是否存在压缩图片
            FTPFile file = ftpClient.mlistFile(new String(path.getBytes(ftpClientHelper.getLocalCharset()), ftpClientHelper.getServerCharset()));
            logger.info("file:{}", file == null);
            if (null == file) {   //如果ftp上不存在压缩图片则需要将原图压缩上传
                File root = new File(ServletActionContext.getServletContext().getRealPath("/"));
                String tmpFolder = replaceSeparator(TMP_DIR + File.separator + root.getName());
                File tmpFolderFile = new File(tmpFolder);
                if (!tmpFolderFile.exists()) {
                    tmpFolderFile.mkdirs();
                }
                //原图在ftp上的相对路径(/A4S6/Server/Uploads/xxxx.png)
                String oriImagePath = this.getAbsolutePath(imagePath);
                //下载原文件并生成缩略图
                String localImagePath = null;   //下载到本地的原图路径
                String localCompressImagePath = null;   //压缩后的本地图片路径
                try {
                    //按照ftp的字符编码转换后的原图路径
                    String oriImagePathEncoded = new String(oriImagePath.getBytes(ftpClientHelper.getLocalCharset()), ftpClientHelper.getServerCharset());
                    //原图目录
                    String fileFolder = getFolder(oriImagePathEncoded);
                    //原图文件名称
                    String fileName = getFileName(oriImagePathEncoded);
                    //下载原图
                    String localFileName = ftpClientHelper.downloadFile(ftpClient, fileFolder, fileName, tmpFolder);
                    //设置本地原图完整路径
                    localImagePath = tmpFolder + separatorChar + localFileName;

                    localCompressImagePath = this.getCompressImagePath(tmpFolder + separatorChar + localFileName);
                    //压缩原始图片
                    String compressImageName = this.getFileName(localCompressImagePath);
                    localCompressImagePath = ImageUtil.compressImage(localImagePath, localCompressImagePath);
                    if (StringUtils.isNotEmpty(localCompressImagePath)) {
                        //必须先切换FTP工作目录
                        ftpClientHelper.changeWorkingDirectory(ftpClient, ftpAttachmentPath + FTP_UPLOAD_PATH);
                        File localCompressImage = new File(localCompressImagePath);
                        //推送到FTP
                        ftpClientHelper.uploadFile(ftpClient, localCompressImage.getAbsolutePath(), compressImageName);
                        return compressImagePath;
                    }
                } finally {
                    //删除原图
                    deleteFile(localImagePath);
                    deleteFile(localCompressImagePath);
                }
            } else {
                return compressImagePath;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ftpClientHelper.closeConnection(ftpClient);
        }
        return null;
    }

    /**
     * 删除指定文件路径的文件，不包含文件夹
     *
     * @param filePath
     */
    private void deleteFile(String filePath) {
        try {
            if (StringUtils.isNotEmpty(filePath)) {
                File f = new File(filePath);
                if (f.isFile() && f.exists()) {
                    f.delete();
                }
            }
        } catch (Exception ex) {
        }
    }

}
