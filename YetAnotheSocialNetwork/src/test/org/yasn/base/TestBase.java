package org.yasn.base;

import org.junit.Before;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public abstract class TestBase {

  @Before
  public void setupTest() {
    MockitoAnnotations.initMocks(this);
    this.beforeEach();
  }

  protected void beforeEach() {
  }
}
