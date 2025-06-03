import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class TestReadFile {

    public static void main(String[] args) throws Exception {
        // Thay đổi path & ext cho từng file muốn test
        String path = "C:\\Users\\Admin\\Downloads\\ilovepdf_converted\\Phim1.pdf";  // Đường dẫn file cần test
        String ext = "pdf";                // "txt", "docx", "pdf"

        String text = readAnyTextFile(path, ext);

        System.out.println("========== Kết quả đọc file ==========");
        System.out.println(text);
        System.out.println("======================================");
        System.out.println("Độ dài chuỗi: " + (text != null ? text.length() : 0));
    }

    public static String readAnyTextFile(String filePath, String ext) throws Exception {
        if ("txt".equals(ext)) {
            return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } else if ("docx".equals(ext)) {
            try (FileInputStream fis = new FileInputStream(filePath);
                 XWPFDocument doc = new XWPFDocument(fis)) {
                XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
                return extractor.getText();
            }
        } else if ("pdf".equals(ext)) {
            try (PDDocument pdf = PDDocument.load(new File(filePath))) {
                PDFTextStripper stripper = new PDFTextStripper();
                return stripper.getText(pdf);
            }
        }
        throw new IllegalArgumentException("Không hỗ trợ định dạng: " + ext);
    }
}
