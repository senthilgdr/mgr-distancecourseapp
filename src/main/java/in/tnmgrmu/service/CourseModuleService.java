package in.tnmgrmu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.tnmgrmu.dao.CourseModuleDAO;
import in.tnmgrmu.model.CourseModule;

@Service
public class CourseModuleService {
	@Autowired
	private CourseModuleDAO courseModuleDAO;

	public CourseModule findById(Long id) {
		return courseModuleDAO.findById(id);
	}

	public List<CourseModule> list() {
		return courseModuleDAO.list();

	}

	public void delete(Long courseId) {

		courseModuleDAO.delete(courseId);
	}

		
}
