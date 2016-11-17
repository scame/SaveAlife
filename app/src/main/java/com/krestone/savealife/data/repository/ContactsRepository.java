package com.krestone.savealife.data.repository;


import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

import rx.Observable;

public interface ContactsRepository {

    Observable<List<ContactModel>> getContacts();
}
