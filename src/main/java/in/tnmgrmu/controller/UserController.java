package in.tnmgrmu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.tnmgrmu.model.Role;
import in.tnmgrmu.model.User;
import in.tnmgrmu.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/list")
	public String list(ModelMap modelMap, HttpSession session) throws Exception {

		try {

			List<User> list = userService.list();
			System.out.println(list);
			modelMap.addAttribute("USER_LIST", list);

			return "user/list";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("id") Long id, ModelMap modelMap) throws Exception {

		try {
			if (id == null) {
				throw new Exception("Invalid UserId");
			}
			System.out.println(id);	
			userService.delete(Long.valueOf(id));

			return "redirect:/users/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "user/list";
		}
	}

	@GetMapping("/edit")
	public String edit(@RequestParam("id") Long id, ModelMap modelMap, HttpSession session) throws Exception {

		try {
			if (id == null) {
				throw new Exception("Invalid UserId");
			}
			System.out.println(id);	
			User emp = (User) session.getAttribute("LOGGED_IN_USER");
			modelMap.addAttribute("EDIT_USER", emp);

			return "user/edit";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "user/list";
		}

	}

	@GetMapping("/update")
	public String update(@RequestParam("id") Long id, @RequestParam("name") String name,
			@RequestParam("role") Long role,
			@RequestParam("email") String email, @RequestParam("mobileNo") Long mobileNo, ModelMap modelMap,
			HttpSession session) throws Exception {

		try {
			if (id == null ) {
				throw new Exception("Invalid userId");
			}
			if (name == null || "".equals(name.trim())) {
				throw new Exception("Invalid Name");
			}				
			
			if (role == null ) {
				throw new Exception("Invalid RoleId");
			}
			if (email == null || "".equals(email.trim())) {
				throw new Exception("Invalid Email");
			}
			if (mobileNo == null) {
				throw new Exception("Invalid MobileNo");
			}
			User user = userService.findById(id);
			user.setName(name);
			user.setEmail(email);
			user.setMobileNo(mobileNo);

			Role r = new Role();
			r.setId(role);

			user.setRole(r);
			userService.update(user);

			User usernew = userService.findById(id);
			session.setAttribute("LOGGED_IN_USER", usernew);

			System.out.println("Updated" + user);

			return "redirect:/users/myProfile";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "edit";
		}

	}

	@GetMapping("/myProfile")
	public String showMyprofile(ModelMap modelMap, HttpSession session) throws Exception {

		try {
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			System.out.println(user);

			return "user/profile";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}
	
	@GetMapping("/getForgotpassword")
	public String forgotpassword() {
		System.out.println("User->ForgotPassword");
		return "user/forgotpassword";
	}

	@PostMapping("/forgotPassword")
	public String forgotPassword(@RequestParam("emailId") String emailId, ModelMap modelMap) throws Exception {

		try {
			if (emailId == null || "".equals(emailId.trim())) {
				throw new Exception("Invalid EmailId");
			}
			userService.forgotPassword(emailId);

			return "redirect:/";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "user/forgotpassword";
		}

	}
	

	@GetMapping("/changepassword")
	public String changepassword() {
		return "user/changepassword";
	}

	@PostMapping("/updatePassword")
	public String changePassword(@RequestParam("currentpassword") String currentpassword,
			@RequestParam("newpassword") String newpassword, ModelMap modelMap, HttpSession session) throws Exception {

		try {
			if (currentpassword == null || "".equals(currentpassword.trim())) {
				throw new Exception("Invalid CurrentPassword");
			}
			if (newpassword == null || "".equals(newpassword.trim())) {
				throw new Exception("Invalid NewPassword");
			}
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			System.out.println("UpdatePassword:" + user);
			userService.changePassword(user.getEmail(), currentpassword, newpassword);
			session.invalidate();
			return "redirect:/";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "index";
		}

	}
	
	@GetMapping("/accountAcivate")
	public String accountAcivate(HttpSession session, ModelMap modelMap) throws Exception {

		try {
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			userService.accountActivate(user);
			return "redirect:/";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "index";
		}

	}

}
