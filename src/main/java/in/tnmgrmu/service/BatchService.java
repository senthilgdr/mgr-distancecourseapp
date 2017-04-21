package in.tnmgrmu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.tnmgrmu.dao.BatchsDAO;
import in.tnmgrmu.model.Batch;

@Service
public class BatchService {
	@Autowired
	private BatchsDAO batchDAO;

	public Batch findById(Long id) {
		return batchDAO.findById(id);
	}

	public List<Batch> list() {
		return batchDAO.list();

	}

	public void save(Batch batch) {

		batchDAO.save(batch);
	}

	public void delete(Long batchId) {

		batchDAO.delete(batchId);
	}

	public void update(Batch batch) {

		batchDAO.update(batch);
	}

}
