package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.InputStream;

// THÊM IMPORT NÀY
import io.github.cdimascio.dotenv.Dotenv;

/**
 * Tiện ích đọc file đa định dạng cho kiểm duyệt AI nội dung sách + tiện ích đọc biến môi trường từ .env
 */
public class Utils {

    // --- Đọc biến môi trường từ .env ---
    private static final Dotenv dotenv = Dotenv.load();

    public static String getEnv(String key) {
        return dotenv.get(key);
    }

    public static String getUploadFolder() {
        return dotenv.get("C:\\PRJ\\PRJ301_scroll\\web\\uploads");
    }
    public static String getDbUser() {
        return dotenv.get("sa");
    }
    public static String getDbPassword() {
        return dotenv.get("123456");
    }

    // --- Đọc file text thường (UTF-8) ---
    public static String readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readString(path);
    }

    /**
     * Chuẩn hóa text: bỏ khoảng trắng thừa, xuống dòng về 1 dấu cách
     */
    public static String normalizeText(String text) {
        if (text == null) return "";
        return text.trim().replaceAll("\\s+", " ");
    }

    /**
     * Đọc nội dung file đa định dạng: txt, doc, docx, pdf
     * @param filePath Đường dẫn file vật lý
     * @param ext      Đuôi file ("txt", "doc", "docx", "pdf")
     * @return Nội dung file ở dạng String, để kiểm duyệt
     * @throws Exception nếu file không đọc được
     */
    public static String readAnyTextFile(String filePath, String ext) throws Exception {
        if (filePath == null || ext == null) throw new IOException("Thiếu thông tin file.");
        ext = ext.trim().toLowerCase();
        switch (ext) {
            case "txt":
                return readFile(filePath);

            case "docx":
                try (InputStream is = Files.newInputStream(Paths.get(filePath));
                     XWPFDocument doc = new XWPFDocument(is)) {
                    StringBuilder sb = new StringBuilder();
                    doc.getParagraphs().forEach(p -> sb.append(p.getText()).append("\n"));
                    return sb.toString();
                }

            case "doc":
                try (InputStream is = Files.newInputStream(Paths.get(filePath));
                     HWPFDocument doc = new HWPFDocument(is)) {
                    return new WordExtractor(doc).getText();
                }

            case "pdf":
                try (PDDocument pdf = PDDocument.load(new java.io.File(filePath))) {
                    return new PDFTextStripper().getText(pdf);
                }

            default:
                throw new IOException("Không hỗ trợ file: " + ext);
        }
    }

    // --- Chuẩn hóa text cho JSON ---
    public static String safeForJson(String text) {
        if (text == null) return "";
        return text
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\b", "\\b")
            .replace("\f", "\\f")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");
    }

}
