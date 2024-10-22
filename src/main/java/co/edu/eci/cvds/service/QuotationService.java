package co.edu.eci.cvds.service;

import co.edu.eci.cvds.exceptions.ServiceException;
import co.edu.eci.cvds.model.Item;
import co.edu.eci.cvds.model.Quotation;
import co.edu.eci.cvds.model.QuotationStatus;
import co.edu.eci.cvds.repository.QuotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class        QuotationService {

    private final QuotationRepository quotationRepository;

    @Autowired
    public QuotationService(QuotationRepository quotationRepository){
        this.quotationRepository = quotationRepository;
    }

    public void addQuotation(Quotation quotation){
        quotationRepository.save(quotation);
    }

    public Quotation getQuotation(int id) throws ServiceException{
        Optional<Quotation> result = quotationRepository.findById(id);
        if(result.isEmpty()){
            throw new ServiceException(ServiceException.nonExistentQuotation);
        }
        return result.get();
    }

    public List<Quotation> getAllQuotation(){
        return quotationRepository.findAll();
    }

    public void updateQuotation(Quotation quotation) throws ServiceException {
        Quotation update = getQuotation(quotation.getQuotationId());
        update.setCreationDate(quotation.getCreationDate());
        update.updateStatus(quotation.getStatus());
        update.setTotal(quotation.getTotal());
        update.setSubtotal(quotation.getSubtotal());
        update.setTaxes(quotation.getTaxes());
        update.setItems(quotation.getItems());
        quotationRepository.save(update);
    }

    public void deleteQuotation(Quotation quotation){
        quotationRepository.delete(quotation);
    }

    public void deleteQuotation(int id) throws ServiceException {
        Quotation quotation = getQuotation(id);
        quotationRepository.delete(quotation);
    }

    public double calculateTotal(Quotation quotation){
        List<Item> items = quotation.getItems();
        double total = 0;
        for(Item item : items){
            double value = item.getValue();
            double discount = value * (item.getDiscount() / 100);
            double valueWithDiscount = value - discount;
            double tax = valueWithDiscount * (item.getTax() / 100);
            total += valueWithDiscount + tax;
        }
        return total;
    }

    public double calculateTotal(int id) throws ServiceException {
        Quotation quotation = getQuotation(id);
        return calculateTotal(quotation);
    }

    public double calculateSubTotal(Quotation quotation){
        List<Item> items = quotation.getItems();
        double total=0;
        for(Item i: items){
            double value = i.getValue();
            double discount = value * (i.getDiscount() / 100);
            total += value - discount;
        }
        return total;
    }

    public double calculateSubTotal(int id) throws ServiceException {
        Quotation quotation = getQuotation(id);
        return calculateSubTotal(quotation);
    }

    public void updateStatus(Quotation quotation, String status){
        if(status.equals("CREADO")) quotation.updateStatus(QuotationStatus.CREADO);
        else if(status.equals("EN_PROCESO")) quotation.updateStatus(QuotationStatus.EN_PROCESO);
        else if(status.equals("FINALIZADO")) quotation.updateStatus(QuotationStatus.FINALIZADO);
        else if(status.equals("ELIMINADO")) quotation.updateStatus(QuotationStatus.ELIMINADO);
        quotationRepository.save(quotation);
    }

    public void updateStatus(int id, String status) throws ServiceException {
        Quotation quotation = getQuotation(id);
        if(status.equals("CREADO")) quotation.updateStatus(QuotationStatus.CREADO);
        else if(status.equals("EN_PROCESO")) quotation.updateStatus(QuotationStatus.EN_PROCESO);
        else if(status.equals("FINALIZADO")) quotation.updateStatus(QuotationStatus.FINALIZADO);
        else if(status.equals("ELIMINADO")) quotation.updateStatus(QuotationStatus.ELIMINADO);
        quotationRepository.save(quotation);
    }

    public void addItem(Quotation quotation, Item item){
        quotation.addItem(item);
        quotationRepository.save(quotation);
    }

    public void addItem(int id, Item item) throws ServiceException {
        Quotation quotation = getQuotation(id);
        quotation.addItem(item);
        quotationRepository.save(quotation);
    }

    public void deleteItem(Quotation quotation, Item item){
        if(quotation != null){
            List<Item> items = quotation.getItems();
            for(int i = 0; i < items.size(); i++) {  //Implementar metodo que retorne un unico item
                if (items.get(i).getItemId() == item.getItemId()) {
                    quotation.deleteItem(i);
                    break; //evitar eliminar todos los elementos agregados de ese item
                }
            }
            quotationRepository.save(quotation);
        }
    }

    public void deleteItem(int id, Item item) throws ServiceException {
        Quotation quotation = getQuotation(id);
        deleteItem(quotation, item);
    }
    
}
