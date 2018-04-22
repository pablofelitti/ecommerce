package com.ecommerce.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.listener.ChunkListenerSupport;
import org.springframework.batch.core.scope.context.ChunkContext;

public class ChunkExecutionListener extends ChunkListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChunkExecutionListener.class);

    @Override
    public void afterChunk(ChunkContext context) {
        LOGGER.info("After chunk");
        super.afterChunk(context);
    }

    @Override
    public void beforeChunk(ChunkContext context) {
        context.attributeNames();
        LOGGER.info("Before chunk");
        super.beforeChunk(context);
    }
}
