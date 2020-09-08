package org.message.logger.tools.api;

import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class MessageLoggerFactoryTest {

   @Test
   public void messageLoggerCouldNotBeNull() {
      assertThrows(NullPointerException.class, () -> MessageLoggerFactory.getLogger(null));
   }

   @Test
   public void messageLoggerNOPCannotBeUnwrap() {
      UnwrapLogger LOGGER = MessageLoggerFactory.getLogger(UnwrapLogger.class);

      assertThrows(UnsupportedOperationException.class, () -> LOGGER.unwrap(BasicLogger.class));
   }
}
