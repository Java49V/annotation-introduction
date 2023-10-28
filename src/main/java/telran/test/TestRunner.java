package telran.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import telran.test.annotation.BeforeEach;
import telran.test.annotation.Test;

public class TestRunner implements Runnable {
    private Object testObj;
    
    public TestRunner(Object testObj) {
        super();
        this.testObj = testObj;
    }

    @Override
    public void run() {
        Class<?> clazz = testObj.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        
        List<Method> beforeEachMethods = new ArrayList<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeEach.class)) {
                method.setAccessible(true);
                beforeEachMethods.add(method);
            }
        }

        for (Method testMethod : methods) {
            if (testMethod.isAnnotationPresent(Test.class)) {
                for (Method beforeEach : beforeEachMethods) {
                    try {
                        beforeEach.invoke(testObj);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        System.out.println("error in BeforeEach: " + e.getMessage());
                    }
                }
                testMethod.setAccessible(true);
                try {
                    testMethod.invoke(testObj);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    System.out.println("error: " + e.getMessage());
                }
            }
        }
    }
}

// #1 

//public class TestRunner implements Runnable {
//    private Object testObj;
//    
//    public TestRunner(Object testObj) {
//        super();
//        this.testObj = testObj;
//    }
//
//    @Override
//    public void run() {
//        Class<?> clazz = testObj.getClass();
//        Method[] methods = clazz.getDeclaredMethods();
//        
//        Method beforeEachMethod = null;
//        for (Method method : methods) {
//            if (method.isAnnotationPresent(BeforeEach.class)) {
//                beforeEachMethod = method;
//                beforeEachMethod.setAccessible(true);
//                break;
//            }
//        }
//
//        for (Method method : methods) {
//            if (method.isAnnotationPresent(Test.class)) {
//                if (beforeEachMethod != null) {
//                    try {
//                        beforeEachMethod.invoke(testObj);
//                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//                        System.out.println("error in BeforeEach: " + e.getMessage());
//                    }
//                }
//                method.setAccessible(true);
//                try {
//                    method.invoke(testObj);
//                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//                    System.out.println("error: " + e.getMessage());
//                }
//            }
//        }
//    }
//}




//package telran.test;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//
//import telran.test.annotation.Test;
//
//public class TestRunner implements Runnable {
//	private Object testObj;
//	
//	public TestRunner(Object testObj) {
//		super();
//		this.testObj = testObj;
//	}
//
//	@Override
//	public void run() {
//			Class<?> clazz = testObj.getClass();
//			Method[] methods = clazz.getDeclaredMethods();
//			for(Method method: methods) {
//				if(method.isAnnotationPresent(Test.class)) {
//					method.setAccessible(true);
//					try {
//						method.invoke(testObj);
//					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//						System.out.println("error: " + e.getMessage());
//					}
//				}
//			}
//
//	}
//
//}
