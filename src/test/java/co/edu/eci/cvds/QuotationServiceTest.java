package co.edu.eci.cvds;

import co.edu.eci.cvds.exceptions.ModelException;
import co.edu.eci.cvds.exceptions.ServiceException;
import co.edu.eci.cvds.model.Category;
import co.edu.eci.cvds.model.Item;
import co.edu.eci.cvds.model.Quotation;
import co.edu.eci.cvds.model.QuotationStatus;
import co.edu.eci.cvds.service.CategoryService;
import co.edu.eci.cvds.service.ItemService;
import co.edu.eci.cvds.service.QuotationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*import org.junit.jupiter.api.Test;*/

@SpringBootTest
class QuotationServiceTest {

    @Autowired
    private QuotationService quotationService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldAddQuotation(){
        Quotation quotation = new Quotation();
        quotationService.addQuotation(quotation);
        assertEquals(QuotationStatus.CREADO, quotation.getStatus());
        assertEquals(1, quotationService.getAllQuotation().size());
    }

    @Test
    void shouldGetQuotationById(){
        Quotation quotation = new Quotation();
        quotationService.addQuotation(quotation);
        try{
            Quotation newQuotation = quotationService.getQuotation(quotationService.getAllQuotation().get(0).getQuotationId());
            assertEquals(quotation.getCreationDate(), newQuotation.getCreationDate());
        }
        catch(ServiceException serviceException){
            fail();
        }
    }

    @Test
    void shouldNotGetQuotationById(){
        try{
            quotationService.getQuotation(0);
            fail();
        }
        catch(ServiceException serviceException){
            assertEquals(ServiceException.nonExistentQuotation, serviceException.getMessage());
        }
    }

    @Test
    void shouldGetAllQuotations(){
        Quotation quotation1 = new Quotation();
        Quotation quotation2 = new Quotation();
        Quotation quotation3 = new Quotation();
        quotationService.addQuotation(quotation1);
        quotationService.addQuotation(quotation2);
        quotationService.addQuotation(quotation3);
        assertEquals(3, quotationService.getAllQuotation().size());
    }

    @Test
    void shouldUpdateAQuotation(){
        Quotation quotation = new Quotation();
        quotationService.addQuotation(quotation);
        try{
            quotation.setTotal(100000.0);
            quotationService.updateQuotation(quotation);
            assertEquals(100000.0, quotationService.getAllQuotation().get(0).getTotal());
        }
        catch (ServiceException serviceException){
            fail();
        }
    }

    @Test
    void shouldNotUpdateAQuotation(){
        try{
            quotationService.updateQuotation(new Quotation());
            fail();
        }
        catch (ServiceException serviceException){
            assertEquals(ServiceException.nonExistentQuotation, serviceException.getMessage());
        }
    }

    @Test
    void shouldDeleteQuotation(){
        Quotation quotation = new Quotation();
        quotationService.addQuotation(quotation);
        quotationService.deleteQuotation(quotation);
        assertEquals(0, quotationService.getAllQuotation().size());
    }

    @Test
    void shouldDeleteQuotationById(){
        Quotation quotation = new Quotation();
        quotationService.addQuotation(quotation);
        try{
            quotationService.deleteQuotation(quotationService.getAllQuotation().get(0).getQuotationId());
            assertEquals(0, quotationService.getAllQuotation().size());
        }
        catch (ServiceException serviceException){
            fail();
        }
    }

    @Test
    void shouldNotDeleteQuotationById(){
        try{
            quotationService.deleteQuotation(0);
            fail();
        }
        catch (ServiceException serviceException){
            assertEquals(ServiceException.nonExistentQuotation, serviceException.getMessage());
        }
    }

    @Test
    void shouldCalculateTotal(){
        Quotation quotation = new Quotation();
        Category category = new Category("category");
        categoryService.addCategory(category);
        try{
            Item item1 = new Item("name", "shortDescription", "image", "technical", 125000.0, 10.0, 100.0, true, 19.0, category);
            Item item2 = new Item("name", "shortDescription", "image", "technical", 200000.0, 10.0, 50.0, true, 19.0, category);
            Item item3 = new Item("name", "shortDescription", "image", "technical", 130000.0, 10.0, 10.0, true, 19.0, category);
            itemService.addItem(item1);
            itemService.addItem(item2);
            itemService.addItem(item3);
            quotation.addItem(item1);
            quotation.addItem(item2);
            quotation.addItem(item3);
            quotationService.addQuotation(quotation);
            double total = quotationService.calculateTotal(quotation);
            assertEquals(258230.0, total, 0.01);
        }
        catch (ModelException modelException){
            fail();
        }
    }

