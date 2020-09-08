package org.message.logger.tools.annotations.sumologic.tf.processor;

import javax.annotation.processing.Filer;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static java.lang.Character.toLowerCase;

class Utils {

   private static final int BUFFER_SIZE = 4096;

   private static final String TARGET = "target";
   private static final String TERRAFORM = "terraform";

   static File lookupTarget(Filer filer) {
      try {
         FileObject resource = filer.getResource(StandardLocation.CLASS_OUTPUT, "", TERRAFORM);

         File target = new File(resource.toUri());

         while (target != null && !TARGET.equals(target.getName())) {
            target = target.getParentFile();
         }

         return target;
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   static File lookupTerraformDirectory(File target) {
      File terraformDirectory = new File(target, TERRAFORM);
      terraformDirectory.mkdirs();

      return terraformDirectory;
   }

   static String folderName(String name) {
      return toLowerCase(name.charAt(0)) + name.substring(1).replaceAll("([A-Z])", "_$1").toLowerCase();
   }

   static String getContentAsString(String resourceName) throws IOException {
      try (InputStream is = Utils.class.getClassLoader().getResourceAsStream(resourceName)) {
         return copyToString(is, StandardCharsets.UTF_8);
      }
   }

   static String getStackTrace(Throwable t) {
      StringWriter sw = new StringWriter();
      t.printStackTrace(new PrintWriter(sw));
      return sw.toString();
   }

   private static String copyToString(InputStream is, Charset charset) throws IOException {
      if (is == null) {
         return "";
      }

      StringBuilder out = new StringBuilder();
      InputStreamReader reader = new InputStreamReader(is, charset);
      char[] buffer = new char[BUFFER_SIZE];
      int charsRead;
      while ((charsRead = reader.read(buffer)) != -1) {
         out.append(buffer, 0, charsRead);
      }
      return out.toString();
   }
}
