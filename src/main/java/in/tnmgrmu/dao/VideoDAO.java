package in.tnmgrmu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import in.tnmgrmu.model.Video;


@Repository
public class VideoDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Video findById(Long id) {

		String sql = "select video_id,title,url,active,created_at,modified_at from videos where video_id= ?";
		Video Video = jdbcTemplate.queryForObject(sql, new Object[] { id }, (rs, rowNo) -> {
			return convert(rs);
		});
		return Video;

	}

	private Video convert(ResultSet rs) throws SQLException {
		Video video = new Video();
		video.setId(rs.getLong("video_id"));
		video.setTitle(rs.getString("title"));
		video.setUrl(rs.getString("url"));
		video.setActive(rs.getBoolean("active"));
		video.setCreatedDate(rs.getDate("created_at").toLocalDate());
		video.setModifiedDate(rs.getDate("modified_at").toLocalDate());
		
		return video;
	}

	public List<Video> list() {

		String sql = "select  video_id,title,url,active,created_at,modified_at from videos";

		List<Video> list = jdbcTemplate.query(sql, new Object[] {}, (rs, rowNum) -> {
			return convert(rs);
		});
		return list;
	}

	public void save(Video video) {

		String sql = "insert into videos ( title,url ) values ( ?,? )";

		int rows = jdbcTemplate.update(sql, video.getTitle(), video.getUrl());

		System.out.println("No of rows inserted:" + rows);
	}

	public void update(Video video) {

		String sql = "update videos set title=?, url= ? where video_id =? ";

		Integer rows = jdbcTemplate.update(sql, video.getTitle(), video.getUrl(), video.getId());

		System.out.println("No of rows modified:" + rows);

	}

	public void delete(Long videoId) {

		String sql = "delete from videos where video_id = ?";
		int rows = jdbcTemplate.update(sql, videoId);
		System.out.println("No of rows deleted:" + rows);

	}

}
