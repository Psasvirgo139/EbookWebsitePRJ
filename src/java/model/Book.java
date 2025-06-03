package model;

import java.io.Serializable;

public class Book implements Serializable {

    private int id;
    private String title;
    private String author;
    private String genre; // thể loại, có thể là tên hoặc mã thể loại
    private String description;
    private String coverUrl; // link ảnh bìa
    private String contentUrl; // link đến file nội dung (txt/pdf/html/...)
    private int uploaderId; // id người upload sách
    private String status; // "pending", "approved", "rejected", "hidden"...
    private String createdAt;
    private String updatedAt;
    private String summary;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Book(String summary) {
        this.summary = summary;
    }

    // Constructors
    public Book() {
    }

    public Book(int id, String title, String author, String genre, String description,
            String coverUrl, String contentUrl, int uploaderId, String status,
            String createdAt, String updatedAt, String summary) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.genre = genre;
    this.description = description;
    this.coverUrl = coverUrl;
    this.contentUrl = contentUrl;
    this.uploaderId = uploaderId;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.summary = summary;
}


    // Getter & Setter cho tất cả các trường
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public int getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(int uploaderId) {
        this.uploaderId = uploaderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
