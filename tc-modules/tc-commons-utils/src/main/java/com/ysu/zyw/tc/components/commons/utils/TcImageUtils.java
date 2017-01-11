package com.ysu.zyw.tc.components.commons.utils;

import com.ysu.zyw.tc.base.tools.TcPair;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nonnull;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@UtilityClass
public class TcImageUtils {

    public static final String IMAGE_TYPE_JPG = "jpg";

    /**
     * 缩放图像(按比例缩放)
     */
    @SneakyThrows
    public static ByteArrayOutputStream scale(@Nonnull InputStream inputStream, double ratio) {
        checkNotNull(inputStream);
        checkArgument(ratio > 0);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Thumbnails.of(inputStream)
                .scale(ratio)
                .outputFormat(IMAGE_TYPE_JPG)
                .toOutputStream(baos);
        return baos;
    }

    /**
     * 缩放图像(按高度和宽度缩放)
     */
    @SneakyThrows
    public static ByteArrayOutputStream size(@Nonnull InputStream inputStream,
                                             int width,
                                             int height) {
        checkNotNull(inputStream);
        checkArgument(width > 0);
        checkArgument(height > 0);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Thumbnails.of(inputStream)
                .size(width, height)
                .outputFormat(IMAGE_TYPE_JPG)
                .toOutputStream(baos);
        return baos;
    }

    /**
     * 图像切割(按指定起点坐标和宽高切割)
     */
    @SneakyThrows
    public static ByteArrayOutputStream crop(@Nonnull InputStream inputStream, int x, int y, int width, int height) {
        checkNotNull(inputStream);
        checkArgument(x >= 0);
        checkArgument(y >= 0);
        checkArgument(width > 0);
        checkArgument(height > 0);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Thumbnails.of(inputStream)
                .sourceRegion(Positions.TOP_LEFT, width, height)
                .size(width, height)
                .outputFormat(IMAGE_TYPE_JPG)
                .toOutputStream(baos);
        return baos;
    }

    /**
     * 图像切割(按固定大小加最大宽高比,超出部分切除)
     */
    @SneakyThrows
    public static ByteArrayOutputStream crop(@Nonnull InputStream inputStream,
                                             int width,
                                             int height) {
        checkNotNull(inputStream);
        checkArgument(width > 0);
        checkArgument(height > 0);
        byte[] bts = IOUtils.toByteArray(inputStream);
        TcPair<Integer, Integer> widthAndHeight =
                calculateMaxSizeInFixedAspectRatio(new ByteArrayInputStream(bts), (double) width / (double) height);
        ByteArrayOutputStream baos = crop(new ByteArrayInputStream(bts), 0, 0,
                widthAndHeight.getLeft(), widthAndHeight.getRight());
        return size(new ByteArrayInputStream(baos.toByteArray()), width, height);
    }

    /**
     * 计算最大宽高
     */
    @SneakyThrows
    public static TcPair<Integer, Integer> calculateMaxSizeInFixedAspectRatio(@Nonnull InputStream inputStream,
                                                                              double ratio) {
        checkNotNull(inputStream);
        checkArgument(ratio > 0);
        BufferedImage img = Thumbnails.of(inputStream).scale(1).asBufferedImage();
        int width = img.getWidth();
        int height = img.getHeight();
        double imgRatio = (double) width / (double) height;
        if (imgRatio > ratio) {
            width = (int) ((double) width * ratio / imgRatio);
        } else {
            height = (int) ((double) height * imgRatio / ratio);
        }
        return TcPair.with(width, height);
    }

}
