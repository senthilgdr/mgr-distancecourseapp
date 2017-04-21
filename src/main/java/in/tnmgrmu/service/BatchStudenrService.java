package in.tnmgrmu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.tnmgrmu.dao.BatchStudentDAO;
import in.tnmgrmu.model.BatchStudent;

@Service
public class BatchStudenrService {
	@Autowired
	private BatchStudentDAO batchStudentDAO;

	public BatchStudent findById(Long id) {
		return batchStudentDAO.findById(id);
	}

	public List<BatchStudent> list() {
		return batchStudentDAO.list();

	}
	public List<BatchStudent> findByBatch(Long batchId) {
		return batchStudentDAO.findByBatch(batchId);

	}

	public void save(BatchStudent batch) {

		batchStudentDAO.save(batch);
	}

	public void delete(Long batchId) {

		batchStudentDAO.delete(batchId);
	}

	public void update(BatchStudent batch) {

		batchStudentDAO.update(batch);
	}	
	
}
