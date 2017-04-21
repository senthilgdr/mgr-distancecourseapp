package in.tnmgrmu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.tnmgrmu.model.Batch;
import in.tnmgrmu.model.BatchStudent;
import in.tnmgrmu.model.Course;
import in.tnmgrmu.model.User;
import in.tnmgrmu.service.BatchService;
import in.tnmgrmu.service.BatchStudenrService;


@Controller
@RequestMapping("batchstudents")
public class BatchStudentController {

	@Autowired
	private BatchStudenrService batchStudentService;
	
	@Autowired
	private BatchService batchService;
	
	@GetMapping("/listBatchStudent")
	public String listBatchStudent(@RequestParam("batchId") Long batchId,ModelMap modelMap, HttpSession session) throws Exception {

		try {

			Batch b = batchService.findById(batchId);
			System.out.println("list:" + b);
			modelMap.addAttribute("BATCH_DETAIL", b);
			
			List<BatchStudent> list = batchStudentService.findByBatch(batchId);
			System.out.println("list:" + list);
			modelMap.addAttribute("BATCH_STUDENT_LIST", list);

			return "batchstudent/listBatchStudent";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}
	@GetMapping("/list")
	public String list(ModelMap modelMap, HttpSession session) throws Exception {

		try {

			List<BatchStudent> list = batchStudentService.list();
			System.out.println("list:" + list);
			modelMap.addAttribute("BATCH_STUDENT_LIST", list);

			return "batchstudent/list";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}
	
	@GetMapping("/create")
	public String create(ModelMap modelMap) {
		List<Batch> list = batchService.list();
		modelMap.addAttribute("BATCH_LIST" , list );
		return "batchstudent/add";
	}

	@GetMapping("/save")
	public String save(@RequestParam("batchId") Long batchId, ModelMap modelMap, HttpSession session) throws Exception {

		try {
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			// Step : Store in View
			BatchStudent batchStudent = new BatchStudent();
			
			Batch batch=new Batch();
			batch.setBatchId(batchId);
			
			batchStudent.setBatch(batch);
			batchStudent.setUser(user);
						
			batchStudentService.save(batchStudent);

			return "redirect:list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "add";
		}
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("id") Long id, ModelMap modelMap) throws Exception {

		try {
			batchStudentService.delete(Long.valueOf(id));

			return "redirect:/batchstudents/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "batchstudent/list";
		}

	}
	
	
	@GetMapping("/edit")
	public String edit(@RequestParam("id") Long id, ModelMap modelMap) throws Exception {

		try {
			List<Batch> list = batchService.list();
			modelMap.addAttribute("BATCH_LIST" , list );
			
			BatchStudent batchStudent = batchStudentService.findById(id);
			System.out.println("BatchStudent:"+batchStudent);
			modelMap.addAttribute("EDIT_BATCH_STUDENT", batchStudent);

			return "batchstudent/edit";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "batchstudent/list";
		}

	}

	@GetMapping("/update")
	public String update(@RequestParam("id") Long id,@RequestParam("batchId") Long batchId, ModelMap modelMap,
			HttpSession session) throws Exception {

		try {
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			// Step : Store in View
			BatchStudent batchStudent = new BatchStudent();
			batchStudent.setBatchStudentId(id);
			
			Batch batch=new Batch();
			batch.setBatchId(batchId);
			
			batchStudent.setBatch(batch);
			batchStudent.setUser(user);			
						
			batchStudentService.update(batchStudent);
			
			return "redirect:/batchstudents/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "edit";
		}

	}
	
	
}
