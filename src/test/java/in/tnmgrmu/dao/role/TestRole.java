/*package in.tnmgrmu.dao.role;

import java.util.List;

import org.springframework.stereotype.Repository;

import in.tnmgrmu.dao.RoleDAO;
import in.tnmgrmu.model.Role;


public class TestRole {
	
	public static void main(String[] args) {
		
		testFindAll();
		testFindOne();
	}
	
	private static  void testFindAll(){
	
		RoleDAO r=new RoleDAO();
		
		List<Role> list=r.list();
		for(Role role:list){
			System.out.println(role);
		}
		
		private static void testFindOne(){
			
			RoleDAO dao=new RoleDAO();
			
			Role role=dao.findById(1l);
			System.out.println(role);
			
		}
		
		private static void testDelete(){
			
			RoleDAO dao=new RoleDAO();
			dao.delete(1l);
			System.out.println();
		}
		
	}

}
*/