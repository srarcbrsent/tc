package com.ysu.zyw.tc.base.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * PaginationUtil. start page start by 1.
 *
 * @author yaowu.zhang
 */
@Slf4j
public class PaginationUtil {

    /**
     * give every page size and current page, then return start line number and page size.
     *
     * only consider logic paging, do not accept all records size verify.
     */
    public static Pagination paging(int currentPage, int pageSize) {
        checkArgument(pageSize > 0, "negative or zero page size is not allowed");

        if (currentPage <= 0) {
            currentPage = 1;
        }

        int startLine = (currentPage - 1) * pageSize;

        return new Pagination(startLine, pageSize, -1);
    }

    /**
     * give total size, every page size and current page, then return start line number and all page count.
     *
     * auto reset the first page and last page if pass an invalid current page.
     */
    public static Pagination paging(int currentPage, int totalSize, int pageSize) {
        checkArgument(pageSize > 0, "negative or zero page size is not allowed");
        checkArgument(totalSize > 0, "negative or zero total size is not allowed");

        if (currentPage <= 0) {
            currentPage = 1;
        }

        int pageCount = totalSize % pageSize == 0 ? (totalSize / pageSize) : (totalSize / pageSize + 1);
        int startLine = ((currentPage > pageCount ? pageCount : currentPage) - 1) * pageSize;

        return new Pagination(startLine, pageSize, pageCount);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pagination {

        private int startLine;

        private int pageSize;

        private int pageCount;

    }

}
