package co.edu.eci.cvds.controller;

import co.edu.eci.cvds.exceptions.ControllerException;
import co.edu.eci.cvds.exceptions.ServiceException;
import co.edu.eci.cvds.model.*;
import co.edu.eci.cvds.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/quotation")
public class QuotationController {

    private String quotationString = "quotation";
    private String vehicleString = "vehicle";
    private String categoryString = "categoryId";
    private String redirectRoute = "redirect:/category/getAllCategories";
    QuotationService quotationService;
    private static final String BAD_REQUEST = "Bad Request";

    @Autowired
    public QuotationController(QuotationService quotationService){
        this.quotationService = quotationService;
    }

    @GetMapping("/addQuotation")
    public String addQuotation(@ModelAttribute(vehicleString) Vehicle vehicle, @ModelAttribute(categoryString) Integer categoryId,
                               Model model, RedirectAttributes redirectAttributes){
        Quotation quotation = new Quotation();
        quotationService.addQuotation(quotation);
        redirectAttributes.addFlashAttribute(quotationString, quotation);
        redirectAttributes.addFlashAttribute(vehicleString, vehicle);
        redirectAttributes.addFlashAttribute(categoryString, categoryId);
        return redirectRoute;
    }

    @GetMapping("/getQuotation")
    public String getQuotation(@ModelAttribute(vehicleString) Vehicle vehicle, @ModelAttribute(categoryString) Integer categoryId,
                             @ModelAttribute(quotationString) Quotation quotation, @ModelAttribute("categories") List<Category> categories,
                             Model model, RedirectAttributes redirectAttributes) throws ControllerException {
        try {
            redirectAttributes.addFlashAttribute(vehicleString, vehicle);
            redirectAttributes.addFlashAttribute(categoryString, categoryId);
            redirectAttributes.addFlashAttribute("categories", categories);
            redirectAttributes.addFlashAttribute(quotationString, quotationService.getQuotation(quotation.getQuotationId()));
            return "redirect:/item/";
        } catch (ServiceException e) {
            throw new ControllerException(BAD_REQUEST);
        }
    }

    @GetMapping("/getAllQuotations")
    public void getAllQuotations(Model model){
        model.addAttribute("quotations", quotationService.getAllQuotation());
    }

    @PostMapping("/updateQuotation")
    public void updateQuotation(@RequestBody Quotation quotation, Model model) throws ControllerException {
        try {
            quotationService.updateQuotation(quotation);
            model.addAttribute(quotationString, quotation);
        } catch (ServiceException e) {
            throw new ControllerException(BAD_REQUEST);
        }
    }

    @PostMapping("/deleteQuotation")
    public void deleteQuotation(@RequestBody Quotation quotation){
        quotationService.deleteQuotation(quotation);
    }

    @PostMapping("/deleteQuotationById/{id}")
    public void deleteQuotation(@PathVariable int id) throws ControllerException {
        try {
            quotationService.deleteQuotation(id);
        } catch (ServiceException e) {
            throw new ControllerException(BAD_REQUEST);
        }
    }

    @GetMapping("/getTotal")
    public void calculateTotal(Quotation quotation, Model model){
        model.addAttribute("total", quotationService.calculateTotal(quotation));
    }

    @GetMapping("/getTotalById")
    public String calculateTotalById(@ModelAttribute(quotationString) Quotation quotation, @ModelAttribute(vehicleString) Vehicle vehicle,
                                   @ModelAttribute(categoryString) Integer categoryId, @ModelAttribute("item") Item item, Model model,
                                   RedirectAttributes redirectAttributes) throws ControllerException {
        try {
            redirectAttributes.addFlashAttribute(vehicleString, vehicle);
            redirectAttributes.addFlashAttribute("item", item);
            redirectAttributes.addFlashAttribute(categoryString, categoryId);
            quotation.setTotal(quotationService.calculateTotal(quotation));
            quotationService.updateQuotation(quotation);
            redirectAttributes.addFlashAttribute(quotationString, quotationService.getQuotation(quotation.getQuotationId()));
            return redirectRoute;
        } catch (ServiceException e) {
            throw new ControllerException(BAD_REQUEST);
        }
    }

    @GetMapping("/getSubTotal")
    public void calculateSubTotal(@RequestBody Quotation quotation, Model model){
        model.addAttribute("subTotal", quotationService.calculateSubTotal(quotation));
    }

    @GetMapping("/getSubTotalById")
    public String calculateSubTotalById(@ModelAttribute(quotationString) Quotation quotation, @ModelAttribute(vehicleString) Vehicle vehicle,
                                        @ModelAttribute(categoryString) Integer categoryId, @ModelAttribute("item") Item item, Model model,
                                      RedirectAttributes redirectAttributes) throws ControllerException {
        try {
            redirectAttributes.addFlashAttribute(vehicleString, vehicle);
            redirectAttributes.addFlashAttribute("item", item);
            redirectAttributes.addFlashAttribute(categoryString, categoryId);
            quotation.setSubtotal(quotationService.calculateSubTotal(quotation));
            quotationService.updateQuotation(quotation);
            redirectAttributes.addFlashAttribute(quotationString, quotationService.getQuotation(quotation.getQuotationId()));
            return "redirect:/quotation/getTotalById";
        } catch (ServiceException e) {
            throw new ControllerException(BAD_REQUEST);
        }
    }

