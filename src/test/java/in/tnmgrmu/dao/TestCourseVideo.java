package in.tnmgrmu.dao;

import java.util.List;

import in.tnmgrmu.model.Batch;
import in.tnmgrmu.model.Course;
import in.tnmgrmu.model.CourseVideo;
import in.tnmgrmu.model.Role;
import in.tnmgrmu.service.RoleService;

public class TestCourseVideo {

	public static void main(String[] args) {

		//testFindAll();
		
		Batch course=new Batch();
		course.setBatchName("JEE");
		BatchsDAO dao=new BatchsDAO();
		dao.save(course);
		
		System.out.println("Inserted:"+course);
		
	}

	/*
	public static void testFindAll()

	{
		CourseVideo rs = new CourseVideo();

		List<CourseVideo> list = rs.list();

		for (CourseVideo role : list) {

			System.out.println(role);
		}

	}	*/

}
