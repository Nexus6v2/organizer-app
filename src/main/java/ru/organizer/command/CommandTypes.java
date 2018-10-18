package ru.organizer.command;

import lombok.RequiredArgsConstructor;
import ru.organizer.exception.UnknownCommandException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public enum CommandTypes {
    help(HelpCommand.class),
    insert(InsertCommand.class),
    update(UpdateCommand.class),
    delete(DeleteCommand.class),
    list(ListCommand.class),
    find(FindCommand.class);
    
    private final Class<? extends Command> command;
    
    public static Class<? extends Command> getCommandByName(String commandName) {
        Class<? extends Command> command = COMMANDS_MAP.get(commandName);
        if (command == null) {
            throw new UnknownCommandException(commandName);
        }
        return command;
    }
    
    private static final Map<String, Class<? extends Command>> COMMANDS_MAP =
            Collections.unmodifiableMap(mapNameToCommand());
    
    private static Map<String, Class<? extends Command>> mapNameToCommand() {
        Map<String, Class<? extends Command>> map = new HashMap<>();
        for (CommandTypes type : CommandTypes.values()) {
            map.put(type.toString(), type.command);
        }
        return map;
    }
    
}
