package com.greenorange.myuicontantsbackup.Task.Contacts;

import java.util.ArrayList;
import java.util.List;


public class UserContact{
    private ArrayList<UserContactData> contact;

    public ArrayList<UserContactData> getContact() {
        return contact;
    }

    public void setContact(ArrayList<UserContactData> contact) {
        this.contact = contact;
    }
	
    public boolean isSame(UserContact otherContact){
        if(otherContact.getContact()!=null && otherContact.getContact().size()>0){
            for(UserContactData contact:this.contact){
                boolean hasFound = false;
                for(UserContactData contact2:otherContact.getContact()){
                    if(contact.equals(contact2)){
                        hasFound = true;
                        break;
                    }
                }
                if(!hasFound){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
	
}
