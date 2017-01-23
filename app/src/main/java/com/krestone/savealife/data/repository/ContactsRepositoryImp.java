package com.krestone.savealife.data.repository;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.krestone.savealife.data.entities.requests.ContactsNumbersHolder;
import com.krestone.savealife.data.entities.responses.ContactsHolder;
import com.krestone.savealife.data.mappers.NotInEmergencyListFilter;
import com.krestone.savealife.data.rest.ServerApi;
import com.krestone.savealife.data.sqlite.EmergencyContactsTable;
import com.krestone.savealife.data.sync.states.DataStates;
import com.krestone.savealife.presentation.models.ContactModel;
import com.krestone.savealife.util.PrefsUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Completable;
import rx.Scheduler;
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

    private ServerApi serverApi;

    private Context context;

    private NotInEmergencyListFilter notInEmergencyListFilter;

    private EmergencyContactsTable emergencyContactsTable;

    private Scheduler scheduler;

    public ContactsRepositoryImp(Context context, ServerApi serverApi, EmergencyContactsTable emergencyContactsTable,
                                 NotInEmergencyListFilter notInEmergencyListFilter,
                                 Scheduler scheduler) {
        this.context = context;
        this.serverApi = serverApi;
        this.scheduler = scheduler;
        this.notInEmergencyListFilter = notInEmergencyListFilter;
        this.emergencyContactsTable = emergencyContactsTable;
    }

    @Override
    public Completable updateDataState(List<ContactModel> contacts, DataStates dataState) {
        return Completable.fromCallable(() -> emergencyContactsTable.updateDataState(contacts, dataState));
    }

    @Override
    public Single<List<ContactModel>> getContactsNotInEmergencyList() {
        return Single.zip(
                Single.just(queryContacts()).subscribeOn(scheduler),
                serverApi.getEmergencyContacts(PrefsUtil.getAuthToken(context)).subscribeOn(scheduler).toSingle(),
                notInEmergencyListFilter::filter
        );
    }

    @Override
    public Single<List<ContactModel>> getContactsNotInEmergencyListLocal() {
        return Single.zip(
                Single.just(queryContacts()).subscribeOn(scheduler),
                getEmergencyContactsLocal().subscribeOn(scheduler),
                notInEmergencyListFilter::filter
        );
    }

    @Override
    public Single<ContactsHolder> getContactsInApp() {
        return serverApi.getContactsInApp(getContactsNumbers(), PrefsUtil.getAuthToken(context)).toSingle();
    }

    private ContactsNumbersHolder getContactsNumbers() {
        List<String> numbers = new ArrayList<>();
        List<ContactModel> detailedContacts = queryContacts();

        for (ContactModel contactModel : detailedContacts) {
            numbers.add(contactModel.getNumber());
        }
        return new ContactsNumbersHolder(numbers);
    }

    @Override
    public Completable addOrUpdateEmergencyContacts(List<ContactModel> contactModels) {
        return Completable.fromCallable(() -> emergencyContactsTable.addOrUpdateLocalContacts(contactModels));
    }

    @Override
    public Completable addToEmergencyList(List<ContactModel> contactModels) {
        return serverApi.addToEmergencyList(new ContactsHolder(contactModels),
                PrefsUtil.getAuthToken(context)).toCompletable();
    }

    @Override
    public Single<ContactsHolder> getEmergencyContacts() {
        return serverApi.getEmergencyContacts(PrefsUtil.getAuthToken(context)).toSingle();
    }

    @Override
    public Single<ContactsHolder> getEmergencyContactsLocalByState(DataStates dataState) {
        return Single.fromCallable(() -> new ContactsHolder(emergencyContactsTable
                .getLocalEmergencyContactsByState(dataState)));
    }

    @Override
    public Single<ContactsHolder> getEmergencyContactsLocal() {
        return Single.fromCallable(() -> new ContactsHolder(emergencyContactsTable
                .getLocalEmergencyContacts()));
    }

    @Override
    public Completable deleteFromEmergencyList(ContactsNumbersHolder contactsNumbersHolder) {
        return serverApi.deleteContactsFromEmergencyList(contactsNumbersHolder,
                PrefsUtil.getAuthToken(context)).toCompletable();
    }

    @Override
    public Completable deleteFromEmergencyListLocal(List<ContactModel> contacts) {
        return Completable.fromCallable(() -> emergencyContactsTable.deleteLocalContacts(contacts));
    }

    @Override
    public Completable cleanLocalContactsList() {
        return Completable.fromCallable(() -> emergencyContactsTable.deleteAllLocalContacts());
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

        return eliminateDuplicates(contacts);
    }

    private List<ContactModel> eliminateDuplicates(List<ContactModel> contacts) {
        return rx.Observable.from(contacts).distinct().toList().toBlocking().first();
    }
}
