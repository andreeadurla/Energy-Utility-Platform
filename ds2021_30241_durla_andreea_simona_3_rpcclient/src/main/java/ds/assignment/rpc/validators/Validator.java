package ds.assignment.rpc.validators;

import ds.assignment.rpc.exceptions.InvalidDataException;

public class Validator {

    public static void isEmptyOrNull(String text) {
        if(text == null || text.isEmpty())
            throw new InvalidDataException("Text field cannot be empty");
    }
}
