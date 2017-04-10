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
import in.tnmgrmu.model.CourseEnrollment;
import in.tnmgrmu.model.User;
import in.tnmgrmu.service.CourseEnrollmentService;
import in.tnmgrmu.service.CourseService;


@Controller
@RequestMapping("coursesenrollments")
public class CourseEnrollmentController {

	@Autowired
	private CourseEnrollmentService courseEnrollmentService;
	@Autowired
	private CourseService courseService;

	@GetMapping("/list")
	public String list(ModelMap modelMap, HttpSession session) throws Exception {

		try {

			List<CourseEnrollment> list = courseEnrollmentService.list();
			System.out.println("list:" + list);
			modelMap.addAttribute("COURSE_ENROLLMENT_LIST", list);

			return "courseenrollment/list";

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
		return "courseenrollment/add";
	}
	

	@GetMapping("/courseEnroll")
	public String courseEnroll(@RequestParam("courseId") Long courseId, ModelMap modelMap, HttpSession session) throws Exception {

		try {
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			
			CourseEnrollment courseEnroll = new CourseEnrollment();
			
			courseEnroll.setUser(user);
			
			Course course=new Course();
			course.setCourseId(courseId);
						
			courseEnroll.setCourse(course);	
			
			courseEnrollmentService.courseEnroll(courseEnroll);

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
			courseEnrollmentService.delete(Long.valueOf(id));

			return "redirect:/coursesenrollments/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "courseenrollment/list";
		}

	}
	
}
