package com.example.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerController {
    private static List<Customer> customerList = new ArrayList<Customer>();
    private static final String emailAddress = "malika.satimbaeva@iaau.edu.kg";
    @Autowired
    private CustomerRepository customerRepository;


    static {

        customerList.add(new Customer("Bill", "Gates","qw","we","er","sd","we"));
    }



    @RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("message", message);
        //
        //
        //


        return "home";
    }



    @GetMapping(value = "/customerList")
    public String customerList( String emailAddress, Model model) {
        List<Customer> customers= customerRepository.findByEmailAddress(emailAddress);
        if (customers != null) {
            model.addAttribute("customerList", customerList);

        }

        return "customerList";
    }

    // ​​​​​​​
    // Вводится (inject) из application.properties.
    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;


    @RequestMapping(value = { "/addCustomer" }, method = RequestMethod.GET)
    public String showAddCustomersPage(Model model) {

        CustomerForm customerForm = new CustomerForm();
        model.addAttribute("customerForm", customerForm);

        return "addCustomer";
    }



    @PostMapping(value = "/addCustomer")
    public String saveCustomer(Customer customer,Model model,@ModelAttribute("customerForm") CustomerForm customerForm) {

        String firstName = customerForm.getFirstName();
        String lastName = customerForm.getLastName();
        String emailAddress = customerForm.getEmailAddress();
        String jobTitle = customerForm.getJobTitle();
        String mobilePhone = customerForm.getMobilePhone();
        String city = customerForm.getCity();
        String webPage = customerForm.getWebPage();


        if (firstName != null && firstName.length() > 0 //
                && lastName != null && lastName.length() > 0
        ) {
            Customer newPerson = new Customer(firstName, lastName,emailAddress,jobTitle,mobilePhone,city,webPage);
            customerList.add(newPerson);
            //customerList.add(customer);//
            //customer.setEmailAddress(emailAddress);
            customerList.addAll(customerRepository.findAll());

            customerRepository.save(customer);


            return "redirect:/customerList";
        }


        model.addAttribute("errorMessage", errorMessage);

            return "redirect:/customerList";

    }




}
