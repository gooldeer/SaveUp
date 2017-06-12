package com.moysa.saveup.data;

import com.moysa.saveup.data.entity.PocketEntity;
import com.moysa.saveup.data.entity.TransactionEntity;
import com.moysa.saveup.data.entity.UserEntity;
import com.moysa.saveup.data.model.Pocket;
import com.moysa.saveup.data.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sergey Moysa
 */

class DatabaseInitUtil {

    private static final String USERS[] = new String[]{
            "John", "Mark", "Fred"
    };
    private static final String POCKETS[] = new String[]{
            "On Car", "On Apartment"
    };
    private static final String TRANSACTIONS[] = new String[]{
            "Comment 1", "Comment 2", "Comment 3"
    };

    static void initializeDb(AppDatabase db) {

        List<UserEntity> users = new ArrayList<>();
        List<PocketEntity> pockets = new ArrayList<>();
        List<TransactionEntity> transactions = new ArrayList<>();

        generateData(users, pockets, transactions);
        insertData(db, users, pockets, transactions);
    }

    private static void insertData(AppDatabase db, List<UserEntity> users, List<PocketEntity> pockets, List<TransactionEntity> transactions) {

        db.beginTransaction();

        try {
            db.userDao().insertAll(users);
            db.pocketDao().insertAll(pockets);
            db.transactionDao().insertAll(transactions);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private static void generateData(List<UserEntity> users, List<PocketEntity> pockets, List<TransactionEntity> transactions) {

        Random rnd = new Random();

        for (String name : USERS) {

            UserEntity user = new UserEntity();
            user.setId(rnd.nextInt(10000));
            user.setName(name);

            users.add(user);
        }

        for (User user : users) {

            for (String pocketName : POCKETS) {

                PocketEntity pocket = new PocketEntity();
                pocket.setId(rnd.nextInt(10000));
                pocket.setName(pocketName);
                pocket.setAmount(rnd.nextInt(10000));
                pocket.setUserId(user.getId());

                pockets.add(pocket);
            }
        }

        for (Pocket pocket : pockets) {

            for (String comment : TRANSACTIONS) {

                TransactionEntity transaction = new TransactionEntity();

                transaction.setAmount(rnd.nextInt(100));
                transaction.setComment(comment);
                transaction.setDate(new Date(System.currentTimeMillis()
                        - TimeUnit.DAYS.toMillis(rnd.nextInt(5))
                        + TimeUnit.HOURS.toMillis(rnd.nextInt(10))));
                transaction.setPocketId(pocket.getId());

                transactions.add(transaction);
            }
        }
    }
}
