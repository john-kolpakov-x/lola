package kz.pompei.lola.db.test;

import kz.pompei.lola.db.impl.DbRegisterConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ DbRegisterConfig.class })
public class TestConfig {
  
}
