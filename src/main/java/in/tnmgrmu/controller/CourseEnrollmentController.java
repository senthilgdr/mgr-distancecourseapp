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
			
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			
			CourseEnrollment courseEnroll = new CourseEnrollment();			
			courseEnroll.setUser(user);
			
			Course course=new Course();
			course.setCourseId(courseId);						
			courseEnroll.setCourse(course);	
			
			courseEnrollmentService.courseEnroll(courseEnroll);

			return "redirect:/api/coursesenrollments/myCourses";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "../coursevideos/listCourse";
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
			return "/coursevideos/listCourse";
		}
	}
	
	@GetMapping("/viewcourses")
	public String myCourses(@RequestParam("courseEnrollId") Long courseEnrollId,ModelMap modelMap, HttpSession session) throws Exception {

		try {
			if (courseEnrollId == null ) {
				throw new Exception("Invalid courseEnrollId");
			}
			
			User user = (User) session.getAttribute("LOGGED_IN_USER");
						
			List<CourseEnrollment> list = courseEnrollmentService.findByCourseId(courseEnrollId);
			System.out.println("list:" + list);
			modelMap.addAttribute("COURSE_LIST", list);

			return "courseenrollment/courselist";

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
			return "courseenrollment/list";
		}

	}
	
}
