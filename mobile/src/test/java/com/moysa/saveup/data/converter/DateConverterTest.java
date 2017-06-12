package com.moysa.saveup.data.converter;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Sergey Moysa
 */
public class DateConverterTest {

    private Date mDate;
    private Long mTimestamp;

    @Before
    public void setUp() throws Exception {
        mTimestamp = System.currentTimeMillis();
        mDate = new Date(mTimestamp);
    }

    @Test
    public void toDate() throws Exception {

        assertEquals(mDate, DateConverter.toDate(mTimestamp));
        assertNull(DateConverter.toDate(null));
    }

    @Test
    public void toTimestamp() throws Exception {

        assertEquals(mTimestamp, DateConverter.toTimestamp(mDate));
        assertNull(DateConverter.toTimestamp(null));
    }

}