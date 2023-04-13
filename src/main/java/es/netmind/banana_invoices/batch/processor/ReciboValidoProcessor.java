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
        // TODO: HERE GET PAID STATUS AND PROCESS RECIBO - RETURN RECIBO OR RECIBO_INVALIDO
    	logger.info("Recibo válido processor!!");
        return null;
    }

}
