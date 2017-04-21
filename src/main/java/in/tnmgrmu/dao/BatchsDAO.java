package in.tnmgrmu.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import in.tnmgrmu.model.Batch;

@Repository
public class BatchsDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Batch findById(Long id) {

		String sql = "select id,name,start_date,end_date from batches where id= ?";
		Batch batch = jdbcTemplate.queryForObject(sql, new Object[] { id }, (rs, rowNo) -> {
			return convert(rs);
		});
		return batch;

	}

	private Batch convert(ResultSet rs) throws SQLException {

		Batch batch = new Batch();
		batch.setBatchId(rs.getLong("id"));
		batch.setBatchName(rs.getString("name"));

		batch.setStartDate(rs.getDate("start_date").toLocalDate());

		Date endDate = rs.getDate("end_date");
		if (endDate != null) {
			batch.setEndDate(endDate.toLocalDate());
		}

		return batch;
	}

	public List<Batch> list() {

		String sql = "select id,name,start_date,end_date from batches";

		List<Batch> list = jdbcTemplate.query(sql, new Object[] {}, (rs, rowNum) -> {
			return convert(rs);
		});
		return list;
	}

	public void save(Batch batch) {

		String sql = "insert into batches ( name) values ( ?)";

		int rows = jdbcTemplate.update(sql, batch.getBatchName());

		System.out.println("No of rows inserted:" + rows);

	}

	public void update(Batch batch) {

		String sql = "update batches set name=? where id =? ";

		Integer rows = jdbcTemplate.update(sql, batch.getBatchName(), batch.getBatchId());

		System.out.println("No of rows modified:" + rows);

	}

	public void delete(Long batchId) {

		String sql = "delete from batches where id = ?";
		int rows = jdbcTemplate.update(sql, batchId);
		System.out.println("No of rows deleted:" + rows);

	}

}
