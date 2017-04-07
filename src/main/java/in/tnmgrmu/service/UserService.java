package in.tnmgrmu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.tnmgrmu.dao.UserDAO;
import in.tnmgrmu.model.User;

@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;

	public User findByEmailAndPassword(String code, String password) {

		return userDAO.findByEmailAndPassword(code, password);
	}
	
	public User findById(Long userId) {

		return userDAO.findById(userId);
	}

	public List<User> list() {

		return userDAO.list();
	}

	public void delete(Long userId) {

		userDAO.delete(userId);
	}

	public void update(User user) {

		userDAO.update(user);
	}

	public void register(User user) {

		userDAO.save(user);
	}

}
