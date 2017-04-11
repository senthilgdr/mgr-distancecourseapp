package in.tnmgrmu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.tnmgrmu.model.Category;
import in.tnmgrmu.model.Course;
import in.tnmgrmu.model.CourseCategory;
import in.tnmgrmu.service.CategoryService;
import in.tnmgrmu.service.CourseCategoryService;
import in.tnmgrmu.service.CourseService;


@Controller
@RequestMapping("coursecategories")
public class CourseCategoryController {

	@Autowired
	private CourseCategoryService courseCategoryService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private CategoryService categoryService;
	
	
	@GetMapping("/list")
	public String list(ModelMap modelMap, HttpSession session) throws Exception {

		try {

			List<CourseCategory> list = courseCategoryService.list();
			System.out.println("list:" + list);
			modelMap.addAttribute("COURSE_CATEGORY_LIST", list);

			return "coursecategory/list";

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
		
		List<Category> category = categoryService.list();
		modelMap.addAttribute("CATEGORY_LIST" , category );
		return "coursecategory/add";
	}

	@GetMapping("/save")
	public String save(@RequestParam("courseId") Long courseId,@RequestParam("categoryId") Long categoryId, ModelMap modelMap, HttpSession session) throws Exception {

		try {
			
			// Step : Store in View
			CourseCategory courseCategory = new CourseCategory();
			
			Course course=new Course();
			course.setCourseId(courseId);			
			
			Category category=new Category();
			category.setCategoryId(categoryId);		
			
			courseCategory.setCourse(course);
			courseCategory.setCategory(category);
						
			courseCategoryService.save(courseCategory);

			return "redirect:list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "add";
		}
	}
	@GetMapping("/edit")
	public String edit(@RequestParam("id") Long id, ModelMap modelMap) throws Exception {

		try {

			CourseCategory courseCategory = courseCategoryService.findById(id);
			modelMap.addAttribute("EDIT_COURSE_CATEGORY", courseCategory);

			return "coursecategory/edit";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "coursecategory/list";
		}

	}

	@GetMapping("/update")
	public String update(@RequestParam("id") Long id,@RequestParam("courseId") Long courseId,@RequestParam("categoryId") Long categoryId, ModelMap modelMap,
			HttpSession session) throws Exception {

		try {
         CourseCategory courseCategory = new CourseCategory();
         courseCategory.setCcId(id);
			
			Course course=new Course();
			course.setCourseId(courseId);			
			
			Category category=new Category();
			category.setCategoryId(categoryId);		
			
			courseCategory.setCourse(course);
			courseCategory.setCategory(category);
						
			courseCategoryService.update(courseCategory);
					
			return "redirect:/coursecategories/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "edit";
		}

	}

	@GetMapping("/delete")
	public String delete(@RequestParam("id") Long id, ModelMap modelMap) throws Exception {

		try {
			courseCategoryService.delete(Long.valueOf(id));

			return "redirect:/coursecategories/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "coursecategory/list";
		}

	}
	
}
