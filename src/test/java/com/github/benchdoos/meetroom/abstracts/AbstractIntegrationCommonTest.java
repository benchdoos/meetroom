package com.github.benchdoos.meetroom.abstracts;

import com.github.benchdoos.meetroom.abstracts.marks.IntegrationTest;
import com.github.benchdoos.meetroom.config.TestMailSenderConfig;
import com.github.benchdoos.meetroom.config.beans.SpringConfigurationInfoBean;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import org.jetbrains.annotations.NotNull;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Abstract class for all integration tests
 */
@ActiveProfiles("TEST")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AbstractContainerizedTest.Config.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = AbstractIntegrationCommonTest.Initializer.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Import(TestMailSenderConfig.class)
@Slf4j
@Category(IntegrationTest.class)
public abstract class AbstractIntegrationCommonTest extends AbstractContainerizedTest {

    protected final EasyRandom easyRandom = new EasyRandom();

    @Override
    public void setUp() throws Exception {
        /*NOP*/
    }

    @Override
    public void tearDown() throws Exception {
        /*NOP*/
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(@NotNull ConfigurableApplicationContext configurableApplicationContext) {
        }
    }
}
