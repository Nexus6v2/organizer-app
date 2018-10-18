package ru.organizer;

import lombok.SneakyThrows;
import org.junit.Test;
import ru.organizer.command.OrganizerCommandFactory;
import ru.organizer.dao.Organizer;
import ru.organizer.entity.Customer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class OrganizerAppTests {
    private Organizer organizer;
    private OrganizerCommandFactory organizerCommandFactory;
    
    private String executeCommand(String commandBody) {
        return organizerCommandFactory.createCommand(commandBody).execute(organizer);
    }
    
    @SneakyThrows
	private void init() {
        organizerCommandFactory = new OrganizerCommandFactory();
        
        Customer testCustomer1 = new Customer()
                .setId("a7cf2ac5")
                .setName("IvanIvanov")
                .setEmail("ivanov@gmail.com")
                .setOrganization("Sberbank")
                .setPosition("Developer")
                .setPhones(Arrays.asList("89991234567", "89997654321"));
        
        Customer testCustomer2 = new Customer()
                .setId("f6cf7c26")
                .setName("DmitryPetrov")
                .setEmail("petrov@gmail.com")
                .setOrganization("Sberbank")
                .setPosition("Developer")
                .setPhones(Arrays.asList("84567891234"));
        
        organizer = new Organizer("ru/organizer/organizer.xml");
        
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(testCustomer1);
        customers.add(testCustomer2);
        Field customersField = organizer.getClass().getDeclaredField("customers");
        customersField.setAccessible(true);
        customersField.set(organizer, customers);
    
        Method persist = organizer.getClass().getDeclaredMethod("persist");
        persist.setAccessible(true);
        persist.invoke(organizer);
    }
    
    @Test
    public void testListCommand() {
	    init();
	    String listCommandBody = "list organication;name;email";
	    String output = executeCommand(listCommandBody);
	    
	    assertEquals("id: f6cf7c26, name: DmitryPetrov, organization: Sberbank, position: Developer, email: petrov@gmail.com, phones: [84567891234]\n" +
                        "id: a7cf2ac5, name: IvanIvanov, organization: Sberbank, position: Developer, email: ivanov@gmail.com, phones: [89991234567, 89997654321]",
                output);
    }
    
    @Test
    public void testInsertCommand() {
	    init();
        String insertCommandBody = "insert -name FedorKuznetsov -organization Sberbank -position SeniorDeveloper -email kuzentsov@gmail.com -phones 81234567123,81234567120";
        String output = executeCommand(insertCommandBody);
        
        assertEquals("Создан клиент: \"id: ********, name: FedorKuznetsov, organization: Sberbank, position: SeniorDeveloper, email: kuzentsov@gmail.com, phones: [81234567123, 81234567120]\"\n",
                output.replaceAll("id: (\\w+)", "id: ********"));
	}
    
    @Test
    public void testFindCommand() {
	    init();
	    String findCommandBody = "find 89991234567";
        String output = executeCommand(findCommandBody);
        
	   assertEquals("id: a7cf2ac5, name: IvanIvanov, organization: Sberbank, position: Developer, email: ivanov@gmail.com, phones: [89991234567, 89997654321]",
               output);
    }
    
    @Test
    public void testUpdateCommand() {
	    init();
        String updateCommandBody = "update a7cf2ac5 -name IvanSmirnov -email smirnov@mail.ru";
        String output = executeCommand(updateCommandBody);
        
        assertEquals("Изменён клиент:\n" +
                "id: a7cf2ac5, name: IvanSmirnov, organization: Sberbank, position: Developer, email: smirnov@mail.ru, phones: [89991234567, 89997654321]",
                output);
    }
    
    @Test
    public void testDeleteCommand() {
	    init();
	    String deleteCommandBody = "delete a7cf2ac5";
	    String output = executeCommand(deleteCommandBody);
        System.out.println(output);
	    
	    assertEquals("Удален клиент:\n" +
                "id: a7cf2ac5, name: IvanIvanov, organization: Sberbank, position: Developer, email: ivanov@gmail.com, phones: [89991234567, 89997654321]",
                output);
    }
	
}
