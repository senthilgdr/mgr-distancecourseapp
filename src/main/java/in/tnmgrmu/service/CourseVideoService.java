package in.tnmgrmu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.tnmgrmu.dao.CourseVideoDAO;
import in.tnmgrmu.model.CourseVideo;

@Service
public class CourseVideoService {
	@Autowired
	private CourseVideoDAO courseVidoeDAO;
	
	public List<CourseVideo> list(Long courseId) {
		return courseVidoeDAO.list(courseId);

	}
	public CourseVideo findById(Long id) { 
		return courseVidoeDAO.findById(id);

	}	
	
}
