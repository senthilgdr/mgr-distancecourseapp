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
@RequestMapping("api/coursesenrollments")
public class CourseEnrollmentController {

	@Autowired
	private CourseEnrollmentService courseEnrollmentService;
	@Autowired
	private CourseService courseService;
	

	@GetMapping("/courseEnroll")
	public String courseEnroll(@RequestParam("courseId") Long courseId, ModelMap modelMap, HttpSession session) throws Exception {

		try {
			if (courseId == null ) {
				throw new Exception("Invalid CourseId");
			}
			
			//not set data in model attribute so got to controller
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			
			
			CourseEnrollment courseEnroll = new CourseEnrollment();					
			
			Course course=new Course();
			course.setCourseId(courseId);						
			courseEnroll.setCourse(course);	
			courseEnroll.setUser(user);
			
			courseEnrollmentService.courseEnroll(courseEnroll);

			return "redirect:/api/coursesenrollments/myCourses";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "redirect:../../coursevideos/listCourse?courseId="+ courseId;
		}
	}	
	
	@GetMapping("/myCourses")
	public String myCourses(ModelMap modelMap, HttpSession session) throws Exception {

		try {
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			System.out.println("myUsers:"+ user);
			List<CourseEnrollment> list = courseEnrollmentService.findByUserId(user.getId());
			System.out.println("list:" + list);
			modelMap.addAttribute("USER_COURSES_LIST", list);

			return "courseenrollment/userlist";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}	
	
	@GetMapping("/cancelCourse")
	public String cancelCourse(@RequestParam("id") Long id, ModelMap modelMap) throws Exception {

		try {
			if (id == null ) {
				throw new Exception("Invalid CourseId");
			}
			courseEnrollmentService.delete(Long.valueOf(id));

			return "redirect:/api/coursesenrollments/myCourses";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "courseenrollment/userlist";
		}

	}
	
}