    @Test
    void shouldCalculateTotalById(){
        Quotation quotation = new Quotation();
        Category category = new Category("category");
        categoryService.addCategory(category);
        try{
            Item item1 = new Item("name", "shortDescription", "image", "technical", 125000.0, 10.0, 100.0, true, 19.0, category);
            Item item2 = new Item("name", "shortDescription", "image", "technical", 200000.0, 10.0, 50.0, true, 19.0, category);
            Item item3 = new Item("name", "shortDescription", "image", "technical", 130000.0, 10.0, 10.0, true, 19.0, category);
            itemService.addItem(item1);
            itemService.addItem(item2);
            itemService.addItem(item3);
            quotation.addItem(item1);
            quotation.addItem(item2);
            quotation.addItem(item3);
            item1.addQuotation(quotation);
            item2.addQuotation(quotation);
            item3.addQuotation(quotation);
            quotationService.addQuotation(quotation);
            double total = quotationService.calculateTotal(quotationService.getAllQuotation().get(0).getQuotationId());
            assertEquals(258230.0, total, 0.01);
        }
        catch (ModelException modelException){
            fail();
        }
        catch (ServiceException serviceException){
            fail();
        }
    }

    @Test
    void shouldNotCalculateTotalById(){
        try {
            quotationService.calculateTotal(0);
            fail();
        }
        catch (ServiceException serviceException){
            assertEquals(ServiceException.nonExistentQuotation, serviceException.getMessage());
        }
    }

    @Test
    void shouldCalculateSubtotal(){
        Quotation quotation = new Quotation();
        Category category = new Category("category");
        categoryService.addCategory(category);
        try{
            Item item1 = new Item("name", "shortDescription", "image", "technical", 125000.0, 10.0, 100.0, true, 19.0, category);
            Item item2 = new Item("name", "shortDescription", "image", "technical", 200000.0, 10.0, 50.0, true, 19.0, category);
            Item item3 = new Item("name", "shortDescription", "image", "technical", 130000.0, 10.0, 10.0, true, 19.0, category);
            itemService.addItem(item1);
            itemService.addItem(item2);
            itemService.addItem(item3);
            quotation.addItem(item1);
            quotation.addItem(item2);
            quotation.addItem(item3);
            item1.addQuotation(quotation);
            item2.addQuotation(quotation);
            item3.addQuotation(quotation);
            quotationService.addQuotation(quotation);
            double total = quotationService.calculateSubTotal(quotation);
            assertEquals(217000.0, total, 0.01);
        }
        catch (ModelException modelException){
            fail();
        }
    }

    @Test
    void shouldCalculateSubtotalById(){
        Quotation quotation = new Quotation();
        Category category = new Category("category");
        categoryService.addCategory(category);
        try{
            Item item1 = new Item("name", "shortDescription", "image", "technical", 125000.0, 10.0, 100.0, true, 19.0, category);
            Item item2 = new Item("name", "shortDescription", "image", "technical", 200000.0, 10.0, 50.0, true, 19.0, category);
            Item item3 = new Item("name", "shortDescription", "image", "technical", 130000.0, 10.0, 10.0, true, 19.0, category);
            itemService.addItem(item1);
            itemService.addItem(item2);
            itemService.addItem(item3);
            quotation.addItem(item1);
            quotation.addItem(item2);
            quotation.addItem(item3);
            quotationService.addQuotation(quotation);
            double total = quotationService.calculateSubTotal(quotationService.getAllQuotation().get(0).getQuotationId());
            assertEquals(217000.0, total, 0.01);
        }
        catch (ModelException modelException){
            fail();
        }
        catch(ServiceException serviceException){
            fail();
        }
    }

    @Test
    void shouldNotCalculateSubtotalById(){
        try {
            double total = quotationService.calculateSubTotal(0);
            fail();
        }
        catch (ServiceException serviceException){
            assertEquals(ServiceException.nonExistentQuotation, serviceException.getMessage());
        }
    }

    @Test
    void shouldUpdateStatus(){
        Quotation quotation = new Quotation();
        quotationService.addQuotation(quotation);
        quotationService.updateStatus(quotation, "EN_PROCESO");
        assertEquals(QuotationStatus.EN_PROCESO, quotationService.getAllQuotation().get(0).getStatus());
    }

