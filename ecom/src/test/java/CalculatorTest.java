import com.generic.ecom.EcomApplication;
import com.generic.ecom.model.Users;
import com.generic.ecom.repo.UserRepo;
import com.generic.ecom.service.UserService;
import com.generic.ecom.view.BusinessLogic.Calculator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = EcomApplication.class)
public class CalculatorTest {

    @MockitoBean
    private UserRepo userRepo; // instead of using real db connection it uses mock here.

    @Autowired
    private UserService userService;

    @Test
    void testAdd() {
        Calculator calc = new Calculator();
        int result = calc.add(2, 3);
        assertEquals(5, result); // Check if result is 5
    }

    @Test
    void testUserByUserName(){
        // Arrange
        Users user = new Users();
        user.setUserName("Bharathwajan");
        user.setFirstName("Hi hello");
        when(userRepo.findByUserName("Bharathwajan")).thenReturn(Optional.of(user));

        //Act
        Optional<Users> result=userService.getUserByUserName("Bharathwjanr");

        //Assert
        assertEquals(result.get().getFirstName(),"Bharathwajan");

    }
}
