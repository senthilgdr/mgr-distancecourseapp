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
import in.tnmgrmu.model.User;
import in.tnmgrmu.service.CourseService;

@Controller
@RequestMapping("courses")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@GetMapping("/list")
	public String list(@RequestParam(value = "category", required = false)  String category,ModelMap modelMap, HttpSession session) throws Exception {

		try {

			List<String> str = courseService.findAllCategory();
			System.out.println("Category:" + str);
			modelMap.addAttribute("CATEGORY_LIST", str);

			List<Course> list = null;
			if ( category == null || category.equals("All")){
				list = courseService.list();
			}else{
				
				list=courseService.list(category);
			}
			modelMap.addAttribute("SELECTED_CATEGORY", category);
			System.out.println("list:" + list);
			modelMap.addAttribute("COURSE_LIST", list);

			return "course/list";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}

	@GetMapping("/listCategory")
	public String listCategory(@RequestParam(value = "category", required = false) String category, ModelMap modelMap,
			HttpSession session) throws Exception {

		try {
			List<String> str = courseService.findAllCategory();
			System.out.println("Category:" + str);
			modelMap.addAttribute("CATEGORY_LIST", str);

			List<Course> list = null;
			if ( category == null || category.equals("All")){
				list = courseService.list();
			}else{
				
				list=courseService.list(category);
			}
			modelMap.addAttribute("SELECTED_CATEGORY", category);
			System.out.println("list:" + list);
			modelMap.addAttribute("COURSE_LIST", list);

			return "course/listcategory";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}

	@GetMapping("/create")
	public String create() {
		return "course/add";
	}

	@GetMapping("/save")
	public String save(@RequestParam("courseName") String courseName, @RequestParam("description") String description,
			@RequestParam("category") String category, ModelMap modelMap, HttpSession session) throws Exception {

		try {
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			// Step : Store in View
			Course course = new Course();
			course.setCourseName(courseName);
			course.setDescription(description);
			course.setCategory(category);

			course.setCreatedBy(user.getId());

			courseService.save(course);

			return "redirect:listCategory";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "add";
		}
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("id") Long id, ModelMap modelMap) throws Exception {

		try {
			courseService.delete(Long.valueOf(id));

			return "redirect:/courses/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "course/list";
		}

	}

	@GetMapping("/courseEnable")
	public String courseEnable(@RequestParam("id") Long id, ModelMap modelMap) throws Exception {

		try {
			Course course = new Course();
			course.setCourseId(id);
			courseService.courseEnable(course);

			return "redirect:/courses/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "course/list";
		}

	}

	@GetMapping("/courseDisable")
	public String courseDisable(@RequestParam("id") Long id, ModelMap modelMap) throws Exception {

		try {
			Course course = new Course();
			course.setCourseId(id);
			courseService.courseDisable(course);

			return "redirect:/courses/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "course/list";
		}

	}

	@GetMapping("/edit")
	public String edit(@RequestParam("id") Long id, ModelMap modelMap) throws Exception {

		try {

			Course course = courseService.findById(id);
			modelMap.addAttribute("EDIT_COURSE", course);

			return "course/edit";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "course/list";
		}

	}

	@GetMapping("/update")
	public String update(@RequestParam("id") Long id, @RequestParam("courseName") String courseName,
			@RequestParam("description") String description, @RequestParam("category") String category,
			ModelMap modelMap, HttpSession session) throws Exception {

		try {
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			Course course = new Course();
			course.setCourseId(id);
			course.setCourseName(courseName);
			course.setDescription(description);
			course.setCategory(category);

			course.setModifiedBy(user.getId());

			courseService.update(course);

			return "redirect:/courses/listCategory";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "edit";
		}

	}

}
