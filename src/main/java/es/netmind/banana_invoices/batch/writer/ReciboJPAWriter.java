package es.netmind.banana_invoices.batch.writer;

import es.netmind.banana_invoices.models.PaidStatus;
import es.netmind.banana_invoices.models.Recibo;
import es.netmind.banana_invoices.models.ReciboInvalido;
import es.netmind.banana_invoices.persistence.IReciboRepo;
import es.netmind.banana_invoices.persistence.ReciboInvalidoRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Setter @Getter
@Transactional
public class ReciboJPAWriter implements ItemWriter<Object> {
	
	@Autowired
    private IReciboRepo reciboRepo;
    
    @Autowired
    private ReciboInvalidoRepository invalidoRepository;

    @Override
    public void write(List<? extends Object> list) throws Exception {
        System.out.println("ReciboJPAWriter write()....:" + list.size());
        
        List<Recibo> listaMapeada = (List<Recibo>) list;
        RestTemplate restTemplate = new RestTemplate();
                
        String url = "http://localhost:9100/api/v1/invoices/{id}";
        
        
        for(Recibo item : listaMapeada){
        	ResponseEntity<PaidStatus> response = restTemplate.getForEntity(url, PaidStatus.class, item.getId());
        	if(response.getStatusCode() == HttpStatus.OK) {
        		if(response.getBody().getPaid()) {
        			reciboRepo.save(item);
        		}else {
        			invalidoRepository.save(new ReciboInvalido(item, "No ha pagado"));
        		}
        	}else {
        		// Ha petado la petición
        	}
        }
    }
}
