package com.moysa.saveup.view.pockets;

import com.moysa.saveup.data.entity.PocketEntity;
import com.moysa.saveup.data.repository.PocketsRepository;
import com.moysa.saveup.utils.PocketsGenerator;
import com.moysa.saveup.utils.UsersGenerator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by Sergey Moysa
 */
public class PocketsViewModelTest {

    private PocketsViewModel mViewModel;
    private PocketsRepository mRepo;

    @Before
    public void setUp() throws Exception {

        mRepo = Mockito.mock(PocketsRepository.class);
        mViewModel = new PocketsViewModel(mRepo);
    }

    @Test
    public void getPockets() throws Exception {
        mViewModel.getPockets(0);

        Mockito.verify(mRepo).pockets(0);
    }

    @Test
    public void getPocket() throws Exception {
        mViewModel.getPocket(0);

        Mockito.verify(mRepo).pocket(0);
    }

    @Test
    public void create() throws Exception {
        PocketEntity pocket = PocketsGenerator.testPocket(UsersGenerator.testUser());

        mViewModel.create(pocket);

        Mockito.verify(mRepo).create(pocket);
    }

    @Test
    public void update() throws Exception {
        PocketEntity pocket = PocketsGenerator.testPocket(UsersGenerator.testUser());

        mViewModel.update(pocket);

        Mockito.verify(mRepo).update(pocket);
    }

    @Test
    public void delete() throws Exception {
        PocketEntity pocket = PocketsGenerator.testPocket(UsersGenerator.testUser());

        mViewModel.delete(pocket);

        Mockito.verify(mRepo).delete(pocket);
    }

}