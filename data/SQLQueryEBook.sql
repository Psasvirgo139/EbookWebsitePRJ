-- Tạo database
CREATE DATABASE EBookWebsite;
USE EBookWebsite;

-- USERS
CREATE TABLE Users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(100) NOT NULL,
    email NVARCHAR(255) UNIQUE NOT NULL,
    password_hash NVARCHAR(255) NOT NULL,
    avatar_url NVARCHAR(MAX),
    role NVARCHAR(20) DEFAULT 'user',
    created_at DATETIME DEFAULT GETDATE(),
    userinfor_id INT NULL,
    status NVARCHAR(20) DEFAULT 'active',  -- Thêm cột status để theo dõi trạng thái (active, banned, deleted...)
    last_login DATETIME NULL              -- Thêm cột last_login để theo dõi thời gian đăng nhập cuối
);

-- USER INFORMATION
CREATE TABLE UserInfor (
    id INT IDENTITY(1,1) PRIMARY KEY,
    phone NVARCHAR(20),
    birthday DATE,
    gender NVARCHAR(10),
    address NVARCHAR(255),
    introduction NVARCHAR(MAX)
);

ALTER TABLE Users
ADD CONSTRAINT FK_Users_UserInfor
FOREIGN KEY (userinfor_id) REFERENCES UserInfor(id);

-- EBOOKS
CREATE TABLE Ebooks (
    id INT IDENTITY(1,1) PRIMARY KEY,
    title NVARCHAR(255) NOT NULL,
    description NVARCHAR(MAX),
    release_type NVARCHAR(20) NOT NULL,
    language NVARCHAR(50),
    status NVARCHAR(20) DEFAULT 'ongoing',
    visibility NVARCHAR(20) DEFAULT 'public',
    uploader_id INT,
    created_at DATETIME DEFAULT GETDATE(),
    view_count INT DEFAULT 0,             -- Thêm cột view_count để theo dõi lượt xem
    cover_url NVARCHAR(MAX),              -- Thêm cột cover_url để lưu đường dẫn bìa sách
    FOREIGN KEY (uploader_id) REFERENCES Users(id)
);

-- TAGS
CREATE TABLE Tags (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    CONSTRAINT UK_Tags_Name UNIQUE (name)  -- Thêm ràng buộc UNIQUE trên cột name để tránh trùng lặp
);

-- EBOOK-TAGS
CREATE TABLE EbookTags (
    ebook_id INT,
    tag_id INT,
    is_primary BIT DEFAULT 0,
    PRIMARY KEY (ebook_id, tag_id),
    FOREIGN KEY (ebook_id) REFERENCES Ebooks(id),
    FOREIGN KEY (tag_id) REFERENCES Tags(id)
);

-- VOLUMES
CREATE TABLE Volumes (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ebook_id INT NOT NULL,
    title NVARCHAR(255),
    number INT,
    published_at DATE,
    access_level NVARCHAR(20) DEFAULT 'public', -- Thêm cột access_level để quản lý quyền truy cập (public, premium...)
    view_count INT DEFAULT 0,                  -- Thêm cột view_count để theo dõi lượt xem
    like_count INT DEFAULT 0,                  -- Thêm cột like_count để theo dõi lượt thích
    FOREIGN KEY (ebook_id) REFERENCES Ebooks(id)
);

-- CHAPTERS
CREATE TABLE Chapters (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ebook_id INT NOT NULL,
    volume_id INT NULL,
    title NVARCHAR(255),
    number DECIMAL(5,1),
    content_url NVARCHAR(MAX),
    created_at DATETIME DEFAULT GETDATE(),
    access_level NVARCHAR(20) DEFAULT 'public', -- Thêm cột access_level để quản lý quyền truy cập (public, premium...)
    view_count INT DEFAULT 0,                  -- Thêm cột view_count để theo dõi lượt xem
    like_count INT DEFAULT 0,                  -- Thêm cột like_count để theo dõi lượt thích
    FOREIGN KEY (ebook_id) REFERENCES Ebooks(id),
    FOREIGN KEY (volume_id) REFERENCES Volumes(id)
);

-- AUTHORS
CREATE TABLE Authors (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    bio NVARCHAR(MAX),
    avatar_url NVARCHAR(MAX),
    CONSTRAINT UK_Authors_Name UNIQUE (name)   -- Thêm ràng buộc UNIQUE trên cột name để tránh trùng lặp
);

-- EBOOK-AUTHORS
CREATE TABLE EbookAuthors (
    ebook_id INT,
    author_id INT,
    role NVARCHAR(100),
    PRIMARY KEY (ebook_id, author_id),
    FOREIGN KEY (ebook_id) REFERENCES Ebooks(id),
    FOREIGN KEY (author_id) REFERENCES Authors(id)
);

-- FAVORITES
CREATE TABLE Favorites (
    user_id INT,
    ebook_id INT,
    chapter_id INT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    PRIMARY KEY (user_id, ebook_id),
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (ebook_id) REFERENCES Ebooks(id),
    FOREIGN KEY (chapter_id) REFERENCES Chapters(id)
);

-- COMMENTS
CREATE TABLE Comments (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    ebook_id INT NOT NULL,
    chapter_id INT NULL,
    content NVARCHAR(MAX) NOT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    parent_comment_id INT NULL,                -- Thêm cột parent_comment_id để hỗ trợ bình luận trả lời
    like_count INT DEFAULT 0,                  -- Thêm cột like_count để theo dõi lượt thích bình luận
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (ebook_id) REFERENCES Ebooks(id),
    FOREIGN KEY (chapter_id) REFERENCES Chapters(id),
    FOREIGN KEY (parent_comment_id) REFERENCES Comments(id) -- Thêm khóa ngoại cho parent_comment_id
);

-- USER READS
CREATE TABLE UserReads (
    user_id INT,
    ebook_id INT,
    last_read_chapter_id INT,   
    last_read_at DATETIME DEFAULT GETDATE(),
    PRIMARY KEY (user_id, ebook_id),
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (ebook_id) REFERENCES Ebooks(id),
    FOREIGN KEY (last_read_chapter_id) REFERENCES Chapters(id)
);

-- User
INSERT INTO Users (username, email, password_hash) VALUES ('demo', 'demo@email.com', '123');

-- Ebook
INSERT INTO Ebooks (title, description, release_type, language, uploader_id)
VALUES
(N'Sách Java', N'Giới thiệu lập trình Java cơ bản', 'free', 'vi', 1),
(N'Lập trình Servlet', N'Tạo web với Servlet & JSP', 'free', 'vi', 1);

-- Chapter
INSERT INTO Chapters (ebook_id, title, number, content_url)
VALUES
(1, N'Giới thiệu Java', 1, 'content/java1.txt'),
(2, N'Tổng quan Servlet', 1, 'content/servlet1.txt');
