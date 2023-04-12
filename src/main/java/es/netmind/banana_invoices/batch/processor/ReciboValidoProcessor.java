package es.netmind.banana_invoices.batch.processor;

import es.netmind.banana_invoices.models.PaidStatus;
import es.netmind.banana_invoices.models.Recibo;
import es.netmind.banana_invoices.models.ReciboInvalido;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

public class ReciboValidoProcessor implements ItemProcessor<Recibo, Object> {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    //	Esto es lo mismo que ReciboPagadoProcessor, usar ese en vez de este
    
    @Override
    public Object process(Recibo recibo) {
    	
    	RestTemplate restTemplate = new RestTemplate();
        
        String url = "http://localhost:9100/api/v1/invoices/{id}";
        ResponseEntity<PaidStatus> response = restTemplate.getForEntity(url, PaidStatus.class, recibo.getId());
    	if(response.getStatusCode() == HttpStatus.OK) {
    		if(response.getBody().getPaid()) {
    			return recibo;
    		}else {
    			return new ReciboInvalido(recibo, "dwa");
    		}
    	}else {
    		// Ha petado la petición
    		return null;
    	}
        
         
    }

}
