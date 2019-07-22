package cn.sf_soft.file.action;

import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.file.service.FileService;
import cn.sf_soft.user.model.SysUsers;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;


import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.dispatcher.multipart.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;


/*@Controller(value="fileAction")
@Namespace("/File")
@ParentPackage("struts-default")
@Scope(value="prototype")*/
public class FileAction extends BaseAction {
    final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    @Autowired
    private FileService fileService;

    private String fileName;

    private String filePath;

    private String userNo;

    private String mimeType;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }


    @Access(pass = true)
    public String uploadImg() throws IOException {
//        MultiPartRequestWrapper request = (MultiPartRequestWrapper) ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
////        File desertFile = null;
////        Enumeration<String> parmNames =  request.getFileParameterNames();
////        while(parmNames.hasMoreElements()){
////            String file = parmNames.nextElement();
////            File[] files = request.getFiles(file);
////            for (int i = 0; i < files.length; i++){
////                String desertFolder = fileService.getAttachmentTmpFolder(userNo);
////                String fileName = UUID.randomUUID().toString() + FileTypeUtil.mimeMapingSuffix(mimeType); //防止照片重复，所有的图片都重命名
////                desertFile = new File(desertFolder+"//"+fileName);
////                FileUtils.copyFile(files[i],desertFile);
////            }
////        }
////
////        if(desertFile!=null){
////            Map<String, Object> rtnData = new HashMap<String, Object>(2);
////            rtnData.put("fileName",desertFile.getName());
////            setResponseData(rtnData);
////        }
////        return SUCCESS;

        return uploadAttachment();
    }


    @Access(pass = true)
    public String uploadAttachment() throws IOException {
        MultiPartRequestWrapper request = (MultiPartRequestWrapper) ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
        File desertFile = null;
        Enumeration<String> parmNames = request.getFileParameterNames();
        while (parmNames.hasMoreElements()) {
            String fileKey = parmNames.nextElement();
            UploadedFile[] files = request.getFiles(fileKey);

            for (int i = 0; i < files.length; i++) {
                String desertFolder = fileService.getAttachmentTmpFolder(userNo);

                String fileName = fileKey.substring(fileKey.lastIndexOf("/") + 1);
                int index = fileName.lastIndexOf(".");
                String mainName = fileName.substring(0, index);
                String extName = fileName.substring(index);
                String desertFileName = desertFolder + "//" + getValidAttachmentTmpFile(desertFolder, mainName, extName, 0);
                desertFile = new File(desertFileName);

                logger.debug(String.format("文件上传到临时文件夹：源=%s,目标=%s", files[i].getAbsolutePath(), desertFile.getAbsolutePath()));
                FileUtils.copyFile(new File(files[i].getAbsolutePath()), desertFile);
            }
        }

        if (desertFile != null) {
            Map<String, Object> rtnData = new HashMap<String, Object>(2);
            rtnData.put("fileName", desertFile.getName());
            setResponseData(rtnData);
        }
        return SUCCESS;
    }

    private String getValidAttachmentTmpFile(String desertFolderName, String mainName, String extName, int index) throws IOException {
        String attachmentFileName = "";
        if (index == 0) {
            attachmentFileName = mainName + extName;
        } else {
            attachmentFileName = String.format("%s_%d%s", mainName, index, extName);
        }

        File desertFolder = new File(desertFolderName);

        if (desertFolder.exists() && desertFolder.isDirectory()) {
            File[] files = desertFolder.listFiles();
            if (files != null && files.length > 0) {
                for (File ftpFile : files) {
                    if (attachmentFileName.equalsIgnoreCase(ftpFile.getName())) {
                        return getValidAttachmentTmpFile(desertFolderName, mainName, extName, ++index);
                    }
                }
            }
        }
        return attachmentFileName;
    }


    /**
     * 获得临时文件的缩略图
     *
     * @return
     * @throws Exception
     */
    @Access(pass = true)
    public String getThumbnailFromImgTmp() throws Exception {

        SysUsers user = (SysUsers) getAttributeFromSession(Constant.Attribute.SESSION_USER);
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);
        OutputStream out = new BufferedOutputStream(response.getOutputStream());
        response.reset();

        response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/octet-stream");
        response.addHeader("Connection", "close");
        InputStream is = fileService.getThumbnailFromImgTmp(fileName, user.getUserNo());
        int count = 0;
        int pics = 0;
        byte[] buffer = new byte[1024 * 32];
        List<byte[]> pool = new ArrayList<byte[]>();
        List<Integer> sizes = new ArrayList<Integer>();
        while ((pics = is.read(buffer)) >= 0) {
            count += pics;
            sizes.add(pics);
            pool.add(buffer);
            buffer = new byte[1024 * 32];
        }
        response.setContentLength(count);
        for (int i = 0; i < sizes.size(); i++) {
            out.write(pool.get(i), 0, sizes.get(i));
        }

        if (is != null) {
            is.close();
        }
        if (out != null) {
            out.flush();
            out.close();
        }

        return SUCCESS;
    }

    /**
     * 判断文件名是否合法（Win平台）
     *
     * @param fileName
     * @return
     */
    public static boolean isValidFileName(String fileName) {
        if (fileName == null || fileName.length() > 255)
            return false;
        else
            return fileName.matches("[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$");
    }

    /**
     * 检查附件是否过期<p>
     *
     * @return
     * @throws Exception
     * @parm: filepath（附件相对路径）
     */
    @Access(pass = true)
    public String getAttachmentLastModifyTime() {
        if (StringUtils.isNotEmpty(filePath)) {
            try {
                Long lastModifyTime = fileService.getLastModifyTime(filePath);
                setResponseData(lastModifyTime);
            } catch (Exception ex) {
                logger.error(String.format("获取文件'%s'的最后更新时间出错。", filePath), ex);
            }
        }
        return SUCCESS;
    }


    /**
     * 获取文件缩略图，只支持图片文件
     *
     * @return
     * @parm filepath 文件相对路径
     */
    @Access(pass = true)
    public String getAttachmentOfThumbnail() {
        if (StringUtils.isNotEmpty(filePath)) {
            logger.debug(String.format("缩略图文件的filePath:%s", filePath));
            try {
                InputStream in = fileService.createOrUpdateThumbnail(filePath);
                if (null != in) {
                    try {
                        downLoad(in);
                    } finally {
                        if (null != in) {
                            try {
                                in.close();
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                logger.error("获取缩略图出错", ex);
            }
        } else {
            logger.error("缩略图文件的filePath为空");
        }
        return null;
    }


    @Access(pass = true)
    public String getAttachment() throws IOException {
        if (StringUtils.isNotEmpty(filePath)) {
            logger.debug(String.format("文件的filePath:%s", filePath));
            InputStream is = null;
            try {
                is = this.fileService.getInputStream(filePath);
                if (null != is)
                    this.downLoad(is);
            } catch (Exception ex) {
                logger.error("下载附件出错", ex);
            } finally {
                if (null != is) {
                    try {
                        is.close();
                    } catch (Exception e) {
                    }
                }
            }
        } else {
            logger.error("文件的filePath为空");
        }
        return null;
    }

    private void downLoad(InputStream in) throws IOException {
        HttpServletResponse response = (HttpServletResponse)
                ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);
        HttpServletRequest request = (HttpServletRequest)
                ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);

        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        try {
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType("application/octet-stream");
            response.addHeader("Connection", "close");
            int count = 0;
            int pics = 0;
            byte[] buffer = new byte[1024 * 32];
            List<byte[]> pool = new ArrayList<byte[]>();
            List<Integer> sizes = new ArrayList<Integer>();
            while ((pics = in.read(buffer)) >= 0) {
                count += pics;
                sizes.add(pics);
                pool.add(buffer);
                buffer = new byte[1024 * 32];
            }
            response.setContentLength(count);
            for (int i = 0; i < sizes.size(); i++) {
                toClient.write(pool.get(i), 0, sizes.get(i));
            }
        } finally {
            if (null != toClient) {
                try {
                    toClient.flush();
                } catch (Exception e) {
                }
                try {
                    toClient.close();
                } catch (Exception e) {
                }
            }
        }

    }


}
