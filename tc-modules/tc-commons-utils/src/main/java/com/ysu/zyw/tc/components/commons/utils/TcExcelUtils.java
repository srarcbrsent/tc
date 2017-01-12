package com.ysu.zyw.tc.components.commons.utils;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;

import javax.annotation.Nonnull;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

public class TcExcelUtils {

    public String contentType() {
        return "application/vnd.ms-excel";
    }

    @SneakyThrows
    public Workbook load(@Nonnull String path) {
        checkNotNull(path);
        try (FileInputStream fileInputStream = new FileInputStream(path);
             POIFSFileSystem poifsFileSystem = new POIFSFileSystem(fileInputStream)) {
            return new HSSFWorkbook(poifsFileSystem);
        }
    }

    public HSSFSheet load(@Nonnull HSSFWorkbook workbook, int index) {
        checkNotNull(workbook);
        return workbook.getSheetAt(index);
    }

    public Workbook create() {
        return new HSSFWorkbook();
    }

    public HSSFSheet create(@Nonnull HSSFWorkbook workbook) {
        checkNotNull(workbook);
        return workbook.createSheet();
    }

    public TcExcelUtils write(@Nonnull HSSFSheet sheet,
                              int row,
                              int column,
                              @Nonnull String value) {
        checkNotNull(sheet);
        checkNotNull(value);
        this.write(sheet, row, column, cell -> cell.setCellValue(value));
        sheet.getRow(row).getCell(column).setCellValue(value);
        return this;
    }

    public TcExcelUtils write(@Nonnull HSSFSheet sheet,
                              int row,
                              int column,
                              @Nonnull Consumer<HSSFCell> consumer) {
        checkNotNull(sheet);
        checkNotNull(consumer);
        HSSFCell cell = sheet.getRow(row).getCell(column);
        consumer.accept(cell);
        return this;
    }

    public String read(@Nonnull HSSFSheet sheet,
                       int row,
                       int column) {
        return sheet.getRow(row).getCell(column).getStringCellValue();
    }

    public <R> R read(@Nonnull HSSFSheet sheet,
                      int row,
                      int column,
                      @Nonnull Function<HSSFCell, R> function) {
        HSSFCell cell = sheet.getRow(row).getCell(column);
        return function.apply(cell);
    }

    @SneakyThrows
    public ByteArrayOutputStream write2StreamAndCloseWorkbook(HSSFWorkbook workbook) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        IOUtils.closeQuietly(workbook);
        return baos;
    }

}
