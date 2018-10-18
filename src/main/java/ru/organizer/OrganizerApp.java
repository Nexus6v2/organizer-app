package ru.organizer;

import lombok.SneakyThrows;
import ru.organizer.command.Command;
import ru.organizer.command.OrganizerCommandFactory;
import ru.organizer.dao.Organizer;
import ru.organizer.exception.UnknownCommandException;

import java.util.Arrays;
import java.util.Scanner;

public class OrganizerApp {
    
    @SneakyThrows
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
    
        Organizer organizer = new Organizer("ru/organizer/organizer.xml");
        OrganizerCommandFactory commandFactory = new OrganizerCommandFactory();
        
        System.out.println("Приложение \"Органайзер\". Для списка команд введите \"help\".");
        
        while (true) {
            System.out.println("\nВведите команду:");
            String input = scanner.nextLine();
    
            try {
                Command command = commandFactory.createCommand(input);
                String output = command.execute(organizer);
                System.out.println(output);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
