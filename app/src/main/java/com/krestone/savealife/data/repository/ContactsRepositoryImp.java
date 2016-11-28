package com.krestone.savealife.data.repository;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.krestone.savealife.data.sqlite.SaveAlifeDatabaseHelper;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.ArrayList;
import java.util.List;

import rx.Completable;
import rx.Observable;
import rx.Single;


public class ContactsRepositoryImp implements ContactsRepository {

    private static final String PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
    private static final String PHONE_TYPE = ContactsContract.CommonDataKinds.Phone.TYPE;
    private static final String CONTACT_NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
    private static final String PHOTO_URI = ContactsContract.CommonDataKinds.Phone.PHOTO_URI;
    private static final String CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;

    private final String[] projectionFields = new String[]{
            PHONE_NUMBER, PHONE_TYPE, CONTACT_NAME, PHOTO_URI, CONTACT_ID
    };

    private Context context;

    private SaveAlifeDatabaseHelper databaseHelper;

    public ContactsRepositoryImp(Context context, SaveAlifeDatabaseHelper databaseHelper) {
        this.context = context;
        this.databaseHelper = databaseHelper;
    }

    @Override
    public Single<List<ContactModel>> getEmergencyContacts() {
        return Single.defer(() -> Single.just(databaseHelper.getAllEmergencyContacts()));
    }

    @Override
    public Completable updateEmergencyContacts(List<ContactModel> contacts) {
        return Completable.defer(() -> {
            Observable.from(contacts).filter(ContactModel::isInEmergencyList).toList().toSingle().subscribe(contactModels -> {
                databaseHelper.deleteAllContacts();
                databaseHelper.addContacts(contactModels);
            });
            return Completable.complete();
        });
    }

    @Override
    public Single<List<ContactModel>> getContacts() {
        return Single.zip(getEmergencyContacts(), Single.just(queryContacts()), (emergencyContacts, allContacts) -> {
            contactContacts(emergencyContacts, allContacts);
            return allContacts;
        });
    }

    // TODO: should be done through contact ids, read below
    private void contactContacts(List<ContactModel> emergencyContacts, List<ContactModel> allContacts) {
        for (ContactModel emergencyContact : emergencyContacts) {
            for (ContactModel plainContact : allContacts) {
                if (plainContact.getMobileNumber().equals(emergencyContact.getMobileNumber()) &&
                        plainContact.getName().equals(emergencyContact.getName())) {
                    plainContact.setInApp(emergencyContact.isInApp());
                    plainContact.setInEmergencyList(emergencyContact.isInEmergencyList());
                    break;
                }
            }
        }
    }

    // TODO: duplicates are possible when a contact has more than one number
    // TODO: should be eliminated with contact_id field
    private List<ContactModel> queryContacts() {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projectionFields, null, null, null);
        List<ContactModel> contacts = new ArrayList<>();

        if (cursor != null && cursor.getCount() > 0) {
            int idColumnIndex = cursor.getColumnIndex(CONTACT_ID);
            int nameColumnIndex = cursor.getColumnIndex(CONTACT_NAME);
            int thumbnailColumnIndex = cursor.getColumnIndex(PHOTO_URI);
            int numberColumnIndex = cursor.getColumnIndex(PHONE_NUMBER);

            while (cursor.moveToNext()) {
                String id = cursor.getString(idColumnIndex);
                String name = cursor.getString(nameColumnIndex);
                String thumbnailUri = cursor.getString(thumbnailColumnIndex);
                String phoneNumber = cursor.getString(numberColumnIndex);

                contacts.add(new ContactModel(id, name, thumbnailUri, phoneNumber));
            }
            cursor.close();
        }

        return contacts;
    }
}
