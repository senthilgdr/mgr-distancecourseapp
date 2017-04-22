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
import in.tnmgrmu.service.UserCourseVideoService;
import in.tnmgrmu.service.VideoService;

@Controller
@RequestMapping("usercoursevideos")
public class UserCourseVideoController {
	@Autowired
	private UserCourseVideoService userCourseVideoService;

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
			if (courseId == null ) {
				throw new Exception("Invalid UserCourseId");
			}
			
			System.out.println(courseId);
			
			User emp = (User) session.getAttribute("LOGGED_IN_USER");
			
			Course course = courseService.findById(courseId);
			modelMap.addAttribute("COURSE_DETAIL" , course );
			
			Long pendingVideos=userCourseVideoService.pendingVideos(courseId, emp.getId());
			modelMap.addAttribute("PENDING_VIDEO_COUNT", pendingVideos);
			
			System.out.println("Pending Videos:" + pendingVideos);
			
			Long completedVideos=userCourseVideoService.completedVideos(courseId, emp.getId());
			modelMap.addAttribute("COMPLETED_VIDEO_COUNT", completedVideos);
			
			Long percentage = 0L;
			if ( completedVideos > 0 ){
				percentage = 100 * completedVideos / ( pendingVideos + completedVideos) ;
			}
			modelMap.addAttribute("COURSE_PERCENTAGE", percentage);
			System.out.println("percentage Videos:" + percentage);
			
			List<UserCourseVideo> list = userCourseVideoService.list(courseId,emp.getId());
			System.out.println("list:" + list);
			modelMap.addAttribute("USER_COURSE_VIDEO_LIST", list);

			return "usercoursevideo/list";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "usercoursevideo/list";
		}
	}
	
	@GetMapping("/updateStatus")
	public String updateStatus(@RequestParam("id") Long id,@RequestParam("courseId") Long courseId, ModelMap modelMap,
			HttpSession session) throws Exception {

		System.out.println("Update Status-> id=" + id );
		try {
			if (id == null ) {
				throw new Exception("Invalid UserCourseId");
			}
			userCourseVideoService.updateStatus(id);

			return "redirect:/usercoursevideos/list?courseId=" + courseId;
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "usercoursevideos/list?courseId=" + courseId;
		}

	}

}
