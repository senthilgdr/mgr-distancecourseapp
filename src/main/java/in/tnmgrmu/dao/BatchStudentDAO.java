package in.tnmgrmu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import in.tnmgrmu.model.Batch;
import in.tnmgrmu.model.BatchStudent;
import in.tnmgrmu.model.Course;
import in.tnmgrmu.model.CourseVideo;
import in.tnmgrmu.model.User;
import in.tnmgrmu.model.Video;


@Repository
public class BatchStudentDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	

	private  BatchStudent convert(ResultSet rs) throws SQLException {
		BatchStudent  batchStudent = new  BatchStudent(); 
		batchStudent.setBatchStudentId(rs.getLong("id"));	
		
				
		Batch batch=new Batch();
		batch.setBatchId(rs.getLong("batch_id"));	
		batch.setBatchName(rs.getString("batch_name"));
		
		User user=new User();
		user.setId(rs.getLong("user_id"));
		user.setName(rs.getString("user_name"));
		
		batchStudent.setBatch(batch);
		batchStudent.setUser(user);
		
		
		return  batchStudent;
	}
	
	public List<BatchStudent> list() {
		
		String sql="SELECT bs.id,b.id AS batch_id,b.name AS batch_name ,u.id AS user_id,u.name AS user_name FROM "
				+ "batch_students bs,batches b,user_accounts u WHERE "
				+ "bs.batch_id=b.id  AND u.id=bs.user_id ";
		
		
		
		List<BatchStudent> list = jdbcTemplate.query(sql, new Object[] {}, (rs, rowNum) -> {
			return convert(rs);
			
		});
		System.out.println("ListBatchStudent:"+list);
		return list;
	}
public List<BatchStudent> findByBatch(Long batchId) {
		
		String sql="SELECT bs.id,b.id AS batch_id,b.name AS batch_name ,u.id AS user_id,u.name AS user_name FROM "
				+ "batch_students bs,batches b,user_accounts u WHERE "
				+ "bs.batch_id=b.id  AND u.id=bs.user_id AND bs.batch_id=? ";
		
		
		
		List<BatchStudent> list = jdbcTemplate.query(sql, new Object[] {batchId}, (rs, rowNum) -> {
			return convert(rs);
			
		});
		System.out.println("ListBatchStudent:"+list);
		return list;
	}
	
	public BatchStudent findById(Long id) {

		String sql = "SELECT bs.id,b.id AS batch_id,b.name AS batch_name ,u.id AS user_id,u.name AS user_name FROM "
				+ "batch_students bs,batches b,user_accounts u WHERE "
				+ "bs.batch_id=b.id  AND u.id=bs.user_id"
				+ " AND bs.id=?";

		BatchStudent list = jdbcTemplate.queryForObject(sql, new Object[] {id}, (rs, rowNum) -> {
			return convert(rs);
		});
		return list;
	}
	public void save(BatchStudent batchStudent) {

		String sql = "insert into batch_students(user_id,batch_id) values(?,?) ";

		Integer rows = jdbcTemplate.update(sql,batchStudent.getUser().getId(),batchStudent.getBatch().getBatchId());

		System.out.println("No of rows modified:" + rows);

	}
	public void update(BatchStudent batchStudent) {

		String sql = "update  batch_students set user_id=?,batch_id=? where id =? ";
System.out.println("Sql:"+sql);
		Integer rows = jdbcTemplate.update(sql,batchStudent.getUser().getId(),batchStudent.getBatch().getBatchId(),batchStudent.getBatchStudentId());

		System.out.println("No of rows modified:" + rows);

	}	
	public List<String> findAllBatchStudent() {

		String sql = "SELECT DISTINCT bacth_id FROM batch_students";

		List<String> list = jdbcTemplate.queryForList(sql, new Object[] {}, String.class);
		return list;
	}
	
	public void delete(Long studentId) {

		String sql = "delete from batch_students where id = ?";
		int rows = jdbcTemplate.update(sql, studentId);
		System.out.println("No of rows deleted:" + rows);

	}
	
}
