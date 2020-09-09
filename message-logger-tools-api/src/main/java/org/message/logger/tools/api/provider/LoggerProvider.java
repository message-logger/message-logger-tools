package org.message.logger.tools.api.provider;

import org.message.logger.tools.api.ExtendedBasicLogger;

public interface LoggerProvider {

   ExtendedBasicLogger getLogger(String name);
}
