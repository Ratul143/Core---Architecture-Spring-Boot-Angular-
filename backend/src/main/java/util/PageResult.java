package util;

import java.util.List;

public class PageResult<T> {
    private List<T> content;
    private long totalElements;

    public PageResult(List<T> resultList, long totalRows) {
        this.content = resultList;
        this.totalElements = totalRows;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}
