package com.github.benchdoos.meetroom.abstracts;

import com.github.benchdoos.meetroom.abstracts.marks.UnitTest;
import lombok.RequiredArgsConstructor;
import org.jeasy.random.EasyRandom;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("TEST")
@Category(UnitTest.class)
@RequiredArgsConstructor
public abstract class AbstractUnitTest extends AbstractTest {

    protected final EasyRandom easyRandom;

    @Override
    public void init() throws Exception {
        //no-op
    }

    @Override
    public void tearDown() throws Exception {
        Mockito.validateMockitoUsage();
    }
}
