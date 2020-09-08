package org.message.logger.tools.slf4j.provider;

import org.message.logger.tools.api.MessageLoggerFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.slf4j.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class SLF4JLoggerProviderTest {

   @Rule
   public final SystemErrRule output = new SystemErrRule().enableLog();

   @Test
   public void givenNoSL4JProvidersShouldFallbackToSL4JNop() {
      DefaultMessageLogger logger = MessageLoggerFactory.getLogger(DefaultMessageLogger.class);

      assertThat(output.getLog(), containsString("SLF4J: No SLF4J providers were found"));
   }

   @Test
   public void givenNoSL4JProvidersShouldFallbackToSL4JNop1() {
      DefaultMessageLogger logger = MessageLoggerFactory.getLogger(DefaultMessageLogger.class);

      Logger underlyingLogger = logger.unwrap(Logger.class);
   }
}