    @PostMapping("/updateStatus")
    public void updateStatus(@RequestBody Quotation quotation, @RequestBody String status, Model model){
        quotationService.updateStatus(quotation, status);
        model.addAttribute(quotationString, quotation);
    }

    @GetMapping("/updateStatusById")
    public String updateStatusById(@ModelAttribute(quotationString) Quotation quotation, @ModelAttribute(vehicleString) Vehicle vehicle,
                                   @ModelAttribute("item") Item item, @ModelAttribute(categoryString) String categoryId,
                                   Model model, RedirectAttributes redirectAttributes) throws ControllerException {
        try {
            quotationService.updateStatus(quotation.getQuotationId(), "EN_PROCESO");
            redirectAttributes.addFlashAttribute(quotationString, quotationService.getQuotation(quotation.getQuotationId()));
            redirectAttributes.addFlashAttribute(vehicleString, vehicle);
            redirectAttributes.addFlashAttribute("item", item);
            Integer newCategoryId = Integer.parseInt(categoryId);
            redirectAttributes.addFlashAttribute(categoryString, newCategoryId);
            return "redirect:/quotation/addItemById";
        } catch (ServiceException e) {
            throw new ControllerException(BAD_REQUEST);
        }
    }

    @GetMapping("/getQuotationValues")
    public String getQuotationValues(@ModelAttribute(quotationString) Quotation quotation, @ModelAttribute(vehicleString) Vehicle vehicle,
                                     @ModelAttribute("item") Item item, @ModelAttribute(categoryString) String categoryId,
                                     Model model, RedirectAttributes redirectAttributes) throws ControllerException {
        try {
            redirectAttributes.addFlashAttribute(vehicleString, vehicle);
            redirectAttributes.addFlashAttribute("item", item);
            redirectAttributes.addFlashAttribute(categoryString, categoryId);
            quotation.setSubtotal(quotationService.calculateSubTotal(quotation));
            quotation.setTotal(quotationService.calculateTotal(quotation));
            quotation.setTaxes(quotation.getTotal() - quotation.getSubtotal());
            quotationService.updateQuotation(quotation);
            redirectAttributes.addFlashAttribute(quotationString, quotationService.getQuotation(quotation.getQuotationId()));
            return redirectRoute;
        } catch (ServiceException e) {
            throw new ControllerException(BAD_REQUEST);
        }
    }

    @PostMapping("/addItem")
    public void addItem(@RequestBody Quotation quotation, @RequestBody Item item, Model model){
        quotationService.addItem(quotation, item);
        model.addAttribute(quotationString, quotation);
    }

    @GetMapping("/addItemById")
    public String addItemById(@ModelAttribute(quotationString) Quotation quotation, @ModelAttribute(vehicleString) Vehicle vehicle,
                            @ModelAttribute(categoryString) Integer categoryId, @ModelAttribute("item") Item item, Model model,
                            RedirectAttributes redirectAttributes) throws ControllerException {
        try {
            quotationService.addItem(quotation.getQuotationId(), item);
            redirectAttributes.addFlashAttribute(quotationString, quotationService.getQuotation(quotation.getQuotationId()));
            redirectAttributes.addFlashAttribute(vehicleString, vehicle);
            redirectAttributes.addFlashAttribute("item", item);
            redirectAttributes.addFlashAttribute(categoryString, categoryId);
            return "redirect:/quotation/getQuotationValues";
        } catch (ServiceException e) {
            throw new ControllerException(BAD_REQUEST);
        }
    }

    @PostMapping("/deleteItem")
    public void deleteItem(@RequestBody Quotation quotation, @RequestBody Item item, Model model){
        quotationService.deleteItem(quotation, item);
        model.addAttribute(quotationString, quotation);
    }

    @GetMapping("/deleteItemById")
    public String deleteItemById(@ModelAttribute(quotationString) Quotation quotation, @ModelAttribute(vehicleString) Vehicle vehicle,
                                 @ModelAttribute("item") Item item, @ModelAttribute(categoryString) String categoryId, Model model,
                                 RedirectAttributes redirectAttributes) throws ControllerException {
        try {
            quotationService.deleteItem(quotation.getQuotationId(), item);
            redirectAttributes.addFlashAttribute(quotationString, quotationService.getQuotation(quotation.getQuotationId()));
            redirectAttributes.addFlashAttribute(vehicleString, vehicle);
            redirectAttributes.addFlashAttribute("item", item);
            Integer newCategoryId = Integer.parseInt(categoryId);
            redirectAttributes.addFlashAttribute(categoryString, newCategoryId);
            return "redirect:/quotation/getQuotationValues";
        } catch (ServiceException e) {
            throw new ControllerException(BAD_REQUEST);
        }
    }

    @GetMapping("/quotationFinished")
    public String quotationFinish(@ModelAttribute(quotationString) Quotation quotation, @ModelAttribute(vehicleString) Vehicle vehicle,
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
        redirectAttributes.addFlashAttribute(quotationString, quotation);
        redirectAttributes.addFlashAttribute(vehicleString, vehicle);
        redirectAttributes.addFlashAttribute("items", integerItems);
        return "redirect:/item/getItem/";
    }
}
