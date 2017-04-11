package in.tnmgrmu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.tnmgrmu.dao.CourseCategoryDAO;
import in.tnmgrmu.model.CourseCategory;

@Service
public class CourseCategoryService {
	@Autowired
	private CourseCategoryDAO courseCategoryDAO;

	public CourseCategory findById(Long id) {
		return courseCategoryDAO.findById(id);
	}

	public List<CourseCategory> list() {
		return courseCategoryDAO.list();

	}

	public void save(CourseCategory category) {

		courseCategoryDAO.save(category);
	}

	public void delete(Long categoryId) {

		courseCategoryDAO.delete(categoryId);
	}	
	public void update(CourseCategory category) {

		courseCategoryDAO.update(category);
	}

}
