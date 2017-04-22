package in.tnmgrmu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.tnmgrmu.model.Video;
import in.tnmgrmu.service.VideoService;

@Controller
@RequestMapping("videos")
public class VideoController {

	@Autowired
	private VideoService videoService;

	@GetMapping("/list")
	public String list(ModelMap modelMap, HttpSession session) throws Exception {

		try {

			List<Video> list = videoService.list();
			System.out.println("list:" + list);
			modelMap.addAttribute("VIDEO_LIST", list);

			return "video/list";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}

	@GetMapping("/create")
	public String create() {
		return "video/add";
	}

	@GetMapping("/save")
	public String save(@RequestParam("title") String title,@RequestParam("url") String url, ModelMap modelMap, HttpSession session) throws Exception {

		try {
			if (title == null || "".equals(title.trim())) {
				throw new Exception("Invalid Title");
			}
			if (url == null || "".equals(url.trim())) {
				throw new Exception("Invalid url");
			}

			// Step : Store in View
			Video video = new Video();
			video.setTitle(title);
			video.setUrl(url);

			videoService.save(video);

			return "redirect:list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "add";
		}
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("id") Long id, ModelMap modelMap) throws Exception {

		try {
			if (id == null ) {
				throw new Exception("Invalid VideoId");
			}
			videoService.delete(Long.valueOf(id));

			return "redirect:/videos/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "video/list";
		}

	}

	@GetMapping("/edit")
	public String edit(@RequestParam("id") Long id, ModelMap modelMap) throws Exception {

		try {
			if (id == null ) {
				throw new Exception("Invalid VideoId");
			}
			Video Video = videoService.findById(id);
			modelMap.addAttribute("EDIT_VIDEO", Video);

			return "video/edit";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "video/list";
		}

	}

	@GetMapping("/update")
	public String update(@RequestParam("id") Long id, @RequestParam("title") String title,@RequestParam("url") String url, ModelMap modelMap,
			HttpSession session) throws Exception {

		try {
			if (id == null ) {
				throw new Exception("Invalid VideoId");
			}
			if (title == null || "".equals(title.trim())) {
				throw new Exception("Invalid Title");
			}
			if (url == null || "".equals(url.trim())) {
				throw new Exception("Invalid url");
			}

			Video video = new Video();
			video.setId(id);
			video.setTitle(title);
			video.setUrl(url);

			videoService.update(video);

			return "redirect:/videos/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "edit";
		}

	}

}
