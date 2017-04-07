package in.tnmgrmu.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.tnmgrmu.model.Role;
import in.tnmgrmu.model.User;
import in.tnmgrmu.service.RoleService;
import in.tnmgrmu.service.UserService;


public class TestUser {
	
	static UserService rs = new UserService();

	public static void main(String[] args) {

	//	testInsert();
	//	testUpdate();
		testDelete();
		testFindAll();
		testFindById();
	}

	private static void testDelete() {
		
		
	}

	public static void testFindAll()

	{
		
		List<User> list = rs.list();

		for (User user : list) {

			System.out.println(user);
		}

	}
	
	public static void testFindById()

	{
		
		User list = rs.findById(1L);
		System.out.println(list);

	}
	public static void testInsert()

	{
		
		User user=new User();
        user.setName("Ganesan");
        user.setEmail("ganes@gmail.com");
        user.setPassword("pass123");
        user.setMobileNo(9900000L);
        
        rs.register(user);
        System.out.println("Inserted:"+ user);

	}
	public static void testUpdate()

	{
		User user = rs.findById(7L);
        user.setName("Ganesan");
        user.setEmail("ganes@gmail.com");
        user.setPassword("pass123");
        user.setMobileNo(9900000L);
      //  user.setRole(role);
        
        rs.update(user);
        System.out.println("updated:"+ user);
        

	}

}
