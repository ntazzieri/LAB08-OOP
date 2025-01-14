package it.unibo.mvc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;
import it.unibo.mvc.view.DrawNumberConsoleView;
import it.unibo.mvc.view.DrawNumberSwingView;

/**
 * Application entry-point.
 */
public final class LaunchApp {

    private static final int VIEW_COUNT = 3;
    private static final String VIEW_PACKAGE = "it.unibo.mvc.view.";

    private LaunchApp() { }

    /**
     * Runs the application.
     *
     * @param args ignored
     * @throws ClassNotFoundException if the fetches class does not exist
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalAccessException in case of reflection issues
     * @throws IllegalArgumentException in case of reflection issues
     */
    public static void main(final String... args) throws
    ClassNotFoundException, 
    NoSuchMethodException,
    InvocationTargetException,
    IllegalAccessException,
    InstantiationException{
        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);
        final Class<?> swingView = Class.forName(VIEW_PACKAGE + "DrawNumberSwingView"); 
        final Class<?> consoleView = Class.forName(VIEW_PACKAGE + "DrawNumberConsoleView"); 
        final Constructor<?> swingViewConstructor = swingView.getConstructor();
        final Constructor<?> consoleViewConstructor = consoleView.getConstructor();
        for(int i = 0; i < VIEW_COUNT; i++) {
            app.addView((DrawNumberSwingView)swingViewConstructor.newInstance());
            app.addView((DrawNumberConsoleView)consoleViewConstructor.newInstance());
        }
    }
}
