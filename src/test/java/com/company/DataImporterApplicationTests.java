package com.company;

import com.company.util.FileNameParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataImporterApplicationTests {
    @Mock
    private FileNameParser fileNameParser;

    @Before
    public void setUp() {
        fileNameParser = new FileNameParser();
    }

    @Test
    public void testFileNameParser() {
        String fileName = "PortedNumbers_2017_07_21";
        Calendar dateFromFileName = fileNameParser.getTimestampFromFileName(fileName);

        Calendar dateToCompare = Calendar.getInstance();
        dateToCompare.set(2017, 7, 21);

        Assert.assertEquals(dateToCompare.getTime(), dateFromFileName.getTime());
    }
}