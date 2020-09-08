package org.message.logger.tools.api.provider;

import org.message.logger.tools.api.BasicLogger;

public interface LoggerProvider {

   BasicLogger getLogger(String name);
}
