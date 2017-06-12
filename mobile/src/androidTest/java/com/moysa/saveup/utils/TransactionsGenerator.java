package com.moysa.saveup.utils;

import com.moysa.saveup.data.entity.TransactionEntity;
import com.moysa.saveup.data.model.Pocket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Sergey Moysa
 */

public class TransactionsGenerator {

    private static final String[] COMMENTS = {"Comment 1", "Comment 2", "Comment 3"};

    private static List<TransactionEntity> testTransactions(List<Pocket> pockets) {

        List<TransactionEntity> transactions = new ArrayList<>();
        int index = 0;
        Random random = new Random();

        for (String comment : COMMENTS) {

            for (Pocket pocket : pockets) {

                TransactionEntity transaction = new TransactionEntity();

                transaction.setComment(comment);
                transaction.setId(++index);
                transaction.setAmount(random.nextInt(1000));
                transaction.setDate(new Date());
                transaction.setPocketId(pocket.getId());

                transactions.add(transaction);
            }
        }
        return transactions;
    }

    public static List<TransactionEntity> testTransactions(Pocket... pockets) {
        return testTransactions(Arrays.asList(pockets));
    }

    public static TransactionEntity testTransaction(Pocket pocket) {

        Random random = new Random();
        TransactionEntity transaction = new TransactionEntity();

        transaction.setId(1);
        transaction.setPocketId(pocket.getId());
        transaction.setDate(new Date());
        transaction.setAmount(random.nextInt(1000));
        transaction.setComment(COMMENTS[0]);

        return transaction;
    }
}
