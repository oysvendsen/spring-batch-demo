package net.svendsen.oyvind.educational.springbatchdemo.components;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("simpleWriter")
public class SimpleWriter implements ItemWriter<String> {

    @Override public void write(List<? extends String> items) throws Exception {
        System.out.println(items);
    }
}
