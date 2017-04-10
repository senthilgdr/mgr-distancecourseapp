package in.tnmgrmu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.tnmgrmu.dao.UserDAO;
import in.tnmgrmu.mailmanager.UserMailManager;
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
	public void accountActivate(User user) {

		userDAO.accountActivate(user);
	}

	public void register(User user) throws Exception {

		userDAO.save(user);
		UserMailManager.sendNewRegistrationEmail(user);
	}
	public void forgotPassword(String emailId) throws Exception {

		User user = userDAO.findByEmailId(emailId);

		if (user == null) {
			throw new Exception("MailId does not exists");
		}
		UserMailManager.sendPassword(user);

	}
	public void changePassword(String emailId, String oldPassword, String newPassword) throws Exception {

		if (oldPassword.equals(newPassword)) {
			throw new Exception("Old Password and New Password is same");
		}

		User user = userDAO.findByEmailAndPassword(emailId, oldPassword);

		if (user == null) {
			throw new Exception("Invalid EmailId/Password");
		}

		boolean isModified = userDAO.changePassword(emailId, oldPassword, newPassword);
		if (isModified) {

			userDAO.addPasswordEntry(user.getId(), oldPassword, newPassword);
		} else {
			throw new Exception("Unable to change Password.");

		}
		UserMailManager.changePassword(user, newPassword);
	}
}
