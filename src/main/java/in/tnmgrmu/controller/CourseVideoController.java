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

	@GetMapping("/list")
	public String list(@RequestParam("courseId") Long courseId, ModelMap modelMap, HttpSession session)
			throws Exception {

		try {
			System.out.println(courseId);
			
			User emp = (User) session.getAttribute("LOGGED_IN_USER");
			
			Course course = courseService.findById(courseId);
			modelMap.addAttribute("COURSE_DETAIL" , course );
			
			List<UserCourseVideo> list = courseVideoService.list(courseId,emp.getId());
			System.out.println("list:" + list);
			modelMap.addAttribute("USER_COURSE_VIDEO_LIST", list);

			return "coursevideo/list";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}
	
	@GetMapping("/updateStatus")
	public String updateStatus(@RequestParam("id") Long id,@RequestParam("courseId") Long courseId, ModelMap modelMap,
			HttpSession session) throws Exception {

		System.out.println("Update Status-> id=" + id );
		try {
			
	    	courseVideoService.updateStatus(id);

			return "redirect:/coursevideos/list?courseId="+ courseId;
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "coursevideo/list?courseId=" + courseId;
		}

	}

}
