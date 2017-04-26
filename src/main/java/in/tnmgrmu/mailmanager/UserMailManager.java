package in.tnmgrmu.mailmanager;

import in.tnmgrmu.model.User;

/* 3 methods in mailmanager*/
public class UserMailManager {
	
	public static void sendNewRegistrationEmail(User user) throws Exception {

		String subject = "Reg: Welcome to MGR-DisatanceCourse App";

		StringBuilder message = new StringBuilder();

		message.append("Dear" + user.getName() + ",\n");
		message.append("Successfully you are registered to MGr-Distance CourseApp Website.");
		
		message.append("\n\n Your Activation Code is :abcd ");
		message.append("\n Please click the activation link below:");
		message.append("\n http://localhost:8080/auth/accountActivation");
		message.append("\n\nRegards ");
		message.append("\n MGR-DisatanceCourse App Support Team");

		MailUtil.sendMail(user.getEmail(), subject, message.toString());
		System.out.println("Mail sent!");

	}	
	
	public static void sendPassword(User user) throws Exception {

		String subject = "Reg:Your Forgot Password";

		StringBuilder message = new StringBuilder();

		message.append("Dear " + user.getName() + "...\n");
		message.append("Your password here " + user.getPassword() + "\n"); 
																
		message.append("Regards, \n");
		message.append("MGR-DisatanceCourse App Support Team");

		MailUtil.sendMail(user.getEmail(), subject, message.toString());

	}
	
	public static void changePassword(User user, String newPassword) throws Exception {

		String subject = "Reg:Your New Password";
		StringBuilder message = new StringBuilder();

		message.append("Dear " + user.getName() + "...\n");
		message.append("Your new password here (" + newPassword + ")\n"); // send password																			// password
		message.append("Regards \n");
		message.append("MGR-DisatanceCourse App Support Team");

		MailUtil.sendMail(user.getEmail(), subject, message.toString());
		
	}

}
