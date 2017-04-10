package in.tnmgrmu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.tnmgrmu.dao.VideoDAO;
import in.tnmgrmu.model.Video;

@Service
public class VideoService {
	@Autowired
	private VideoDAO videoDAO;

	public Video findById(Long id) {
		return videoDAO.findById(id);
	}

	public List<Video> list() {
		return videoDAO.list();

	}

	public void save(Video video) {

		videoDAO.save(video);
	}

	public void delete(Long videoId) {

		videoDAO.delete(videoId);
	}

	public void update(Video Video) {

		videoDAO.update(Video);
	}

}
