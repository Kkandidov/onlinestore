package org.astashonok.onlinestore.util;

import org.astashonok.onlinestore.controller.service.*;

import java.util.HashMap;

public class CommandContainer {
    private HashMap<String, Command> map;

    private CommandContainer() {
        this.map = new HashMap<>();
        map.put("about", new AboutPageCommand());
        map.put("contact",new ContactPageCommand());
        map.put("home", new HomePageCommand());
        map.put("show.all.products", new AllProductsPageCommand());
        map.put("show.products.category", new CategoryProductsPageCommand());
        map.put("show.product", new SingleProductPageCommand());
    }

    private static class CommandContainerHandler {
        private final static CommandContainer instance = new CommandContainer();
    }

    public static CommandContainer getInstance() {
        return CommandContainerHandler.instance;
    }

    public Command getCommand(String key) {
        Command command = map.get(key);
        if (command == null) {
            command = Command.NO_ACTION;
        }
        return command;
    }
}
