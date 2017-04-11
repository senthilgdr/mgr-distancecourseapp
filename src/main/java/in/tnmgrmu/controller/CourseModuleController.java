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

	
	@GetMapping("/create")
	public String create(ModelMap modelMap) {
		List<Course> list = courseService.list();
		modelMap.addAttribute("COURSE_LIST" , list );
		return "coursemodule/add";
	}

	@GetMapping("/save")
	public String save(@RequestParam("courseId") Long courseId,@RequestParam("moduleName") String moduleName, ModelMap modelMap, HttpSession session) throws Exception {

		try {
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			// Step : Store in View
			CourseModule courseModule = new CourseModule();
			
			Course course=new Course();
			course.setCourseId(courseId);
			courseModule.setModuleName(moduleName);
			
			courseModule.setCourse(course);
			
			courseModule.setCreatedBy(user.getId());
						
			courseModuleService.save(courseModule);

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
			courseModuleService.delete(Long.valueOf(id));

			return "redirect:/coursemodules/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "coursemodule/list";
		}

	}
	
	
	@GetMapping("/edit")
	public String edit(@RequestParam("id") Long id, ModelMap modelMap) throws Exception {

		try {

			CourseModule courseModule = courseModuleService.findById(id);
			modelMap.addAttribute("EDIT_COURSE_MODULE", courseModule);

			return "coursemodule/edit";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "coursemodule/list";
		}

	}

	@GetMapping("/update")
	public String update(@RequestParam("id") Long id,@RequestParam("courseId") Long courseName,@RequestParam("moduleName") String moduleName, ModelMap modelMap,
			HttpSession session) throws Exception {

		try {
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			
			CourseModule courseModule = new CourseModule();
			courseModule.setCourseModuleId(id);
			courseModule.setModuleName(moduleName);
			
			Course course=new Course();
			course.setCourseId(courseName);
			
			courseModule.setCourse(course);
			
			courseModule.setModifiedBy(user.getId());
						
			courseModuleService.update(courseModule);
			
			return "redirect:/coursemodules/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "edit";
		}

	}

	
	
}
