package org.message.logger.tools.api;

import java.util.Map;

public interface LibraryLogger extends BasicLogger {

   void debug(String msg, Map<String, String> metadata);

   void debug(String msg, Map<String, String> metadata, Object... arguments);

   void debug(String msg, Map<String, String> metadata, Throwable t);

   void info(String msg, Map<String, String> metadata);

   void info(String msg, Map<String, String> metadata, Object... arguments);

   void info(String msg, Map<String, String> metadata, Throwable t);

   void warn(String msg, Map<String, String> metadata);

   void warn(String msg, Map<String, String> metadata, Object... arguments);

   void warn(String msg, Map<String, String> metadata, Throwable t);

   void error(String msg, Map<String, String> metadata);

   void error(String msg, Map<String, String> metadata, Object... arguments);

   void error(String msg, Map<String, String> metadata, Throwable t);
}
