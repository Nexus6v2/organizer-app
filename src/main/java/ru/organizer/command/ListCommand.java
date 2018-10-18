package ru.organizer.command;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import ru.organizer.dao.Organizer;
import ru.organizer.entity.Customer;

import java.lang.reflect.Method;
import java.sql.Ref;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ListCommand extends Command {
    
    public ListCommand(String commandBody) {
        super(commandBody);
    }
    
    @Override
    @SneakyThrows
    public String execute(Organizer organizer) {
        Comparator<Customer> comparator = Comparator.comparing(Customer::getId);
        
        if (mainArgument != null) {
            List<Method> getters = Arrays.stream(mainArgument.split(";"))
                    .map(fieldName -> "get" + StringUtils.capitalize(fieldName))
                    .map(getterName -> {
                        try {
                            return Customer.class.getDeclaredMethod(getterName);
                        } catch (ReflectiveOperationException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(toList());
            
            comparator = Comparator.comparing(makeGetterFunction(getters.get(0)));
            for (Method getter : getters.subList(1, getters.size())) {
                comparator.thenComparing(makeGetterFunction(getter));
            }
        }
        
        List<String> customer = organizer.getCustomers()
                .stream()
                .sorted(comparator)
                .map(Customer::toString)
                .collect(toList());
    
        return String.join("\n", customer);
    }
    
    private Function makeGetterFunction(Method getter) {
        return (customer) -> {
            try {
                Object result = getter.invoke(customer);
                return result == null ? "zzz" : result;
            } catch (ReflectiveOperationException e) {
                return null;
            }
        };
    }

}
