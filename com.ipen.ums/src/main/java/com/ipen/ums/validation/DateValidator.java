package com.ipen.ums.validation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateValidator {

	public static String validateDateOfBirth(String dateOfBirth) {
		if (dateOfBirth == null || dateOfBirth.isEmpty()) {
			return "Date of birth cannot be empty.";
		}
		try {
			LocalDate dob = LocalDate.parse(dateOfBirth);
			LocalDate currentDate = LocalDate.now();
			if (dob.isAfter(currentDate)) {
				return "Date of birth cannot be in the future.";
			}
			return null; // Date of birth is valid
		} catch (DateTimeParseException e) {
			return "Invalid date format for date of birth.";
		}
	}
}
