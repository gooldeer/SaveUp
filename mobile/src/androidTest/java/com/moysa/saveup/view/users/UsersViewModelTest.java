package com.moysa.saveup.view.users;

import com.moysa.saveup.data.entity.UserEntity;
import com.moysa.saveup.data.repository.UsersRepository;
import com.moysa.saveup.utils.UsersGenerator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by Sergey Moysa
 */
public class UsersViewModelTest {

    private UsersViewModel mViewModel;
    private UsersRepository mRepo;

    @Before
    public void setUp() throws Exception {

        mRepo = Mockito.mock(UsersRepository.class);
        mViewModel = new UsersViewModel(mRepo);
    }

    @Test
    public void getUsersList() throws Exception {
        mViewModel.getUsersList();

        Mockito.verify(mRepo).users();
    }

    @Test
    public void getUser() throws Exception {
        mViewModel.getUser(0);

        Mockito.verify(mRepo).user(0);
    }

    @Test
    public void create() throws Exception {
        UserEntity user = UsersGenerator.testUser();

        mViewModel.create(user);

        Mockito.verify(mRepo).create(user);
    }

    @Test
    public void update() throws Exception {
        UserEntity user = UsersGenerator.testUser();

        mViewModel.update(user);

        Mockito.verify(mRepo).update(user);
    }

    @Test
    public void delete() throws Exception {

        UserEntity user = UsersGenerator.testUser();

        mViewModel.delete(user);

        Mockito.verify(mRepo).delete(user);
    }

}