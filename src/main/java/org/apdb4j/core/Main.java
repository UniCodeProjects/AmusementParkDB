package org.apdb4j.core;

import javafx.application.Application;
import org.apdb4j.view.App;

/**
 * Main class.
 */
public final class Main {

    private Main() {
    }

    /**
     * Main method.
     * @param args main args.
     */
    public static void main(final String[] args) {
        Application.launch(App.class, args);
    }
}
