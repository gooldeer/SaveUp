package com.moysa.saveup.data.model;

import java.util.Date;

/**
 * Created by Sergey Moysa
 */

public interface Transaction {

    int getId();

    int getPocketId();

    float getAmount();

    String getComment();

    Date getDate();
}
