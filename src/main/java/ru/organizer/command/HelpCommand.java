package ru.organizer.command;

import ru.organizer.dao.Organizer;

public class HelpCommand extends Command {
    
    public HelpCommand(String commandBody) {
        super(commandBody);
    }
    
    @Override
    public String execute(Organizer organizer) {
        return "\nСписок команд: \n" +
                "insert – Добавление нового клиента. \nПример: insert -name Ivanov/Ivan -organization Sberbank -position CEO -email ivan@gmail.com -phones +7999123456\n\n" +
                "update – Редактирование даннных по клиенту по id\nПример: update f6cf7c26 -name Ivanov/Sergei -phones +7999123456,+7999456123\n\n" +
                "delete – Удаление клиента по id.\nПример: delete f6cf7c26\n\n" +
                "list – Список всех клиентов с сортировкой\nПример: list organization\n\n" +
                "find – Поиск клиента по номеру телефона\nПример: find +7999123456\n\n";
    }
    
}
