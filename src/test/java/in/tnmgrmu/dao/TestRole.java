package in.tnmgrmu.dao;

import java.util.List;

import in.tnmgrmu.model.Role;
import in.tnmgrmu.service.RoleService;

public class TestRole {

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
		RoleService rs = new RoleService();

		List<Role> list = rs.list();

		for (Role role : list) {

			System.out.println(role);
		}

	}
	
	public static void testFindById()

	{
		RoleService rs = new RoleService();

		Role list = rs.findById(1L);
		System.out.println(list);

	}
	public static void testInsert()

	{
		RoleService rs = new RoleService(); 
		Role role=new Role();
        role.setName("Trainer");
        rs.save(role);
        System.out.println("Inserted:"+ role);

	}
	public static void testUpdate()

	{
		RoleService rs = new RoleService(); 
		Role role = rs.findById(1L);
        role.setName("Trainer");
        role.setActive(false);
        rs.save(role);
        System.out.println("Inserted:"+ role);

	}

}
