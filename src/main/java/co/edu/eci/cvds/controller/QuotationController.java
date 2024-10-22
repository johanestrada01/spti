package co.edu.eci.cvds.controller;

import co.edu.eci.cvds.exceptions.ServiceException;
import co.edu.eci.cvds.model.*;
import co.edu.eci.cvds.service.ItemService;
import co.edu.eci.cvds.service.QuotationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/quotation")
public class QuotationController {

    QuotationService quotationService;

    @Autowired
    public QuotationController(QuotationService quotationService){
        this.quotationService = quotationService;
    }

    @GetMapping("/addQuotation")
    public String addQuotation(@ModelAttribute("vehicle") Vehicle vehicle, @ModelAttribute("categoryId") Integer categoryId,
                               Model model, RedirectAttributes redirectAttributes){
        Quotation quotation = new Quotation();
        quotationService.addQuotation(quotation);
        redirectAttributes.addFlashAttribute("quotation", quotation);
        redirectAttributes.addFlashAttribute("vehicle", vehicle);
        redirectAttributes.addFlashAttribute("categoryId", categoryId);
        return "redirect:/category/getAllCategories";
    }

    @GetMapping("/getQuotation")
    public String getQuotation(@ModelAttribute("vehicle") Vehicle vehicle, @ModelAttribute("categoryId") Integer categoryId,
                             @ModelAttribute("quotation") Quotation quotation, @ModelAttribute("categories") List<Category> categories,
                             Model model, RedirectAttributes redirectAttributes){
        try {
            redirectAttributes.addFlashAttribute("vehicle", vehicle);
            redirectAttributes.addFlashAttribute("categoryId", categoryId);
            redirectAttributes.addFlashAttribute("categories", categories);
            redirectAttributes.addFlashAttribute("quotation", quotationService.getQuotation(quotation.getQuotationId()));
            return "redirect:/item/";
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getAllQuotations")
    public void getAllQuotations(Model model){
        model.addAttribute("quotations", quotationService.getAllQuotation());
    }

    @PostMapping("/updateQuotation")
    public void updateQuotation(@RequestBody Quotation quotation, Model model){
        try {
            quotationService.updateQuotation(quotation);
            model.addAttribute("quotation", quotation);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/deleteQuotation")
    public void deleteQuotation(@RequestBody Quotation quotation){
        quotationService.deleteQuotation(quotation);
    }

    @PostMapping("/deleteQuotationById/{id}")
    public void deleteQuotation(@PathVariable int id){
        try {
            quotationService.deleteQuotation(id);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getTotal")
    public void calculateTotal(Quotation quotation, Model model){
        model.addAttribute("total", quotationService.calculateTotal(quotation));
    }

    @GetMapping("/getTotalById")
    public String calculateTotalById(@ModelAttribute("quotation") Quotation quotation, @ModelAttribute("vehicle") Vehicle vehicle,
                                   @ModelAttribute("categoryId") Integer categoryId, @ModelAttribute("item") Item item, Model model,
                                   RedirectAttributes redirectAttributes){
        try {
            redirectAttributes.addFlashAttribute("vehicle", vehicle);
            redirectAttributes.addFlashAttribute("item", item);
            redirectAttributes.addFlashAttribute("categoryId", categoryId);
            quotation.setTotal(quotationService.calculateTotal(quotation));
            quotationService.updateQuotation(quotation);
            redirectAttributes.addFlashAttribute("quotation", quotationService.getQuotation(quotation.getQuotationId()));
            return "redirect:/category/getAllCategories";
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getSubTotal")
    public void calculateSubTotal(@RequestBody Quotation quotation, Model model){
        model.addAttribute("subTotal", quotationService.calculateSubTotal(quotation));
    }

    @GetMapping("/getSubTotalById")
    public String calculateSubTotalById(@ModelAttribute("quotation") Quotation quotation, @ModelAttribute("vehicle") Vehicle vehicle,
                                        @ModelAttribute("categoryId") Integer categoryId, @ModelAttribute("item") Item item, Model model,
                                      RedirectAttributes redirectAttributes){
        try {
            redirectAttributes.addFlashAttribute("vehicle", vehicle);
            redirectAttributes.addFlashAttribute("item", item);
            redirectAttributes.addFlashAttribute("categoryId", categoryId);
            quotation.setSubtotal(quotationService.calculateSubTotal(quotation));
            quotationService.updateQuotation(quotation);
            redirectAttributes.addFlashAttribute("quotation", quotationService.getQuotation(quotation.getQuotationId()));
            return "redirect:/quotation/getTotalById";
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/updateStatus")
    public void updateStatus(@RequestBody Quotation quotation, @RequestBody String status, Model model){
        quotationService.updateStatus(quotation, status);
        model.addAttribute("quotation", quotation);
    }

    @GetMapping("/updateStatusById")
    public String updateStatusById(@ModelAttribute("quotation") Quotation quotation, @ModelAttribute("vehicle") Vehicle vehicle,
                                   @ModelAttribute("item") Item item, @ModelAttribute("categoryId") String categoryId,
                                   Model model, RedirectAttributes redirectAttributes){
        try {
            quotationService.updateStatus(quotation.getQuotationId(), "EN_PROCESO");
            redirectAttributes.addFlashAttribute("quotation", quotationService.getQuotation(quotation.getQuotationId()));
            redirectAttributes.addFlashAttribute("vehicle", vehicle);
            redirectAttributes.addFlashAttribute("item", item);
            Integer newCategoryId = Integer.parseInt(categoryId);
            redirectAttributes.addFlashAttribute("categoryId", newCategoryId);
            return "redirect:/quotation/addItemById";
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getQuotationValues")
    public String getQuotationValues(@ModelAttribute("quotation") Quotation quotation, @ModelAttribute("vehicle") Vehicle vehicle,
                                     @ModelAttribute("item") Item item, @ModelAttribute("categoryId") String categoryId,
                                     Model model, RedirectAttributes redirectAttributes){
        try {
            redirectAttributes.addFlashAttribute("vehicle", vehicle);
            redirectAttributes.addFlashAttribute("item", item);
            redirectAttributes.addFlashAttribute("categoryId", categoryId);
            quotation.setSubtotal(quotationService.calculateSubTotal(quotation));
            quotation.setTotal(quotationService.calculateTotal(quotation));
            quotation.setTaxes(quotation.getTotal() - quotation.getSubtotal());
            quotationService.updateQuotation(quotation);
            redirectAttributes.addFlashAttribute("quotation", quotationService.getQuotation(quotation.getQuotationId()));
            return "redirect:/category/getAllCategories";
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/addItem")
    public void addItem(@RequestBody Quotation quotation, @RequestBody Item item, Model model){
        quotationService.addItem(quotation, item);
        model.addAttribute("quotation", quotation);
    }

    @GetMapping("/addItemById")
    public String addItemById(@ModelAttribute("quotation") Quotation quotation, @ModelAttribute("vehicle") Vehicle vehicle,
                            @ModelAttribute("categoryId") Integer categoryId, @ModelAttribute("item") Item item, Model model,
                            RedirectAttributes redirectAttributes){
        try {
            quotationService.addItem(quotation.getQuotationId(), item);
            redirectAttributes.addFlashAttribute("quotation", quotationService.getQuotation(quotation.getQuotationId()));
            redirectAttributes.addFlashAttribute("vehicle", vehicle);
            redirectAttributes.addFlashAttribute("item", item);
            redirectAttributes.addFlashAttribute("categoryId", categoryId);
            return "redirect:/quotation/getQuotationValues";
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/deleteItem")
    public void deleteItem(@RequestBody Quotation quotation, @RequestBody Item item, Model model){
        quotationService.deleteItem(quotation, item);
        model.addAttribute("quotation", quotation);
    }

    @GetMapping("/deleteItemById")
    public String deleteItemById(@ModelAttribute("quotation") Quotation quotation, @ModelAttribute("vehicle") Vehicle vehicle,
                                 @ModelAttribute("item") Item item, @ModelAttribute("categoryId") String categoryId, Model model,
                                 RedirectAttributes redirectAttributes){
        try {
            quotationService.deleteItem(quotation.getQuotationId(), item);
            redirectAttributes.addFlashAttribute("quotation", quotationService.getQuotation(quotation.getQuotationId()));
            redirectAttributes.addFlashAttribute("vehicle", vehicle);
            redirectAttributes.addFlashAttribute("item", item);
            Integer newCategoryId = Integer.parseInt(categoryId);
            redirectAttributes.addFlashAttribute("categoryId", newCategoryId);
            return "redirect:/quotation/getQuotationValues";
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/quotationFinished")
    public String quotationFinish(@ModelAttribute("quotation") Quotation quotation, @ModelAttribute("vehicle") Vehicle vehicle,
                                  @ModelAttribute("quotationItems") String items,
                                  RedirectAttributes redirectAttributes, Model model) {
        quotationService.updateStatus(quotation, "FINALIZADO");
        String[] stringItems = items.split(",");
        List<Integer> integerItems = new ArrayList<>();
        for (String item : stringItems) {
            try {
                integerItems.add(Integer.parseInt(item));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        redirectAttributes.addFlashAttribute("quotation", quotation);
        redirectAttributes.addFlashAttribute("vehicle", vehicle);
        redirectAttributes.addFlashAttribute("items", integerItems);
        return "redirect:/item/getItem/";
    }
}
