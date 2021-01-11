package org.upgrad.upstac.testrequests.consultation;


import lombok.Getter;
import lombok.Setter;
import org.aspectj.asm.IProgramElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.testrequests.TestRequestQueryService;
import org.upgrad.upstac.testrequests.TestRequestUpdateService;
import org.upgrad.upstac.testrequests.flow.TestRequestFlowService;
import org.upgrad.upstac.testrequests.lab.CreateLabResult;
import org.upgrad.upstac.users.User;
import org.upgrad.upstac.users.UserService;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.upgrad.upstac.exception.UpgradResponseStatusException.asBadRequest;
import static org.upgrad.upstac.exception.UpgradResponseStatusException.asConstraintViolation;
@Getter
@Setter

@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {

    Logger log = LoggerFactory.getLogger(ConsultationController.class);




    @Autowired
    private TestRequestUpdateService testRequestUpdateService;

    @Autowired
    private TestRequestQueryService testRequestQueryService;


    @Autowired
    TestRequestFlowService  testRequestFlowService;

    @Autowired
    private UserLoggedInService userLoggedInService;

    @Autowired
    private UserService userService;
    private CreateLabResult createLabResult;
    private User tester;

    @GetMapping("/in-queue")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public List<TestRequest> getForConsultations()  {
            User newUser = new User();

            newUser.setUserName("Ray");
            User User1=userService.findByUserName("Nethra");
            System.out.println(User1);
        return testRequestQueryService.findByTester(User1);

        // Implement this method


        //Implement this method to get the list of test requests having status as 'LAB_TEST_COMPLETED'
        // make use of the findBy() method from testRequestQueryService class
        //return the result
        // For reference check the method getForTests() method from LabRequestController class



    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public List<TestRequest> getForDoctor()  {
        User newUser = new User();

        newUser.setUserName("Ray");
        User User1=userService.findByUserName("Nethra");
        System.out.println(User1);
        return testRequestQueryService.findByDoctor(User1);



    }



    @PreAuthorize("hasAnyRole('DOCTOR')")
    @PutMapping("/assign/{id}")
    public Object assignForConsultation(@PathVariable Long id) {

        /*User newUser = new User();

        newUser.setUserName("Ray");
        User User1=userService.findByUserName("Nethra");
        System.out.println(User1);
        return   testRequestUpdateService.assignForConsultation(id,tester);*/ // Implement this method
        User tester =userLoggedInService.getLoggedInUser();

        return   testRequestUpdateService.assignForLabTest(id,tester);

        // Implement this method to assign a particular test request to the current doctor(logged in user)
        //Create an object of User class and get the current logged in user
        //Create an object of TestRequest class and use the assignForConsultation() method of testRequestUpdateService to assign the particular id to the current user
        // return the above created object
        // For reference check the method assignForLabTest() method from LabRequestController class

    }


    @PreAuthorize("hasAnyRole('DOCTOR')")
    @PutMapping("/update/{id}")
    public TestRequest updateConsultation(@PathVariable Long id,@RequestBody CreateConsultationRequest testResult) {

          // Implement this method

        // Implement this method to update the result of the current test request id with test doctor comments
        // Create an object of the User class to get the logged in user
        // Create an object of TestResult class and make use of updateConsultation() method from testRequestUpdateService class
        //to update the current test request id with the testResult details by the current user(object created)
        // For reference check the method updateLabTest() method from LabRequestController class

        try {
            return testRequestUpdateService.updateLabTest(id,createLabResult,tester);// replace this line of code with your implementation



        } catch (ConstraintViolationException e) {
            throw asConstraintViolation(e);
        }catch (AppException e) {
            throw asBadRequest(e.getMessage());
        }
    }



}
