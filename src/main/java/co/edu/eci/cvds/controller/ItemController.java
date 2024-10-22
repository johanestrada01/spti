package co.edu.eci.cvds.controller;


import co.edu.eci.cvds.exceptions.ServiceException;
import co.edu.eci.cvds.model.Category;
import co.edu.eci.cvds.model.Item;
import co.edu.eci.cvds.model.Quotation;
import co.edu.eci.cvds.model.Vehicle;
import co.edu.eci.cvds.service.CategoryService;
import co.edu.eci.cvds.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/item")
public class ItemController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    @Autowired
    public ItemController(ItemService itemService, CategoryService categoryService){
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    @GetMapping("/add")
    public String addI(){
        if (!LoginController.isLogin()){
            return "redirect:/login/test";
        }
        return "new_service";
    }

    @PostMapping("/addItem")
    public String addItem(@ModelAttribute Item item){
        itemService.addItem(item);
        return "redirect:/user/getQuotation";
    }

    @GetMapping("/getItem/")
    public String getItem(@ModelAttribute("quotation") Quotation quotation, @ModelAttribute("vehicle") Vehicle vehicle,
                        @ModelAttribute("items") List<Integer> items, Model model) {
        try {
            ArrayList<Item> newItems = new ArrayList<>();
            for(Integer i : items){
                newItems.add(itemService.getItem(i));
            }
            model.addAttribute("quotation", quotation);
            model.addAttribute("vehicle", vehicle);
            model.addAttribute("items", newItems);
            return "imprimir";
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/update")
    public String udpateItem(Model model, @RequestParam(name="itemId") int id) throws ServiceException {
        model.addAttribute("item", itemService.getItem(id));
        model.addAttribute("categories",categoryService.getAllCategories());
        return "update_service";
    }

    @GetMapping("/update")
    public String updateItem(){
        return "redirect:/login/correct";
    }

    @GetMapping("/")
    public String getItemsByCategoryIdAndVehicleId(@ModelAttribute("vehicle") Vehicle vehicle, @ModelAttribute("categoryId") Integer categoryId,
                                                   @ModelAttribute("quotation") Quotation quotation, @ModelAttribute("categories") List<Category> categories,
                                                   Model model){
        List<Item> items = itemService.getItemsByVehicleIdAndCategoryId(categoryId, vehicle.getVehicleId());
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("categories", categories);
        model.addAttribute("quotation", quotation);
        model.addAttribute("items", items);
        return "quote";
    }

    @GetMapping("/getAllItems")
    public String getAllItems(Model model){
        model.addAttribute("items", itemService.getAllItems());
        if (!LoginController.isLogin()){
            return "redirect:/login/test";
        }
        return "admin_items";
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item){
        try {
            itemService.updateItem(item);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        if (!LoginController.isLogin()){
            return "redirect:/login/test";
        }
        return "redirect:/user/getQuotation";
    }

    @PostMapping("/deleteItem")
    public void deleteItem(@RequestBody Item item){
        itemService.deleteItem(item);
    }

    @PostMapping("/deleteItemById")
    public String deleteItemById(@RequestParam(name="itemId") int id){
        try {
            itemService.deleteItem(id);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return "admin_items";
    }

    @GetMapping("/getSubTotal")
    public void getSubTotal(@RequestBody Item item, Model model){
        model.addAttribute("subTotal", itemService.calculateSubtotal(item));
    }

    @GetMapping("/getSubTotalById/{id}")
    public void getSubtotalById(@PathVariable int id, Model model){
        try {
            model.addAttribute("subTotal", itemService.calculateSubtotal(id));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getTotal")
    public void getTotal(@RequestBody Item item, Model model){
        model.addAttribute("total", itemService.calculateTotal(item));
    }

    @GetMapping("/getTotalById/{id}")
    public void getTotalById(@PathVariable int id, Model model){
        try {
            model.addAttribute("total", itemService.calculateTotal(id));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/addCategory")
    public void addCategory(@RequestBody Item item, @RequestBody Category category){
        itemService.addCategory(item, category);
    }

    @PostMapping("/addCategoryById/{id}")
    public void addCategoryById(@PathVariable int id, @RequestBody Category category){
        try {
            itemService.addCategory(id, category);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
    
}

