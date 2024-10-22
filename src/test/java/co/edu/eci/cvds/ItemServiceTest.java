package co.edu.eci.cvds;

import co.edu.eci.cvds.exceptions.ModelException;
import co.edu.eci.cvds.exceptions.ServiceException;
import co.edu.eci.cvds.model.Category;
import co.edu.eci.cvds.model.Item;
import co.edu.eci.cvds.service.CategoryService;
import co.edu.eci.cvds.service.ItemService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*import org.junit.jupiter.api.Test;*/

@SpringBootTest
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    private Category category;

    @BeforeEach
    void initializeValues(){
        category = new Category("category");
        categoryService.addCategory(category);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void shouldAddItem() {
        try {
            Item item = new Item("name", "shortDescription", "image", "technical", 10.0, 10.0, 10.0, true, 10.0, category);
            itemService.addItem(item);
            assertEquals(1, itemService.getAllItems().size());
        }
        catch(ModelException modelException){
            fail();
        }
    }

    @Test
    void shouldGetItemById(){
        try {
            Item item = new Item("name", "shortDescription", "image", "technical", 10.0, 10.0, 10.0, true, 10.0, category);
            itemService.addItem(item);
            Item newItem = itemService.getItem(itemService.getAllItems().get(0).getItemId());
            assertEquals("name", newItem.getName());
        }
        catch(ModelException modelException){
            fail();
        }
        catch (ServiceException serviceException){
            fail();
        }
    }

    @Test
    void shouldNotGetItemById(){
        try{
            itemService.getItem(1);
            fail();
        }
        catch (ServiceException serviceException){
            assertEquals(ServiceException.nonExistentItem, serviceException.getMessage());
        }
    }

    @Test
    void shouldGetAllItems() {
        try {
            Item item = new Item("name", "shortDescription", "image", "technical", 10.0, 10.0, 10.0, true, 10.0, category);
            Item item2 = new Item("name1", "shortDescription", "image", "technical", 10.0, 10.0, 10.0, true, 10.0, category);
            Item item3 = new Item("name2", "shortDescription", "image", "technical", 10.0, 10.0, 10.0, true, 10.0, category);
            itemService.addItem(item);
            itemService.addItem(item2);
            itemService.addItem(item3);
            assertEquals(3, itemService.getAllItems().size());
        }
        catch(ModelException modelException){
            fail();
        }
    }

    @Test
    void shouldUpdateAnItem(){
        try {
            Item item = new Item("name", "shortDescription", "image", "technical", 10.0, 10.0, 10.0, true, 10.0, category);
            itemService.addItem(item);
            item.setDiscount(100.0);
            itemService.updateItem(item);
            Item newItem = itemService.getItem(itemService.getAllItems().get(0).getItemId());
            assertEquals(100.0, newItem.getDiscount());
        }
        catch(ModelException modelException){
            fail();
        }
        catch (ServiceException serviceException){
            fail();
        }
    }

    @Test
    void shouldNotUpdateAnItem(){
        try {
            Item item = new Item("name", "shortDescription", "image", "technical", 10.0, 10.0, 10.0, true, 10.0, category);
            itemService.updateItem(item);
            fail();
        }
        catch(ModelException modelException){
            fail();
        }
        catch (ServiceException serviceException){
            assertEquals(ServiceException.nonExistentItem, serviceException.getMessage());
        }
        catch (NullPointerException e){
            assertTrue(true);
        }
    }

    @Test
    void shouldDeleteItem() {
        try {
            Item item = new Item("name", "shortDescription", "image", "technical", 10.0, 10.0, 10.0, true, 10.0, category);
            itemService.addItem(item);
            itemService.deleteItem(item);
            assertEquals(0, itemService.getAllItems().size());
        } catch (ModelException modelException) {
            fail();
        }
    }

    @Test
    void shouldDeleteItemById() {
        try {
            Item item = new Item("name", "shortDescription", "image", "technical", 10.0, 10.0, 10.0, true, 10.0, category);
            itemService.addItem(item);
            itemService.deleteItem(itemService.getAllItems().get(0).getItemId());
            assertEquals(0, itemService.getAllItems().size());
        }
        catch(ModelException modelException){
            fail();
        }
        catch(ServiceException serviceException){
            fail();
        }
    }

    @Test
    void shouldNotDeleteItemById() {
        try {
            itemService.deleteItem(1);
            fail();
        }
        catch(ServiceException serviceException){
            assertEquals(ServiceException.nonExistentItem, serviceException.getMessage());
        }
    }

    @Test
    void shouldCalculateSubtotal(){
        try {
            Item item = new Item("name", "shortDescription", "image", "technical", 100000.0, 10.0, 10.0, true, 10.0, category);
            itemService.addItem(item);
            double subtotal = itemService.calculateSubtotal(item);
            assertEquals(90000.0, subtotal, 0.01);
        }
        catch(ModelException modelException){
            fail();
        }
    }

    @Test
    void shouldCalculateSubtotalById(){
        try {
            Item item = new Item("name", "shortDescription", "image", "technical", 100000.0, 10.0, 10.0, true, 10.0, category);
            itemService.addItem(item);
            double subtotal = itemService.calculateSubtotal(itemService.getAllItems().get(0).getItemId());
            assertEquals(90000.0, subtotal, 0.01);
        }
        catch(ModelException modelException){
            fail();
        }
        catch(ServiceException serviceException){
            fail();
        }
    }

    @Test
    void shouldNotCalculateSubtotalById(){
        try {
            itemService.calculateSubtotal(1);
            fail();
        }
        catch(ServiceException serviceException){
            assertEquals(ServiceException.nonExistentItem, serviceException.getMessage());
        }
    }

    @Test
    void shouldCalculateTotal(){
        try {
            Item item = new Item("name", "shortDescription", "image", "technical", 100000.0, 10.0, 10.0, true, 19.0, category);
            itemService.addItem(item);
            double total = itemService.calculateTotal(item);
            assertEquals(107100.0, total, 0.01);
        }
        catch(ModelException modelException){
            fail();
        }
    }

    @Test
    void shouldBeAvailableById(){
        try {
            Item item = new Item("name", "shortDescription", "image", "technical", 100000.0, 10.0, 10.0, true, 19.0, category);
            itemService.addItem(item);
            boolean availability = itemService.isAvailable(itemService.getAllItems().get(0).getItemId());
            assertTrue(availability);
        }
        catch(ModelException modelException){
            fail();
        }
        catch(ServiceException serviceException){
            fail();
        }
    }

    @Test
    void shouldNotBeAvailableById(){
        try {
            itemService.isAvailable(1);
            fail();
        }
        catch(ServiceException serviceException){
            assertEquals(ServiceException.nonExistentItem, serviceException.getMessage());
        }
    }

    @Test
    void shouldAddCategory(){
        Category newCategory = new Category("newCategory");
        categoryService.addCategory(newCategory);
        try {
            Item item = new Item("name", "shortDescription", "image", "technical", 100000.0, 10.0, 10.0, true, 19.0, category);
            itemService.addItem(item);
            itemService.addCategory(item, newCategory);
            assertEquals("newCategory", itemService.getItem(itemService.getAllItems().get(0).getItemId()).getCategory().getName());
        }
        catch(ModelException modelException){
            fail();
        }
        catch(ServiceException serviceException){
            fail();
        }
    }

    @Test
    void shouldAddCategoryById(){
        Category newCategory = new Category("newCategory");
        categoryService.addCategory(newCategory);
        try {
            Item item = new Item("name", "shortDescription", "image", "technical", 100000.0, 10.0, 10.0, true, 19.0, category);
            itemService.addItem(item);
            itemService.addCategory(itemService.getAllItems().get(0).getItemId(), newCategory);
            assertEquals("newCategory", itemService.getItem(itemService.getAllItems().get(0).getItemId()).getCategory().getName());
        }
        catch(ModelException modelException){
            fail();
        }
        catch(ServiceException serviceException){
            fail();
        }
    }

    @Test
    void shouldNotAddCategoryById(){
        try {
            itemService.addCategory(1, category);
            fail();
        }
        catch(ServiceException serviceException){
            assertEquals(ServiceException.nonExistentItem, serviceException.getMessage());
        }
    }

    @AfterEach
    public void deleteValues(){
        List<Item> itemList = itemService.getAllItems();
        List<Category> categoryList = categoryService.getAllCategories();
        for(Item item: itemList){
            itemService.deleteItem(item);
        }
        for(Category category: categoryList){
            categoryService.deleteCategory(category);
        }
    }

}
