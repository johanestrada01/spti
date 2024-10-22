package co.edu.eci.cvds.service;

import co.edu.eci.cvds.exceptions.ServiceException;
import co.edu.eci.cvds.model.Category;
import co.edu.eci.cvds.model.Item;
import co.edu.eci.cvds.model.Vehicle;
import co.edu.eci.cvds.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public void addItem(Item item){
        itemRepository.save(item);
    }

    public Item getItem(int id) throws ServiceException{
        Optional<Item> result = itemRepository.findById(id);
        if(result.isEmpty()){
            throw new ServiceException(ServiceException.nonExistentItem);
        }
        return result.get();
    }

    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public void updateItem(Item item) throws ServiceException, NullPointerException {
        Item update = getItem(item.getItemId());
        update.setName(item.getName());
        update.setShortDescription(item.getShortDescription());
        update.setTechnicalDescription(item.getTechnicalDescription());
        update.setImage(item.getImage());
        update.setValue(item.getValue());
        update.setCurrency(item.getCurrency());
        update.setDiscount(item.getDiscount());
        update.setAvailability(item.getAvailability());
        update.setTax(item.getTax());
        update.setCategory(item.getCategory());
        update.setQuotations(item.getQuotations());
        update.setVehicles(item.getVehicles());
        itemRepository.save(update);
    }

    public void deleteItem(Item item){
        itemRepository.delete(item);
    }

    public void deleteItem(int id) throws ServiceException {
        Item item = getItem(id);
        itemRepository.delete(item);
    }

    public double calculateSubtotal(Item item){
        double value = item.getValue();
        double discount = value * (item.getDiscount() / 100);
        return value - discount;
    }

    public double calculateSubtotal(int id) throws ServiceException {
        Item item = getItem(id);
        double value = item.getValue();
        double discount = value * (item.getDiscount() / 100);
        return value - discount;
    }

    public double calculateTotal(Item item){
        double value = item.getValue();
        double discount = value * (item.getDiscount() / 100);
        double valueWithDiscount = value - discount;
        double tax = valueWithDiscount * (item.getTax() / 100);
        return valueWithDiscount + tax;
    }

    public double calculateTotal(int id) throws ServiceException {
        Item item = getItem(id);
        return calculateTotal(item);
    }

    public boolean isAvailable(Item item){
        return item.getAvailability();
    }

    public boolean isAvailable(int id) throws ServiceException {
        Item item = getItem(id);
        return item.getAvailability();
    }

    public void addCategory(Item item, Category category){
        item.addCategory(category);
        itemRepository.save(item);
    }

    public void addCategory(int id, Category category) throws ServiceException {
        Item item = getItem(id);
        item.addCategory(category);
        itemRepository.save(item);
    }

    public void addVehicle(Item item, Vehicle vehicle){
        item.addVehicle(vehicle);
        try {
            updateItem(item);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Item> getItemsByVehicleIdAndCategoryId(Integer categoryId, Integer vehicleId){
        return itemRepository.findItemsByVehicleIdAndCategoryId(categoryId, vehicleId);
    }

}
