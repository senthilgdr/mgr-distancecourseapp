package in.tnmgrmu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.tnmgrmu.model.Course;
import in.tnmgrmu.model.CourseModule;
import in.tnmgrmu.model.User;
import in.tnmgrmu.service.CourseModuleService;
import in.tnmgrmu.service.CourseService;


@Controller
@RequestMapping("coursemodules")
public class CourseModuleController {

	@Autowired
	private CourseModuleService courseModuleService;
	@Autowired
	private CourseService courseService;
	
	
	@GetMapping("/list")
	public String list(ModelMap modelMap, HttpSession session) throws Exception {

		try {

			List<CourseModule> list = courseModuleService.list();
			System.out.println("list:" + list);
			modelMap.addAttribute("COURSE_MODULE_LIST", list);

			return "coursemodule/list";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}

	
	@GetMapping("/delete")
	public String delete(@RequestParam("id") Long id, ModelMap modelMap) throws Exception {

		try {
			courseModuleService.delete(Long.valueOf(id));

			return "redirect:/coursemodules/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "coursemodule/list";
		}

	}
	
	
}
