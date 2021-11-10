package com.example.h2newsjava11;

import com.example.h2newsjava11.model.Article;
import com.example.h2newsjava11.repository.ArticlesRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.h2newsjava11.utils.Utils;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.zip.ZipInputStream;


@SpringBootApplication
@Controller
public class Application {

	@Autowired
	private ArticlesRep articlesRep;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("App is running.");
	}
	@Component
	public class RunAfterStartUp{
		@EventListener(ApplicationReadyEvent.class)
		public void runAfterStartup(){
			System.out.println("I print it befror start");

			final File folder = new File(UPLOAD_DIR);

			for (final File fileEntry : folder.listFiles()) {
				if (fileEntry.isDirectory()) {
					continue;
				} else {
					if (fileEntry.getName().endsWith(".zip")) {
						System.out.println(fileEntry.getName());
						try {
							ZipInputStream zipIn = new ZipInputStream(
									new FileInputStream(UPLOAD_DIR+fileEntry.getName()));
							Utils.unzipFile(zipIn,UPLOAD_DIR, fileEntry.getName());
						} catch (IOException e){
							e.printStackTrace();
						}
						Article item = Utils.txtToArticle(UPLOAD_DIR,"article.txt");
						//save into reprositrory
						articlesRep.save(item);

					}

				}
			}
			}


		}

	//dir where zip file will be saved
	private final String UPLOAD_DIR = "./src/main/resources/";

	@GetMapping("/")
	public String homepage(
			Model model){
		ArrayList <Article> alist = (ArrayList)articlesRep.findAll();
		Comparator <Article> compareById = (Article a, Article b) -> a.getId().compareTo(b.getId());
		Collections.sort(alist,compareById.reversed());
		model.addAttribute("items",
				alist);
		return "articles";
	}

	@GetMapping("/article")
	public String article(
			//RedirectAttributes attributes,
			Model model
	){
		//RedirectAttributes items = attributes.addFlashAttribute("items", articlesRep.findAll());
		//attributes.addFlashAttribute("items",articlesRep.findAll());
		//ArrayList <Article> alist = (ArrayList)articlesRep.findAll();
		//Comparator <Article> compareById = (Article a, Article b) -> a.getId().compareTo(b.getId());
		//Collections.sort(alist,compareById.reversed());
		//model.addAttribute("items",alist);
		return "article";
	}
	//@GetMapping("/upload")
	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file")MultipartFile file, RedirectAttributes attributes){
			//check if no file
		if (file.isEmpty()){
			attributes.addFlashAttribute("message",
					"Please, select file to upload.");
			return "redirect:/";
		}
		//normalize file path
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		//save file on hard disk
		try{
			Path path = Paths.get(UPLOAD_DIR+fileName);
			Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e){
			e.printStackTrace();
		}
		try {
			ZipInputStream zipIn = new ZipInputStream(new FileInputStream(UPLOAD_DIR+fileName));
			Utils.unzipFile(zipIn,UPLOAD_DIR,fileName);
		} catch (IOException e){
			e.printStackTrace();
		}
		Article item = Utils.txtToArticle(UPLOAD_DIR,"article.txt");
		//save into reprositrory
		articlesRep.save(item);
		//return response
		attributes.addFlashAttribute("items",item);

		attributes.addFlashAttribute("message",
				"You successfully uploaded file: "+fileName);
		//return "redirect:/";
		return "redirect:/article";
	}
}
