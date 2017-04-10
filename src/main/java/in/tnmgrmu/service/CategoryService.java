package in.tnmgrmu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.tnmgrmu.dao.CategoryDAO;
import in.tnmgrmu.model.Category;

@Service
public class CategoryService {
	@Autowired
	private CategoryDAO categoryDAO;

	public Category findById(Long id) {
		return categoryDAO.findById(id);
	}

	public List<Category> list() {
		return categoryDAO.list();

	}

	public void save(Category category) {

		categoryDAO.save(category);
	}

	public void delete(Long categoryId) {

		categoryDAO.delete(categoryId);
	}

	public void update(Category category) {

		categoryDAO.update(category);
	}

}
