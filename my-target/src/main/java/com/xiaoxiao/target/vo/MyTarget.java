package com.xiaoxiao.target.vo;

public class MyTarget {

    /**
     * 邮箱
     */
    private String emile ;

    /**
     * 类型
     */
    private String type ;

    /**
     * 内容
     */
    private String content ;

    /**
     * 创建时间
     */
    private String createDate ;

    /**
     * 截止时间
     */
    private String endDate ;

    /**
     * 状态
     */
    private Integer status ;

    public String getEmile() {
        return emile;
    }

    public void setEmile(String emile) {
        this.emile = emile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MyTarget{" +
                "emile='" + emile + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", createDate='" + createDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", status=" + status +
                '}';
    }
}
