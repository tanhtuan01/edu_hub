//package tat.com.eduhub.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ClassPathResource;
//import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.poi.xslf.usermodel.XMLSlideShow;
//;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@Controller
//public class FileViewerController {
//
//	@GetMapping(value = "/previewFile")
//	public String previewFile(@RequestParam(name = "file") String fileName,
//			@RequestParam(name = "domain") String domain, @RequestParam(name = "share") String share,
//			@RequestParam(name = "type")String type,
//			Model model) throws IOException {
//		
//		System.err.println("File name: "  + fileName);
//		System.err.println("Domain: " + domain);
//		System.err.println("Type: " + type);
//		System.err.println("Share:" + share);
//		
//		String filename = fileName;
//        int dotIndex = filename.lastIndexOf(".");
//        String fileExtension = filename.substring(dotIndex + 1);
//		
//		 String content = "";
//	     String viewName = "";
//	        
//	     	
//	     if (fileExtension.equals("doc")) {
//	    	    String fileNamePath = fileName;
//	    	    
//	    	    Path path;
//	    	    if (share.equals("private")) {
//	    	    	path = Paths.get("src", "main", "resources", "static", "file", "document" , domain, fileNamePath);
//	    	    } else {
//	    	    	path = Paths.get("src", "main", "resources", "static", "file", "document", "public", fileNamePath);
//	    	    }
//
//	    	    
//
//	    	    try {
//	    	        if (Files.exists(path)) {
//	    	            InputStream inputStream = Files.newInputStream(path);
//	    	            HWPFDocument document = new HWPFDocument(inputStream);
//	    	            WordExtractor extractor = new WordExtractor(document);
//	    	            content = extractor.getText();
//	    	     
//	    	        } else {
//	    	            System.err.println("File not found: " + path.toString());
//	    	        }
//	    	    } catch (IOException e) {
//	    	        e.printStackTrace();
//	    	    }
//	    	    viewName = "view_doc";
//	    	}
//	     	
//	     	else if (fileExtension.equals("ppt")) {
//	            Resource resource = new ClassPathResource("documents/" + fileName + ".pptx");
//	            InputStream inputStream = resource.getInputStream();
//	            XMLSlideShow ppt = new XMLSlideShow(inputStream);
//	            // Handle PowerPoint file
//	            content = ""; // Add your logic to extract content from PowerPoint file
//	            viewName = "view_pp";
//	        } else if (fileExtension.equals("txt")) {
//	            Resource resource = new ClassPathResource("documents/" + fileName + ".txt");
//	            InputStream inputStream = resource.getInputStream();
//	            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//	            StringBuilder stringBuilder = new StringBuilder();
//	            String line;
//	            while ((line = reader.readLine()) != null) {
//	                stringBuilder.append(line).append("\n");
//	            }
//	            content = stringBuilder.toString();
//	            viewName = "view_doc";
//	        }
//
//	        model.addAttribute("content", content);
//	        return viewName;
//
//	}
//	
//}
