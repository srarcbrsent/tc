package com.ysu.zyw.tc.base.utils;

import com.google.common.base.Throwables;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

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
    public static ByteArrayOutputStream scale(@Nonnull InputStream inputStream, double scale) {
        checkNotNull(inputStream);
        checkArgument(scale > 0);
        try {
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
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }


    /**
     * 缩放图像（按高度和宽度缩放）
     *
     * @param wrapWhite 比例不对时是否需要补白：true为补白; false为不补白;
     */
    public static ByteArrayOutputStream scale(@Nonnull InputStream inputStream, int height, int width, boolean wrapWhite) {
        checkNotNull(inputStream);
        checkArgument(height > 0);
        checkArgument(width > 0);
        try {
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
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    /**
     * 图像切割(按指定起点坐标和宽高切割)
     */
    public static ByteArrayOutputStream cut(@Nonnull InputStream inputStream, int x, int y, int width, int height) {
        checkNotNull(inputStream);
        checkArgument(x > 0);
        checkArgument(y > 0);
        checkArgument(height > 0);
        checkArgument(width > 0);
        try {
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
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    /**
     * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
     */
    public static ByteArrayOutputStream convert(@Nonnull InputStream inputStream, @Nonnull String formatName) {
        checkNotNull(inputStream);
        checkNotNull(formatName);
        try {
            BufferedImage src = ImageIO.read(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(src, formatName, byteArrayOutputStream);
            return byteArrayOutputStream;
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

}
