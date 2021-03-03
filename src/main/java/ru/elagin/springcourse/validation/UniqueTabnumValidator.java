package ru.elagin.springcourse.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ru.elagin.springcourse.models.Customer;
import ru.elagin.springcourse.repository.CustomerRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueTabnumValidator implements ConstraintValidator<UniqueTabnum, Customer> {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void initialize(UniqueTabnum constraintAnnotation) {
    }

    @Override
    public boolean isValid(Customer customer, ConstraintValidatorContext constraintValidatorContext) {

        List<Customer> customerList = customerRepository.index();

        for (Customer client : customerList) {
            if (client.getTabnum() == customer.getTabnum() && client.getId() != customer.getId()) {

                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("This number already exists in the database")
                        .addPropertyNode("tabnum").addConstraintViolation();

                return false;
            }
        }
        return true;
    }

}
