package com.krestone.savealife.data.entities.requests;


import java.util.ArrayList;
import java.util.List;

public class ContactsNumbersHolder {

    private List<String> numbers;

    public ContactsNumbersHolder() {
        numbers = new ArrayList<>();
    }

    public ContactsNumbersHolder(List<String> numbers) {
        this.numbers = numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }

    public List<String> getNumbers() {
        return numbers;
    }
}
