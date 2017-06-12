package com.moysa.saveup.utils;

import com.moysa.saveup.data.entity.PocketEntity;
import com.moysa.saveup.data.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Sergey Moysa
 */

public class PocketsGenerator {

    private static final String[] POCKETS = {"On Car", "On Apartment", "On Trip"};

    public static List<PocketEntity> testPockets(User user) {

        int index = 0;
        Random random = new Random();

        List<PocketEntity> pockets = new ArrayList<>();

        for (String name : POCKETS) {

            PocketEntity pocket = new PocketEntity();

            pocket.setName(name);
            pocket.setId(++index);
            pocket.setUserId(user.getId());
            pocket.setAmount(random.nextInt(10000));

            pockets.add(pocket);
        }
        return pockets;
    }

    public static PocketEntity testPocket(User user) {

        PocketEntity pocket = new PocketEntity();
        Random random = new Random();

        pocket.setId(1);
        pocket.setUserId(user.getId());
        pocket.setAmount(random.nextInt(10000));
        pocket.setName(POCKETS[0]);

        return pocket;
    }
}
