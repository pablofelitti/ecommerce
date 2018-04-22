package com.ecommerce.job;

import com.ecommerce.entity.CartStatus;
import com.ecommerce.repository.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * Retrieves the carts to start processing them in the job. Each thread will execute read() method
 * to get one of the carts and remove it from the list so that another thread does not process the
 * same cart. To achieve this, read() method is synchronized
 */
@StepScope
@Component
public class AttemptReader implements ItemReader<Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttemptReader.class);

    private final CartRepository cartRepository;
    private Iterable<Long> cartsIdToProcess;

    @Autowired
    AttemptReader(final CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public synchronized Long read() {

        if (cartsIdToProcess == null) {
            LOGGER.info("Retrieving carts to process");
            cartsIdToProcess = cartRepository.findIdByStatusReadyOrderByCreationDateAsc();
        } else {
            LOGGER.info("Carts have been already loaded");
        }

        Iterator<Long> iterator = cartsIdToProcess.iterator();
        if (iterator.hasNext()) {
            Long cartIdToProcess = iterator.next();
            iterator.remove();
            LOGGER.info("Removed from list cart id {}", cartIdToProcess);
            return cartIdToProcess;
        } else {
            LOGGER.info("No more carts to process");
            return null;
        }
    }
}
