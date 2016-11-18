package com.krestone.savealife.data.repository;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.krestone.savealife.presentation.models.ContactModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class ContactsRepositoryImp implements ContactsRepository {

    private final String[] projectionFields = new String[] {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME
    };

    private Context context;

    public ContactsRepositoryImp(Context context) {
        this.context = context;
    }

    @Override
    public Observable<List<ContactModel>> getContacts() {
        return Observable.defer(() -> Observable.from(queryContacts()))
                .filter(contact -> contact.getMobileNumber() != null)
                .toList();
    }

    private List<ContactModel> queryContacts() {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, projectionFields, null, null, null);
        List<ContactModel> contacts = new ArrayList<>();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacts.add(new ContactModel(id, name));

                parsePhoneNumbers(resolver, contacts.get(contacts.size() - 1));
            }
            cursor.close();
        }

        return contacts;
    }

    private void parsePhoneNumbers(ContentResolver resolver, ContactModel contactModel) {
        Cursor phones = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactModel.getId(), null, null);

        if (phones != null) {
            while (phones.moveToNext()) {
                String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                switch (type) {
                    case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                        contactModel.setHomeNumber(number);
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                        contactModel.setMobileNumber(number);
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                        contactModel.setWorkNumber(number);
                        break;
                }
            }
            phones.close();
        }
    }
}
