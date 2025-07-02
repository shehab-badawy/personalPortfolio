package portfolio.controller;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;
import portfolio.Upload.Storage.StorageFileNotFoundException;
import portfolio.Upload.Storage.StorageService;
import portfolio.entity.Achievement.Achievement;
import portfolio.entity.Visual.Visual;
import portfolio.entity.VisualKey.VisualKey;
import portfolio.service.AchievementService.AchievementService;
import portfolio.service.VisualService.VisualService;

@Controller
@RequestMapping("/")
public class FileUploadController {

	private final StorageService storageService;
	@Autowired
	private VisualService visualService;
	@Autowired
	private AchievementService achievementService;
	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/images")
	public String listUploadedFiles(Model model) throws IOException 
	{


		model.addAttribute("files", storageService.loadAll().map(
				path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
						"serveFile", path.getFileName().toString()).build().toUri().toString())
				.collect(Collectors.toList()));

		return "uploadForm";
	}

	@GetMapping("/images/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) 
	{

		Resource file = storageService.loadAsResource(filename);

		if (file == null) {
			return ResponseEntity.notFound().build();
		}
	
		// Determine the file's content type (e.g., image/jpeg, image/png)
		String contentType = null;
		try {
			contentType = Files.probeContentType(file.getFile().toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		if (contentType == null) {
			contentType = "application/octet-stream";  // Default to binary content type if unknown
		}
	
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@PostMapping("/admin/{achie_id}/upload")
	public ResponseEntity<JsonNode>  handleFileUpload(@RequestParam("files") MultipartFile[] files, @PathVariable Long achie_id)
    {
		final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
		logger.info("Uploaded achievement id: {}", achie_id);
		Achievement achievement =  achievementService.getAchievementById(achie_id);

		Arrays.asList(files).stream().forEach(
			file ->
		{
			storageService.store(file);
			VisualKey visualKey = new VisualKey(achievement, "server/"+file.getOriginalFilename());
			Visual visual = new Visual(visualKey, "lablalala");
			visualService.saveVisual(visual);
		}
		);

		ObjectMapper objectMapper = new ObjectMapper();

    // Manually create JSON
    JsonNode response = objectMapper.createObjectNode()
            .put("message", "This is a message")
            .put("status", "success");

    return ResponseEntity.ok(response);

	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}