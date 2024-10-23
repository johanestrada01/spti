package co.edu.eci.cvds.controller;


import co.edu.eci.cvds.exceptions.ControllerException;
import co.edu.eci.cvds.exceptions.ServiceException;
import co.edu.eci.cvds.model.Category;
import co.edu.eci.cvds.model.Quotation;
import co.edu.eci.cvds.model.Vehicle;
import co.edu.eci.cvds.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/getAllCategories")
    public String getAllCategories(@ModelAttribute("vehicle") Vehicle vehicle, @ModelAttribute("category") Category category,
                                   @ModelAttribute("quotation") Quotation quotation,
                                   Model model, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("quotation", quotation);
        redirectAttributes.addFlashAttribute("vehicle", vehicle);
        Integer categoryId = (category.getCategoryId() == null) ? 1 : category.getCategoryId();
        redirectAttributes.addFlashAttribute("categoryId", categoryId);
        redirectAttributes.addFlashAttribute("categories", categoryService.getAllCategories());
        return "redirect:/quotation/getQuotation";
    }

    @PostMapping("/putCategory")
    public String addCategory(@RequestBody Category category, Model model){
        categoryService.addCategory(category);
        return "redirect://quote";
    }


    @GetMapping("/getCategory/{id}")
    public void getCategory(@PathVariable int id, Model model) throws ControllerException {
        try {
            model.addAttribute("category", categoryService.getCategory(id));
        }
        catch (ServiceException e){
            throw new ControllerException("Bad Request");
        }
    }

    
    @PostMapping("/updateCategory")
    public void updateCategory(@RequestBody Category category) throws ControllerException {
        try {
            categoryService.updateCategory(category);
        } catch (ServiceException e) {
            throw new ControllerException("Bad Request");
        }
    }

    @PostMapping("/deleteCategory")
    public void deleteCategory(@RequestBody Category category){
        categoryService.deleteCategory(category);
    }

    @PostMapping("/deleteCategoryById/{id}")
    public void deleteCategoryById(@PathVariable int id) throws ControllerException {
        try {
            categoryService.deleteCategory(id);
        } catch (ServiceException e) {
            throw new ControllerException("Bad Request");
        }
    }
    
}
