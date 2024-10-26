package co.edu.eci.cvds;

import co.edu.eci.cvds.model.*;
import co.edu.eci.cvds.repository.CategoryRepository;
import co.edu.eci.cvds.repository.ItemRepository;
import co.edu.eci.cvds.repository.QuotationRepository;
import co.edu.eci.cvds.repository.UserRepository;
import co.edu.eci.cvds.service.ConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Slf4j
public class SpringApplicationCvds {
	private final ConfigurationService configurationService;
	private final UserRepository userRepository;
	private final QuotationRepository quotationRepository;
	private final ItemRepository itemRepository;
	private final CategoryRepository categoryRepository;

	@Autowired
	public SpringApplicationCvds(UserRepository userRepository, QuotationRepository quotationRepository, ItemRepository itemRepository, CategoryRepository categoryRepository, ConfigurationService configurationService){
        this.configurationService = configurationService;
		this.userRepository = userRepository;
		this.quotationRepository = quotationRepository;
		this.itemRepository = itemRepository;
		this.categoryRepository = categoryRepository;
    }


	public static void main(String[] args) {
		SpringApplication.run(SpringApplicationCvds.class, args);
	}

	@Bean
	public CommandLineRunner run(){
		User user = new User("Juan", 123, 123456789, "juan@mail.com", "juan");
		userRepository.save(user);
		return (args) -> {
		};
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}


}
