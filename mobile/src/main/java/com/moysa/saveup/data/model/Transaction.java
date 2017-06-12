package com.moysa.saveup.data.model;

import java.util.Date;

/**
 * Created by Sergey Moysa
 */

public interface Transaction {

    int getId();

    int getPocketId();

    int getAmount();

    String getComment();

    Date getDate();
}
