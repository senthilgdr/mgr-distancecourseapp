package in.tnmgrmu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.tnmgrmu.model.Category;
import in.tnmgrmu.service.CategoryService;



@Controller
@RequestMapping("categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/list")
	public String list(ModelMap modelMap, HttpSession session) throws Exception {

		try {

			List<Category> list = categoryService.list();
			System.out.println("list:" + list);
			modelMap.addAttribute("CATEGORY_LIST", list);

			return "category/list";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}

	@GetMapping("/create")
	public String create() {
		return "category/add";
	}

	@GetMapping("/save")
	public String save(@RequestParam("categoryName") String categoryName, ModelMap modelMap, HttpSession session) throws Exception {

		try {
			// Step : Store in View
			Category category = new Category();
			category.setCategoryName(categoryName);
			
			categoryService.save(category);

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
			categoryService.delete(Long.valueOf(id));

			return "redirect:/categories/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "category/list";
		}

	}

	@GetMapping("/edit")
	public String edit(@RequestParam("id") Long id, ModelMap modelMap) throws Exception {

		try {

			Category category = categoryService.findById(id);
			modelMap.addAttribute("EDIT_CATEGORY", category);

			return "category/edit";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "category/list";
		}

	}

	@GetMapping("/update")
	public String update(@RequestParam("id") Long id,@RequestParam("categoryName") String categoryName, ModelMap modelMap,
			HttpSession session) throws Exception {

		try {
			
			Category category = new Category();
			category.setCategoryId(id);
			category.setCategoryName(categoryName);
			categoryService.update(category);

			return "redirect:/categories/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "edit";
		}

	}

}
