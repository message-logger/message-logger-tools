package org.message.logger.tools.api.provider;

import org.message.logger.tools.api.LibraryLogger;

public interface LoggerProvider {

   LibraryLogger getLogger(String name);
}
