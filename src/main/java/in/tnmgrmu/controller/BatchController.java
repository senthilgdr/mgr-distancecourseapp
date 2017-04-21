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
import in.tnmgrmu.service.BatchService;

@Controller
@RequestMapping("batches")
public class BatchController {

	@Autowired
	private BatchService batchService;

	@GetMapping("/list")
	public String list(ModelMap modelMap, HttpSession session) throws Exception {

		try {

			List<Batch> list = batchService.list();
			System.out.println("list:" + list);
			modelMap.addAttribute("BATCH_LIST", list);

			return "batch/list";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}
	
	@GetMapping("/create")
	public String create() {
		return "batch/add";
	}

	@GetMapping("/save")
	public String save(@RequestParam("name") String name, ModelMap modelMap, HttpSession session) throws Exception {

		try {

			// Step : Store in View
			Batch batch = new Batch();
			batch.setBatchName(name);
			batchService.save(batch);

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
			batchService.delete(Long.valueOf(id));

			return "redirect:/batches/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "batch/list";
		}

	}

	@GetMapping("/edit")
	public String edit(@RequestParam("id") Long id, ModelMap modelMap) throws Exception {

		try {

			Batch batch = batchService.findById(id);
			modelMap.addAttribute("EDIT_BATCH", batch);

			return "batch/edit";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "batch/list";
		}

	}

	@GetMapping("/update")
	public String update(@RequestParam("id") Long id, @RequestParam("name") String name, ModelMap modelMap,
			HttpSession session) throws Exception {

		try {

			Batch batch = new Batch();
			batch.setBatchId(id);
			batch.setBatchName(name);
			batchService.update(batch);

			return "redirect:/batches/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "edit";
		}

	}

}
