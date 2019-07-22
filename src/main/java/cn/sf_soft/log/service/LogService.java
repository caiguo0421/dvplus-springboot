package cn.sf_soft.log.service;

import cn.sf_soft.common.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class LogService {

    final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
    /**
     * 默认的日志路径
     */
    private static final String DEFAULT_LOG_PATH = "/js/test/";

    @Value("${log.path:}")
    private String logPath;


    public String getLogPath() {
        if (StringUtils.isEmpty(logPath)) {
            logPath = DEFAULT_LOG_PATH;
        }
        return logPath;
    }

    public List<Map<String, Object>> getFileInfoList() {
        List<File> fileList = listFile();
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                if (f1.isDirectory() && f2.isFile())
                    return -1;
                if (f1.isFile() && f2.isDirectory())
                    return 1;
                //按最后修改时间排序
                if (f2.lastModified() - f1.lastModified() > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        List<Map<String, Object>> fileInfoList = new ArrayList<Map<String, Object>>();
        for (File file : fileList) {
            Map<String, Object> fileInfo = new HashMap<String, Object>();
            fileInfo.put("name", file.getName());
            fileInfo.put("lastModified", new Timestamp(file.lastModified()));
            fileInfo.put("length", getNetFileSizeDescription(file.length()));
            fileInfoList.add(fileInfo);
        }

        return fileInfoList;
    }


    public void cleanLog() {
        List<File> fileList = listFile();
        for (File file : fileList) {
            if (file.lastModified() <= System.currentTimeMillis() - 1000 * 3600 * 24 * 30) {//删除30天前的数据
                logger.debug(String.format("删除日志文件：%S", file.getAbsolutePath()));
                file.delete();
            }
        }
    }

    public static String getNetFileSizeDescription(long size) {
        StringBuffer bytes = new StringBuffer();
        DecimalFormat format = new DecimalFormat("###.00");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        } else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        } else if (size >= 1024) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        } else if (size < 1024) {
            if (size <= 0) {
                bytes.append("0B");
            } else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();
    }


    private List<File> listFileOld() {
        logPath = getLogPath();
        try {
            logger.debug(ResourceUtils.getURL(logPath).getPath());
        } catch (Exception ex) {

        }


        String fullPath = String.format("%s/%s", ServletActionContext.getServletContext().getRealPath("/"), logPath);
        File logFloder = new File(fullPath);
        if (!logFloder.exists() || !logFloder.isDirectory()) {
            throw new ServiceException(String.format("%s不存在或不是目录", logFloder.getAbsolutePath()));
        }
        List<File> fileList = Arrays.asList(logFloder.listFiles());
        return fileList;
    }


    private List<File> listFile() {
        logPath = getLogPath();
        File classFile = null;
        try {
            classFile = ResourceUtils.getFile("classpath:");
        } catch (Exception ex) {
            throw new ServiceException("获取classpath路径失败");
        }
        File rootFile = classFile.getParentFile().getParentFile();
        String fullPath = String.format("%s/%s", rootFile.getAbsolutePath(), logPath);
        logger.debug(String.format("日志路径文件：%s", fullPath));
        File logFloder = new File(fullPath);
        if (!logFloder.exists() || !logFloder.isDirectory()) {
            throw new ServiceException(String.format("%s不存在或不是目录", logFloder.getAbsolutePath()));
        }
        List<File> fileList = Arrays.asList(logFloder.listFiles());
        return fileList;
    }

}
