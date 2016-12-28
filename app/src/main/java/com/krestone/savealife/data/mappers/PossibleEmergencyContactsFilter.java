package com.krestone.savealife.data.mappers;


import com.krestone.savealife.data.entities.requests.ContactsNumbersHolder;

import java.util.ArrayList;
import java.util.List;

public class PossibleEmergencyContactsFilter {

    public ContactsNumbersHolder filter(ContactsNumbersHolder numbersInApp, ContactsNumbersHolder numbersFromEmergency) {
        List<String> possibleEmergencyContactsNumbers = copyInAppNumbers(numbersInApp.getNumbers());

        for (String possibleEmergencyContactNumber : possibleEmergencyContactsNumbers) {
            for (String numberFromEmergency : numbersFromEmergency.getNumbers()) {
                if (possibleEmergencyContactNumber.equals(numberFromEmergency)) {
                    possibleEmergencyContactsNumbers.remove(possibleEmergencyContactNumber);
                    break;
                }
            }
        }
        return new ContactsNumbersHolder(possibleEmergencyContactsNumbers);
    }

    private List<String> copyInAppNumbers(List<String> inAppNumbers) {
        List<String> copy = new ArrayList<>();
        for (String inAppNumber : inAppNumbers) {
            copy.add(inAppNumber);
        }
        return copy;
    }
}
