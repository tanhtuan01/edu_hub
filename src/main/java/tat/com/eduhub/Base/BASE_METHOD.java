package tat.com.eduhub.base;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

public class BASE_METHOD {

	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new SecureRandom();
    private static LocalDateTime localDateTime = LocalDateTime.now();
	
	public static void FragmentWeb(String fragment, Model model) {
		String fragmentUrl =  "fragment/web/" + fragment;
		model.addAttribute("fragment", fragmentUrl);
	}
	
	public static void FragmentAdminWeb(String fragment, Model model) {
		String fragmentUrl =  "fragment/admin/" + fragment;
		model.addAttribute("fragment", fragmentUrl);
	}
	
	public static void FragmentAdminSchool(String fragment, Model model) {
		String fragmentUrl =  "fragment/school/" + fragment;
		model.addAttribute("fragment", fragmentUrl);
	}
	
	public static void FragmentLecturerSchool(String fragment, Model model) {
		String fragmentUrl =  "fragment/lecturer_school/" + fragment;
		model.addAttribute("fragment", fragmentUrl);
	}
	
	public static void FragmentLecturerWeb(String fragment, Model model) {
		String fragmentUrl =  "fragment/lecturer_web/" + fragment;
		model.addAttribute("fragment", fragmentUrl);
	}
	
	public static String randomString(int length) {
		StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
	}
	
	public static String createRandomFileName() {
		return  "_" +randomString(5) +"." + localDateTime.getDayOfMonth() +"" + localDateTime.getMonthValue() + localDateTime.getYear();
	}
	
	public static String createStrDateNow() {
		return  localDateTime.getDayOfMonth() +"" + localDateTime.getMonthValue() + localDateTime.getYear();
	}
	
	public static String schoolImagePathUpload(String fileName) {
		Path path = Paths.get("src", "main", "resources", "static", "img", "school");
		if(!Files.exists(path)) {
			try {
				Files.createDirectories(path);
				System.err.println("Created path: " + path);
			} catch (Exception e) {
				// TODO: handle exception
				System.err.println("Cant create path: " + e.getMessage());
			}
		}
		String filePath = Paths.get("src", "main", "resources", "static", "img", "school", fileName)
				.toString();
		return filePath;
	}
	
	public static String lecturerImagePathUpload(String fileName) {
		Path path = Paths.get("src", "main", "resources", "static", "img", "lecturer");
		if(!Files.exists(path)) {
			try {
				Files.createDirectories(path);
				System.err.println("Created path: " + path);
			} catch (Exception e) {
				// TODO: handle exception
				System.err.println("Cant create path: " + e.getMessage());
			}
		}
		String filePath = Paths.get("src", "main", "resources", "static", "img", "lecturer", fileName)
				.toString();
		return filePath;
	}
	
	public static String syllabusLecturerPathUpload(String domain, String fileName) {
		Path path = Paths.get("src", "main", "resources", "static", "file", "syllabus", "lecturer", domain);
		if(!Files.exists(path)) {
			try {
				Files.createDirectories(path);
				System.err.println("Created path: " + domain + " => " + path);
			} catch (Exception e) {
				// TODO: handle exception
				System.err.println("Cant create path: " + e.getMessage());
			}
		}
		String filePath = Paths.get("src", "main", "resources", "static", "file", "syllabus", "lecturer", domain, fileName)
				.toString();
		return filePath;
	}
	
	public static String documentPathUploadPrivate(String domain, String fileName) {
		Path path = Paths.get("src", "main", "resources", "static", "file", "document", domain);
		if(!Files.exists(path)) {
			try {
				Files.createDirectories(path);
				System.err.println("Create path: " + domain + " => " + path);
			} catch (Exception e) {
				// TODO: handle exception
				System.err.println("Không thể tạo path: " + e.getMessage());
			}
		}
		String filePath = Paths.get("src", "main", "resources", "static", "file", "document", domain, fileName)
				.toString();
		return filePath;
	}
	
	public static String documentPathUploadPublic(String fileName) {
		String filePath = Paths.get("src", "main", "resources", "static", "file", "document", "public", fileName)
				.toString();
		return filePath;
	}
	
	public static String removeAccents(String input) {
		String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String unaccented = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
        unaccented = unaccented.replaceAll("đ", "d");
        unaccented = unaccented.replaceAll(" ","_");
        return unaccented;
	}
	
	public static String slug(String input) {
		String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String unaccented = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
        unaccented = unaccented.replaceAll("đ", "d");
        unaccented = unaccented.replaceAll(" ","-");
        return unaccented;
	}
	
	public static String getExtensionFileName(MultipartFile file) {
	    String filename = file.getOriginalFilename();
	    String extension = "";

	    if (filename != null && !filename.isEmpty()) {
	        int dotIndex = filename.lastIndexOf('.');
	        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
	            extension = filename.substring(dotIndex + 1);
	        }
	    }

	    return extension;
	}
	
	public static String extractValueFromUrl(String url, String prefix) {
	    int startIndex = url.indexOf(prefix);
	    if (startIndex != -1) {
	        int endIndex = url.indexOf("/", startIndex + prefix.length());
	        if (endIndex != -1) {
	            return url.substring(startIndex + prefix.length(), endIndex);
	        } else {
	            return url.substring(startIndex + prefix.length());
	        }
	    }
	    return null;
	}
	
	public static String extractValueFromEmail(String email) {
	    int atIndex = email.indexOf('@');
	    if (atIndex != -1 && atIndex < email.length() - 1) {
	        return email.substring(atIndex + 1);
	    }
	    return "";
	}
	
	public static void FragmentStudentSchool(String fragment, Model model) {
		String fragmentUrl =  "fragment/student_school/" + fragment;
		model.addAttribute("fragment", fragmentUrl);
	}

}
