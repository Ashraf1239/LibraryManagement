package com.book.librarymanagement.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogTimeAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogTimeAspect.class);

    @Pointcut("execution(* com.book.librarymanagement.services.BookService.addBook(..)) || " +
            "execution(* com.book.librarymanagement.services.BookService.updateBook(..)) || " +
            "execution(* com.book.librarymanagement.services.BorrowService.borrowBook(..)) || " +
            "execution(* com.book.librarymanagement.services.BorrowService.returnBook(..))")
    public void monitoredMethods() {}

    @Before("monitoredMethods()")
    public void logMethodEntry() {
        logger.info("Method entered and operation started");
    }

    @AfterThrowing(pointcut = "monitoredMethods()", throwing = "exception")
    public void logException(Exception exception) {
        logger.error("Exception occurred: " + exception.getMessage());
    }
}
