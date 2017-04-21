package in.tnmgrmu.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Batch {

	private Long batchId;
	private String batchName;
	private LocalDate startDate;
	private LocalDate endDate;
	

}
