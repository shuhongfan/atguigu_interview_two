package com.shf.TransferValue;

public class TransferValue {
    public void changeValue1(int age) {
        age = 30;
    }

    public void changeValue2(Person person) {
        person.setPersonName("xxx");
    }

    public void changeValue3(String str) {
        str = "xxx";
    }

    public static void main(String[] args) {
        TransferValue transferValue = new TransferValue();

        int age = 20;
        transferValue.changeValue1(age);
        System.out.println("age---------"+age);

        Person person = new Person("abc");
        transferValue.changeValue2(person);
        System.out.println("personName-----"+person.getPersonName());

        String str = "abc";
        transferValue.changeValue3(str);
        System.out.println("string---------"+str);
    }
}
