package es.netmind.banana_invoices.batch.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import es.netmind.banana_invoices.models.Recibo;

public class SimpleReader implements ItemReader<Recibo> {
    final static Logger logger = LoggerFactory.getLogger(SimpleReader.class);

    private static int count = 0;
    
    //TODO: falta implementar este reader para que lea del AWS S3, con el config dado

    @Override
    public Recibo read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
