package cn.sf_soft.common.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtil {

    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

	/**
     * <p>Title: thumbnailImage</p>
     * <p>Description: 根据图片路径生成缩略图 </p>
     * @param imagePath    原图片路径
     * @param w            缩略图宽
     * @param h            缩略图高
     * @param prevfix    生成缩略图的前缀
     * @param force        是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)
	 * @throws Exception 
     */
    public static String thumbnailImage(String imagePath, String thumbImagePath, int w, int h, String prevfix, boolean force) throws Exception {
        File imgFile = new File(imagePath);
        if (imgFile.exists()) {
            try {
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames());
                String suffix = null;
                // 获取图片后缀
                if (imgFile.getName().indexOf(".") > -1) {
                    suffix = imgFile.getName().substring(imgFile.getName().lastIndexOf(".") + 1);
                }// 类型和图片后缀全部小写，然后判断后缀是否合法
                if (suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()) < 0) {
                    return null;
                }
                Image img = ImageIO.read(imgFile);
                if (!force) {
                    // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
                    int width = img.getWidth(null);
                    int height = img.getHeight(null);
                    if ((width * 1.0) / w < (height * 1.0) / h) {
                        if (width > w) {
                            h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w / (width * 1.0)));
                        }
                    } else {
                        if (height > h) {
                            w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h / (height * 1.0)));
                        }
                    }
                }
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.getGraphics();
                g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
                g.dispose();
                File outImage = null;
                if (StringUtils.isEmpty(thumbImagePath)) {
                    String p = imgFile.getPath();
                    // 将图片保存在原目录并加上前缀
                    outImage = new File(p.substring(0, p.lastIndexOf(File.separator)) + File.separator + prevfix + imgFile.getName());
                } else {
                    File thumbImage = new File(thumbImagePath);
                    File parent = thumbImage.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    if (!parent.isDirectory()) {
                        throw new Exception(String.format("缩略图路径'%s'无效", thumbImagePath));
                    }
                    outImage = new File(thumbImagePath);
                }
                ImageIO.write(bi, suffix, outImage);
                return outImage.getPath();
            } catch (IOException e) {
                throw e;
            }
        } else {
            throw new Exception("原始文件不存在，无法生成缩略图");
        }
    }

    /**
     * 根据指定的文件路径判断文件是否是图片
     * @param path 文件路径
     * @return true：是图片文件；false：不是图片文件
     */
    public static boolean isImage(String path) {
        // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
        List<String> names = Arrays.asList(ImageIO.getReaderFormatNames());
        String suffix = null;
        // 获取图片后缀
        if (path.indexOf(".") > -1) {
            suffix = path.substring(path.lastIndexOf(".") + 1);
        }
        if (suffix == null || !names.contains(suffix.toLowerCase())) {
            return false;
        }
        return true;
    }

    /**
     * 根据指定的文件路径获取文件后缀名
     * @param path 文件路径
     * @return 文件后缀名
     */
    public static String getSuffix(String path){
        if(StringUtils.isEmpty(path)){
            return null;
        }
        if(path.indexOf(".") > -1){
            return path.substring(path.lastIndexOf(".") + 1);
        }
        return null;
    }

    /**
     * 压缩图片
     * <ul>
     *     <li>
     *         iphone拍照的图片(格式：png；大小：2357kb)，经原图片尺寸不同的图片质量缩放压缩结果，图片都比较清晰
     *         <ul>
     *             <li>0.5 -> 610kb</li>
     *             <li>0.45 -> 576kb</li>
     *             <li>0.4 -> 538kb</li>
     *             <li>0.35 -> 502kb</li>
     *             <li>0.3 -> 464kb</li>
     *         </ul>
     *     </li>
     *     <li>
     *         pixel xl拍照的图片(格式：png；大小:4094kb)，经原图片尺寸不同的图片质量缩放压缩结果，图片都比较清晰
     *         <ul>
     *             <li>0.5 -> 999kb</li>
     *             <li>0.45 -> 927kb</li>
     *             <li>0.4 -> 850kb</li>
     *             <li>0.35 -> 784kb</li>
     *             <li>0.3 -> 705kb</li>
     *         </ul>
     *     </li>
     * </ul>
     * <p>缩放质量(outputQuality)调整为：0.3</p>
     * @param imagePath 原图片路径
     * @param compressImagePath 压缩后的图片存放路径
     * @return 压缩后的图片存放路径
     * @throws Exception
     */
    public static String compressImage(String imagePath, String compressImagePath) throws Exception {
        File imgFile = new File(imagePath);
        if(imgFile.exists()){
            try {
                if(isImage(imagePath)) {
                    //由于在压缩png文件时，压缩后的文件大小会比原始文件要大，主要是因为png格式的图片已经是压缩的图片
                    //现在将所有的图片都压缩成jpeg格式，然后再转成指定的格式
                    String targetPath = imgFile.getParent()+ File.separator + "~"+ imgFile.getName()+".jpeg";
                    File targetFile = new File(targetPath);
                    Thumbnails.of(imagePath).scale(1f).outputQuality(0.3f).toFile(targetFile);
                    boolean result = targetFile.renameTo(new File(compressImagePath));
                    if(!result){
                        return targetFile.getPath();
                    }
                }else{
                    logger.error("指定的文件(%s)不是图片文件", imagePath);
                    throw new Exception("指定的文件不是图片");
                }
            }catch (Exception ex){
                logger.error("图片压缩失败，原图path：%s", imagePath);
                throw ex;
            }
        }else{
            throw new Exception("原始文件不存在，无法生成缩略图");
        }
        return compressImagePath;
    }
}
