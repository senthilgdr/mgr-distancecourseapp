
package in.tnmgrmu.controller;

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
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public String login(@RequestParam("emailId") String emailId, @RequestParam("password") String password,
			ModelMap modelMap, HttpSession session) throws Exception{
		
		try{			
		
		if (emailId == null || "".equals(emailId.trim())) {
			throw new Exception("Invalid EmailId");
		}
		
		if (password == null || "".equals(password.trim())) {
			throw new Exception("Invalid Password");
		}	
		
		
		User user = userService.findByEmailAndPassword(emailId, password);
		if (user != null) {
			session.setAttribute("LOGGED_IN_USER", user);

			return "/home"; 
		} else {
			modelMap.addAttribute("errorMessage", "Invalid EmailID/Password");
			return "/index";
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", "Invalid EmailID/Password");
			return "/index";
		
		}
	}
	@GetMapping("/create")
	public String create(){
		return "user/register";
	}
	
	@PostMapping("/register")
	public String register(@RequestParam("name") String name,@RequestParam("gender") String gender,@RequestParam("password") String password,
			 @RequestParam("role") Long role,
			@RequestParam("email") String email, 
			@RequestParam("mobileNo") Long mobileNo, ModelMap modelMap, HttpSession session) throws Exception {
		try {
			
			if (name == null || "".equals(name.trim())) {
				throw new Exception("Invalid Name");
			}
			if (gender == null || "".equals(gender.trim())) {
				throw new Exception("Invalid Gender");
			}
			
			if (password == null || "".equals(password.trim())) {
				throw new Exception("Invalid PassWord");
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
			
			User user = new User();
			
			user.setName(name);		
			user.setGender(gender);		
			user.setEmail(email);
			user.setPassword(password);
			user.setMobileNo(mobileNo);
			user.setActive(true);

			Role r = new Role();
			r.setId(role); // user
			user.setRole(r);
			System.out.println(user);

			userService.register(user);
			
			return "redirect:../";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/user/register";
		}

	}
	@GetMapping("/Logout")
	public String logout(HttpSession session) {

		session.invalidate();
		return "redirect:/";
	}
}
