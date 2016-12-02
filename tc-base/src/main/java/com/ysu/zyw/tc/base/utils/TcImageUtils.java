package com.ysu.zyw.tc.base.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@UtilityClass
public class TcImageUtils {

    // 图形交换格式
    public static final String IMAGE_TYPE_GIF = "gif";

    // 联合照片专家组
    public static final String IMAGE_TYPE_JPG = "jpg";

    // 联合照片专家组
    public static final String IMAGE_TYPE_JPEG = "jpeg";

    // 位图
    public static final String IMAGE_TYPE_BMP = "bmp";

    // 可移植网络图形
    public static final String IMAGE_TYPE_PNG = "png";

    /**
     * 缩放图像（按比例缩放）
     */
    @SneakyThrows
    public static ByteArrayOutputStream scale(@Nonnull InputStream inputStream, double scale) {
        checkNotNull(inputStream);
        checkArgument(scale > 0);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedImage src = ImageIO.read(inputStream);
        int width = (int) (src.getWidth() * scale);
        int height = (int) (src.getHeight() * scale);
        Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        ImageIO.write(tag, "JPEG", byteArrayOutputStream);
        return byteArrayOutputStream;
    }


    /**
     * 缩放图像（按高度和宽度缩放）
     *
     * @param wrapWhite 比例不对时是否需要补白：true为补白; false为不补白;
     */
    @SneakyThrows
    public static ByteArrayOutputStream scale(@Nonnull InputStream inputStream, int height, int width, boolean wrapWhite) {
        checkNotNull(inputStream);
        checkArgument(height > 0);
        checkArgument(width > 0);
        double ratio;
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        Image img = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        if ((bufferedImage.getHeight() > height) || (bufferedImage.getWidth() > width)) {
            if (bufferedImage.getHeight() > bufferedImage.getWidth()) {
                ratio = (new Integer(height)).doubleValue()
                        / bufferedImage.getHeight();
            } else {
                ratio = (new Integer(width)).doubleValue() / bufferedImage.getWidth();
            }
            AffineTransformOp affineTransformOp = new AffineTransformOp(AffineTransform
                    .getScaleInstance(ratio, ratio), null);
            img = affineTransformOp.filter(bufferedImage, null);
        }
        if (wrapWhite) {
            // 补白
            BufferedImage bufferedImageBak = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageBak.createGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, width, height);
            if (width == img.getWidth(null)) {
                g.drawImage(img, 0, (height - img.getHeight(null)) / 2,
                        img.getWidth(null), img.getHeight(null),
                        Color.white, null);
            } else {
                g.drawImage(img, (width - img.getWidth(null)) / 2, 0,
                        img.getWidth(null), img.getHeight(null),
                        Color.white, null);
            }
            g.dispose();
            img = bufferedImageBak;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write((BufferedImage) img, "JPEG", byteArrayOutputStream);
        return byteArrayOutputStream;
    }

    /**
     * 图像切割(按指定起点坐标和宽高切割)
     */
    @SneakyThrows
    public static ByteArrayOutputStream cut(@Nonnull InputStream inputStream, int x, int y, int width, int height) {
        checkNotNull(inputStream);
        checkArgument(x > 0);
        checkArgument(y > 0);
        checkArgument(height > 0);
        checkArgument(width > 0);
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        int srcWidth = bufferedImage.getWidth();
        int srcHeight = bufferedImage.getHeight();

        Image image = bufferedImage.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
        ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
        Image img = Toolkit.getDefaultToolkit().createImage(
                new FilteredImageSource(image.getSource(),
                        cropFilter));
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
        g.dispose();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(tag, "JPEG", byteArrayOutputStream);
        return byteArrayOutputStream;
    }

    /**
     * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
     */
    @SneakyThrows
    public static ByteArrayOutputStream convert(@Nonnull InputStream inputStream, @Nonnull String formatName) {
        checkNotNull(inputStream);
        checkNotNull(formatName);
        BufferedImage src = ImageIO.read(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(src, formatName, byteArrayOutputStream);
        return byteArrayOutputStream;
    }

    /**
     * 已有验证码，生成验证码图片
     *
     * @param code               文本验证码
     * @param width              图片宽度
     * @param height             图片高度
     * @param interLines         图片中干扰线的条数
     * @param bgColor            图片颜色，若为null，则采用随机颜色
     * @param fontColor          字体颜色，若为null，则采用随机颜色
     * @param interLineColor     干扰线颜色，若为null，则采用随机颜色
     */
    public static BufferedImage generateCaptchaImg(@Nonnull String code,
                                                   int width,
                                                   int height,
                                                   @Nullable Color bgColor,
                                                   int fontSize,
                                                   @Nullable Color fontColor,
                                                   @Nullable Color interLineColor) {
        checkNotNull(code);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();
        // 画背景图
        g.setColor(bgColor == null ? randomColor() : bgColor);
        g.fillRect(0, 0, width, height);

        int fx = (width / code.length()) / 2;
        int fy = (height - fontSize) / 2;
        g.setFont(new Font("Default", Font.PLAIN, fontSize));
        // 写验证码字符
        for (int i = 0; i < code.length(); i++) {
            g.setColor(fontColor == null ? randomColor() : fontColor);
            g.drawString(code.charAt(i) + "", fx, fy);
            fx += width / code.length();
        }
        g.dispose();
        return bufferedImage;
    }

    private static Color randomColor() {
        Random r = new Random();
        return new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
    }

}
