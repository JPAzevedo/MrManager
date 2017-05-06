package pt.jpazevedo.mrmanager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.util.Date;

import pt.jpazevedo.mrmanager.util.DateUtilities;

/**
 * Created by joaop on 06/05/2017.
 */

public class DateUnitTest {

    @Test
    public void testGetJanuaryMonth(){

        String expected = "January";
        int month = 0;
        String output = getDateUtilities().getCurrentMonth(month);

        Assert.assertEquals(output,expected);
    }

    @Test
    public void testConverMsToString(){
        String expected = "06-05-2017";
        long ms = 149409960000L;
        String output = getDateUtilities().millisecondToString(ms);

        Assert.assertEquals(output,expected);
    }

    private DateUtilities getDateUtilities(){
        return new DateUtilities();
    }

}