    @Test
    void shouldUpdateStatusById(){
        Quotation quotation = new Quotation();
        quotationService.addQuotation(quotation);
        try {
            quotationService.updateStatus(quotationService.getAllQuotation().get(0).getQuotationId(), "EN_PROCESO");
            assertEquals(QuotationStatus.EN_PROCESO, quotationService.getAllQuotation().get(0).getStatus());
        }
        catch (ServiceException serviceException){
            fail();
        }
    }

    @Test
    void shouldNotUpdateStatusById(){
        try {
            quotationService.updateStatus(0, "EN_PROCESO");
            fail();
        }
        catch (ServiceException serviceException){
            assertEquals(ServiceException.nonExistentQuotation, serviceException.getMessage());
        }
    }

    @Test
    void shouldAddItem(){
        Category category = new Category("category");
        categoryService.addCategory(category);
        try{
            Item item = new Item("name", "shortDescription", "image", "technical", 125000.0, 10.0, 100.0, true, 19.0, category);
            itemService.addItem(item);
            Quotation quotation = new Quotation();
            quotationService.addItem(quotation, item);
            item.addQuotation(quotation);
            quotationService.addQuotation(quotation);
            assertEquals(1, quotationService.getAllQuotation().get(0).getItems().size());
        }
        catch (ModelException modelException){
            fail();
        }
    }

    @Test
    void shouldAddItemById(){
        Category category = new Category("category");
        categoryService.addCategory(category);
        try{
            Item item = new Item("name", "shortDescription", "image", "technical", 125000.0, 10.0, 100.0, true, 19.0, category);
            itemService.addItem(item);
            Quotation quotation = new Quotation();
            item.addQuotation(quotation);
            quotationService.addQuotation(quotation);
            quotationService.addItem(quotationService.getAllQuotation().get(0).getQuotationId(), item);
            assertEquals(1, quotationService.getAllQuotation().get(0).getItems().size());
        }
        catch (ModelException modelException){
            fail();
        }
        catch(ServiceException serviceException){
            fail();
        }
    }

    @Test
    void shouldNotAddItemById(){
        Category category = new Category("category");
        categoryService.addCategory(category);
        try{
            Item item = new Item("name", "shortDescription", "image", "technical", 125000.0, 10.0, 100.0, true, 19.0, category);
            itemService.addItem(item);
            quotationService.addItem(0, item);
            fail();
        }
        catch (ModelException modelException){
            fail();
        }
        catch(ServiceException serviceException){
            assertEquals(ServiceException.nonExistentQuotation, serviceException.getMessage());
        }
    }

    @Test
    void shouldDeleteItem(){
        Category category = new Category("category");
        categoryService.addCategory(category);
        try{
            Item item = new Item("name", "shortDescription", "image", "technical", 125000.0, 10.0, 100.0, true, 19.0, category);
            itemService.addItem(item);
            Quotation quotation = new Quotation();
            item.addQuotation(quotation);
            quotationService.addItem(quotation, item);
            quotationService.addQuotation(quotation);
            quotationService.deleteItem(quotation, item);
            assertEquals(0, quotationService.getAllQuotation().get(0).getItems().size());
        }
        catch (ModelException modelException){
            fail();
        }
    }

    @Test
    void shouldDeleteItemById(){
        Category category = new Category("category");
        categoryService.addCategory(category);
        try{
            Item item = new Item("name", "shortDescription", "image", "technical", 125000.0, 10.0, 100.0, true, 19.0, category);
            itemService.addItem(item);
            Quotation quotation = new Quotation();
            item.addQuotation(quotation);
            quotationService.addItem(quotation, item);
            quotationService.addQuotation(quotation);
            quotationService.deleteItem(quotationService.getAllQuotation().get(0).getQuotationId(), item);
            assertEquals(0, quotationService.getAllQuotation().get(0).getItems().size());
        }
        catch (ModelException modelException){
            fail();
        }
        catch(ServiceException serviceException){
            fail();
        }
    }

    @Test
    void shouldNotDeleteItemById(){
        Category category = new Category("category");
        categoryService.addCategory(category);
        try{
            Item item = new Item("name", "shortDescription", "image", "technical", 125000.0, 10.0, 100.0, true, 19.0, category);
            itemService.addItem(item);
            quotationService.addItem(0, item);
            quotationService.deleteItem(0, item);
            fail();
        }
        catch (ModelException modelException){
            fail();
        }
        catch(ServiceException serviceException){
            assertEquals(ServiceException.nonExistentQuotation, serviceException.getMessage());
        }
    }

    @AfterEach
    public void deleteValues(){
        List<Quotation> quotationList = quotationService.getAllQuotation();
        for(Quotation quotation: quotationList){
            quotationService.deleteQuotation(quotation);
        }
    }
}
