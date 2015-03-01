package kz.pompei.lola.db.impl;

import static org.fest.assertions.Assertions.assertThat;
import kz.pompei.lola.db.AccountRegister;
import kz.pompei.lola.db.test.TestConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(classes = TestConfig.class)
public class AccountRegisterImplTest extends AbstractTestNGSpringContextTests {
  
  @Autowired
  private AccountRegister r;
  
  @Test
  public void rIsNotNull() throws Exception {
    assertThat(r).isNotNull();
  }
}
