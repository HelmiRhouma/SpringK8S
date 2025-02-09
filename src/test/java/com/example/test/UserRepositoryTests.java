package com.example.test;
import com.example.test.user.User;
import com.example.test.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired  private UserRepository repo ;
    @Test
    public void testAddNew(){
        User user = new User();
        user.setFirstName("sta");
        user.setPassword("admin02020");
        user.setEmail("fdf06@gmail.com");
        user.setLastName("ghassen");
        User savedUser = repo.save(user);
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }
    @Test
    public void testListAll(){
        Iterable<User> users = repo.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);
       for(User user : users){
           System.out.println(user);
       }
    }
    @Test
    public void testUpdate(){
        Integer userId = 3;
        Optional<User> optionalUser = repo.findById(userId);

        // Check if user is present to avoid NoSuchElementException
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword("Hello2000");  // Update the password to "Hello2000"
            repo.save(user);  // Save the updated user

            // Retrieve the updated user from the database
            User updatedUser = repo.findById(userId).get();

            // Assert that the password is updated to "Hello2000"
            Assertions.assertThat(updatedUser.getPassword()).isEqualTo("Hello2000");
        } else {
            Assertions.fail("User not found for id " + userId);
        }
    }@Test
    public void testUpdates(){
        Integer userId = 3;
        Optional<User> optionalUser = repo.findById(userId);
        // Check if user is present to avoid NoSuchElementException
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword("Hello2000");  // Update the password to "Hello2000"
            repo.save(user);  // Save the updated user
            // Retrieve the updated user from the database
            User updatedUser = repo.findById(userId).get();
            // Assert that the password is updated to "Hello2000"
            Assertions.assertThat(updatedUser.getPassword()).isEqualTo("Hello2000");
        } else {
            Assertions.fail("User not found for id " + userId);
        }
    }
    //  @Test
//    public void testUpdateMail(){
//        Integer userId = 1;
//        Optional<User> optionalUser = repo.findById(userId);
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            user.setEmail("ghassensta07@gmail.com");
//            repo.save(user);
//            User updatedUser = repo.findById(userId).get();
//            Assertions.assertThat(updatedUser.getEmail()).isEqualTo("ghassensta07@gmail.com");
//        }else {
//            Assertions.fail("User not found for id " + userId);
//        }
//    }
//   @Test
//    public void testGet(){
//        Integer userId = 3;
//        Optional<User> optionalUser = repo.findById(userId);
//       if (optionalUser.isPresent()) {
//           User user = optionalUser.get();
//           Assertions.assertThat(optionalUser).isPresent();
//           System.out.println(optionalUser.get());
//       }else {
//           Assertions.fail("User not found for id " + userId);
//       }
//
//
//   }
//   @Test
//    public void testDelete(){
//        Integer userId = 1;
//
//       Optional<User> optionalUser = repo.findById(userId);
//       if (optionalUser.isPresent()) {
//           repo.deleteById(userId);
//       }else {
//           Assertions.fail("User not found for id " + userId);
//       }
//   }



}
