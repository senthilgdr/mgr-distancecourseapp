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
import in.tnmgrmu.model.CourseVideo;
import in.tnmgrmu.model.User;
import in.tnmgrmu.model.UserCourseVideo;
import in.tnmgrmu.service.CourseService;
import in.tnmgrmu.service.CourseVideoService;
import in.tnmgrmu.service.VideoService;

@Controller
@RequestMapping("coursevideos")
public class CourseVideoController {

	@Autowired
	private CourseVideoService courseVideoService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private VideoService videoService;

	@GetMapping("/listCourse")
	public String listCourse(@RequestParam("courseId") Long courseId, ModelMap modelMap, HttpSession session)
			throws Exception {

		try {
			System.out.println(courseId);
			
			User emp = (User) session.getAttribute("LOGGED_IN_USER");
			
			Course course = courseService.findById(courseId);
			modelMap.addAttribute("COURSE_DETAIL" , course );
			
			List<CourseVideo> list = courseVideoService.list(courseId);
			System.out.println("list:" + list);
			modelMap.addAttribute("COURSE_VIDEO_LIST", list);

			return "coursevideo/list";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			System.out.println("errorMessage"+e.getMessage());
			return "coursevideos/listCourse";
		}
	}
	
}
