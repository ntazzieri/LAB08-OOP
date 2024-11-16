package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

/**
 * Console only output {@link DrawNumberView} implementation
 */
public class DrawNumberConsoleView implements DrawNumberView {

    /**
     * {@inheritDoc}
     * NOTE: It doesn't attach any controller since this view is output only
     */
    @Override
    public void setController(DrawNumberController observer) { }

    @Override
    public void start() { }

    @Override
    public void result(DrawResult res) {
        switch (res) {
            case YOURS_HIGH, YOURS_LOW -> {
                System.out.println(res.getDescription());
            }
            case YOU_WON, YOU_LOST -> {
                System.out.println(res.getDescription() + ": a new game starts!");
            }
            default -> {
                throw new IllegalStateException("Unknow game state");
            }
        }
    }
    
}
