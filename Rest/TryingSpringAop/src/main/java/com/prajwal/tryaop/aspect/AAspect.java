package com.prajwal.tryaop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AAspect {


    //before the method
    @Before("execution(public int com.prajwal.tryaop.model.AModel.getA(..))")
    public void beforeA(){
        System.out.println("before a...");
    }


    //after the method getA with 0 or more args
    @After("execution(public int com.prajwal.tryaop.model.AModel.getA(..))")
    public void afterA(){
        System.out.println("after a...");
    }


    //before every method having string as argument
    @Before("execution(public String com.prajwal.tryaop.model.AModel.*(String))")
    public void beforeString(){
        System.out.println("before string..");
    }


    //before and after the method with string as argument
    @Around("execution(public String com.prajwal.tryaop.model.AModel.*(String))")
    public String aroundString(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        System.out.println("before string in around..");
        String returnArg = (String)proceedingJoinPoint.proceed();
        System.out.println("after string around..");

        return returnArg;


    }


    //using point cut
    @Before("pointCutMethod()")
    public void usingPointCut(){
        System.out.println("printing something using pointcut..");
    }


    // a pointcut
    @Pointcut("execution(public int com.prajwal.tryaop.model.AModel.getB())")
    public void pointCutMethod(){}


    //execute after only when the method returns and getting the value returned using returning
    @AfterReturning(pointcut = "execution(public boolean com.prajwal.tryaop.model.AModel.is*())",returning = "val")
    public void afterReturningExecute(boolean val){
        System.out.println("executed after return.."+val);
    }

    //executed after exception is thrown getting the object of exception throws using throwing
    @AfterThrowing(pointcut = "execution(public boolean com.prajwal.tryaop.model.AModel.is*())",throwing = "exception")
    public void afterThrowingExecute(RuntimeException exception){
        System.out.println("executed after throwing.."+exception.getMessage());
    }

    //executed after the method with @MyAnnotation is executed
    @After("@annotation(com.prajwal.tryaop.model.MyAnnotation)")
    public void afterMethodWithAnnotation(){
        System.out.println("after annotation..");
    }


    //not possible in newer java versions...
//    @After("args(java.lang.String)")
//    public void afterAMethodWIthStringArgs(){
//        System.out.println("after a method with string args....");
//    }
}
