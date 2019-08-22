package net.svendsen.oyvind.educational.springbatchdemo.components;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("simpleProcessor")
public class SimpleProcessor implements ItemProcessor<String,String> {

    @Override
    public String process(String item) throws Exception {
        return item;
    }
}
