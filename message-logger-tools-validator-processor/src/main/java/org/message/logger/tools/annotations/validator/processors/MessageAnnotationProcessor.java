package org.message.logger.tools.annotations.validator.processors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.message.logger.tools.annotations.BusinessException;
import org.message.logger.tools.annotations.Message;
import org.message.logger.tools.annotations.utils.MessageUtils;

import static java.lang.String.format;

public class MessageAnnotationProcessor extends AbstractProcessor {

   @Override
   public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
      Messager messager = processingEnv.getMessager();

      for (TypeElement typeElement : annotations) {
         for (Element annotatedElement : env.getElementsAnnotatedWith(typeElement)) {

            ExecutableType executableType = (ExecutableType) annotatedElement.asType();
            List<? extends TypeMirror> parameters = executableType.getParameterTypes();

            String messageTemplate = MessageUtils.resolveMessage((ExecutableElement) annotatedElement);

            int placeholderCount = MessageUtils.countPlaceholders(messageTemplate);
            int parameterCount = parameters.size();

            if (parameterCount > 0) {
               TypeMirror lastParameter = parameters.get(parameterCount - 1);

               boolean isException = checkExceptionTypeByClass(lastParameter, messager) || checkExceptionTypeByName(lastParameter) || checkExceptionTypeByAnnotation(annotatedElement);

               if (isException) {
                  parameterCount -= 1;
               }
            }

            if (placeholderCount != parameterCount) {
               String fqcn = annotatedElement.getEnclosingElement().toString();
               String methodSignature = annotatedElement.toString();
               messager.printMessage(Diagnostic.Kind.ERROR, format("Mismatch at %s#%s (%d placeholders, %d parameters)", fqcn, methodSignature, placeholderCount, parameterCount));
            }

         }
      }

      return false;
   }

   @Override
   public SourceVersion getSupportedSourceVersion() {
      return SourceVersion.latestSupported();
   }

   @Override
   public Set<String> getSupportedAnnotationTypes() {
      return new HashSet<>(Arrays.asList(Message.class.getName()));
   }

   private boolean checkExceptionTypeByClass(TypeMirror parameter, Messager messager) {
      boolean isException = false;
      try {
         Class<?> clazz = Class.forName(parameter.toString());

         isException = Throwable.class.isAssignableFrom(clazz);
      } catch (ClassNotFoundException e) {
         messager.printMessage(Diagnostic.Kind.WARNING, String.format("If you are trying to use a custom exception and the auto discovery does not work for you, please use the annotation %s", BusinessException.class.getName()));
      }
      return isException;
   }

   private boolean checkExceptionTypeByName(TypeMirror parameter) {
      return parameter.toString().endsWith("Exception");
   }

   private boolean checkExceptionTypeByAnnotation(Element annotatedElement) {
      return annotatedElement.getAnnotation(BusinessException.class) != null;
   }
}
