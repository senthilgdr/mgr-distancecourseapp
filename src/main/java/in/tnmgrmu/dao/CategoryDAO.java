package in.tnmgrmu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import in.tnmgrmu.model.Category;


@Repository
public class CategoryDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Category findById(Long id) {

		String sql = "select  category_id,category_name,active,created_at,modified_at from categories where category_id= ?";
		Category category = jdbcTemplate.queryForObject(sql, new Object[] { id }, (rs, rowNo) -> {
			return convert(rs);
		});
		return category;

	}

	private Category convert(ResultSet rs) throws SQLException {
		Category category = new Category();
		category.setCategoryId(rs.getLong("category_id"));
		category.setCategoryName(rs.getString("category_name"));		
		category.setActive(rs.getBoolean("active"));		
		category.setCreatedDate(rs.getDate("created_at").toLocalDate());			
		category.setModifiedDate(rs.getDate("modified_at").toLocalDate());
		return category;
	}

	public List<Category> list() {

		String sql = "select category_id,category_name,active,created_at,modified_at from categories";

		List<Category> list = jdbcTemplate.query(sql, new Object[] {}, (rs, rowNum) -> {
			return convert(rs);
		});
		return list;
	}

	public void save(Category category) {

		String sql = "insert into categories ( category_name) values ( ? )";

		int rows = jdbcTemplate.update(sql, category.getCategoryName());

		System.out.println("No of rows inserted:" + rows);
	}

	public void update(Category category) {

		String sql = "update categories set category_name=? where  category_id =? ";

		Integer rows = jdbcTemplate.update(sql, category.getCategoryName(),category.getCategoryId());

		System.out.println("No of rows modified:" + rows);

	}

	public void delete(Long categoryId) {

		String sql = "delete from categories where category_id = ?";
		int rows = jdbcTemplate.update(sql, categoryId);
		System.out.println("No of rows deleted:" + rows);

	}

}
