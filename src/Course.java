
//importing necessary class files

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Course {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// created a list of courses from the csv file provided

		List<List<String>> courses = new ArrayList<>();
		courses = new Course().scanFiles("courses.csv");

		// created a list of prerequisites from the csv file provided

		List<List<String>> preRequisites = new ArrayList<>();
		preRequisites = new Course().scanFiles("prerequisites.csv");

		// creating a list of completed courses to check before adding new courses to
		// register

		List<List<String>> completedCourses = new ArrayList<>();

		// adding course that do not have any prerequisites as they are independent
		// courses

		completedCourses = new Course().addNonPrerequisiteUnits(courses, preRequisites, completedCourses);

		// Add courses that can be registered after completing necessary prerequisites

		for (int s = 0; s < courses.size(); s++) {

			completedCourses = new Course().addPrerequisiteUnits(courses, preRequisites, completedCourses);

		}

		for (int i = 0; i < completedCourses.size(); i++) {

			List<String> doneUnit = new ArrayList<>();

			doneUnit = completedCourses.get(i);

			System.out.println(doneUnit);

		}
	}

	public List<List<String>> scanFiles(String filename) {

		File file = new File(filename);

		// created a 2-dimensional array of strings

		List<List<String>> data = new ArrayList<>();

		Scanner inputStream;

		try {

			inputStream = new Scanner(file);

			while (inputStream.hasNext()) {

				String line = inputStream.nextLine();

				String[] values = line.split(",");

				// this adds the currently parsed line to the 2-dimensional string array

				data.add(Arrays.asList(values));

			}

			inputStream.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		}

		return data;

	}

	public String checkCompletedCourses(List<List<String>> completedCourses, List<String> unit) {

		for (int k = 0; k < completedCourses.size(); k++) {

			List<String> doneUnit = new ArrayList<>();

			doneUnit = completedCourses.get(k);

			if (unit.get(0).equals(doneUnit.get(0))) {

				return "true";

			}

		}

		return "false";

	}

	public List<List<String>> addNonPrerequisiteUnits(List<List<String>> courses, List<List<String>> preRequisites,

			List<List<String>> completedCourses) {

		for (int i = 1; i < courses.size(); i++) {

			List<String> unit = new ArrayList<>();

			unit = courses.get(i);

			String present = "false";

			String check = null;

			for (int j = 1; j < preRequisites.size(); j++) {

				List<String> preRequisiteUnit = new ArrayList<>();

				preRequisiteUnit = preRequisites.get(j);

				if (unit.get(0).equals(preRequisiteUnit.get(0))) {

					present = "true";

				}

			}

			if (present == "false") {

				check = new Course().checkCompletedCourses(completedCourses, unit);

				if (check == "false") {

					completedCourses.add(unit);

				}

			}

		}

		return completedCourses;

	}
	
	// added pre req units function

	private List<List<String>> addPrerequisiteUnits(List<List<String>> courses, List<List<String>> preRequisites,

			List<List<String>> completedCourses) {

		for (int i = 1; i < courses.size(); i++) {

			List<String> unit = new ArrayList<>();

			unit = courses.get(i);

			String check = null;

			check = new Course().checkCompletedCourses(completedCourses, unit);

			if (check == "false") {

				String allgood = null;

				String preRequisiteDone = null;

				List<String> result = new ArrayList<>();

				for (int j = 1; j < preRequisites.size(); j++) {

					List<String> preRequisiteUnit = new ArrayList<>();

					preRequisiteUnit = preRequisites.get(j);

					preRequisiteDone = "false";

					if (unit.get(0).equals(preRequisiteUnit.get(0))) {

						for (int k = 0; k < completedCourses.size(); k++) {

							List<String> doneUnit = new ArrayList<>();

							doneUnit = completedCourses.get(k);

							if (preRequisiteUnit.get(1).equals(doneUnit.get(0))) {

								preRequisiteDone = "true";

							}

						}

						result.add(preRequisiteDone);

					}

				}

				for (int k = 0; k < result.size(); k++) {

					if (result.get(k).equals("false")) {

						allgood = "false";

					}

				}

				if (allgood != ("false")) {

					completedCourses.add(unit);

				}

			}

		}

		return completedCourses;

	}

}
