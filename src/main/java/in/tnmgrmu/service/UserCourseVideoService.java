package in.tnmgrmu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.tnmgrmu.dao.UserCourseVideoDAO;
import in.tnmgrmu.model.UserCourseVideo;

@Service
public class UserCourseVideoService {
	@Autowired
	private UserCourseVideoDAO userCourseVidoeDAO;

	

	public List<UserCourseVideo> list(Long courseId,Long userId) {
		return userCourseVidoeDAO.list(courseId,userId);
	}
	
	public Long pendingVideos(Long courseId,Long userId) throws Exception {
		return userCourseVidoeDAO.pendingVideos(courseId,userId);
	}
	
	public Long completedVideos(Long courseId,Long userId) throws Exception {
		return userCourseVidoeDAO.completeVideos(courseId,userId);
	}
		
}
