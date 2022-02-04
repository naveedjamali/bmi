import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

enum Month {
    Jan,
    Feb,
    Mar,
    Apr,
    May,
    Jun,
    Jul,
    Aug,
    Sep,
    Oct,
    Nov,
    Dec
}

public class HealthProfileTest {
    public static void main(String[] args) {
        /**
        * Please note that:
         * this program has been written on Ubuntu operating system (Not Microsoft Windows, or macOS).
         * If the program throws 'FileNotFoundError', copy the path of input.txt file,
         * and paste it in the 'filepath' variable,
         * and run it again. Thank you.
        */

        /**
         * Array list to store profiles.
         */
        ArrayList<HealthProfile> profiles = new ArrayList<>();


        /**
         * Arraylist to store each line from the input file
         */
        ArrayList<String> data = new ArrayList<>();

        /**
         * Read lines from the file.
         */
        try {
            profiles = new ArrayList<>();

            /**
             * Path must be accurate, if the program throws file not found exception then check the path you are providing.
             */
            String filePath = "/home/naveed/eclipse-workspace/bmi/src/input.txt";
            File inputFile = new File(filePath);
            Scanner myReader = new Scanner(inputFile);

            while (myReader.hasNextLine()) {

                /**
                 * Go through each line a put the line the data arraylist declared above.
                 */
                String readLine = myReader.nextLine();
                if(readLine.isEmpty()){
                    continue;
                }
                data.add(readLine);
            }
        } catch (FileNotFoundException e) {

            System.out.println(e.getMessage());
        }

        /**
         * Fields to store profile data
         */
        String firstName = "";
        String lastName = "";
        Date dob = new Date(1, Month.Jan, 2021);
        double height = 0;
        double weight = 0;
        int currentYear = 2022;

        for (int i = 0; i < data.size(); i++) {
            /**
             * traverse the data arraylist, read lines, and create person info
             */

            /**
             * Our input file is structured, and we know that one user's data occupy 5 lines, so every five lines represent a single user's data.
             */
            switch (i % 5) {
                case 0: {
                    /**
                     * first name is on zero th line. (first line)
                     */
                    firstName = data.get(i);
                    break;
                }
                case 1: {
                    /**
                     * Second name is on 1th line (second line)
                     */
                    lastName = data.get(i);
                    break;
                }
                case 2: {
                    /**
                     * Date of birth is on the third line.
                     */
                    String[] date = data.get(i).split(" "); // split line to read day, month, year separately.
                    int day = Integer.parseInt(date[0]);
                    Month month = Month.valueOf(date[1]);
                    int year = Integer.parseInt(date[2]);
                    dob = new Date(day, month, year);
                    break;

                }
                case 3: {

                    /**
                     * Read bmi data, and assign it to weight, and height fields.
                     */
                    String[] bmiData = data.get(i).split(" ");
                    height = Double.parseDouble(bmiData[0]);
                    weight = Double.parseDouble(bmiData[1]);
                    break;

                }
                case 4: {
                    /**
                     * read the current year on fifth line, and finally create a HealthProfile object and put it in the profiles array list.
                     */
                    currentYear = Integer.parseInt(data.get(i));
                    HealthProfile profile = new HealthProfile(firstName, lastName, dob, height, weight, currentYear);
                    profiles.add(profile);
                }

            }

        }

        /**
         * Print info of each profile
         */
        for (int i = 0; i < profiles.size(); i++) {
            profiles.get(i).printInfo();
        }

    }
}

class HealthProfile {
    private String firstName;
    private String lastName;
    private Date dob;
    private double height;
    private double weight;
    private int currentYear;

    public HealthProfile(String firstName, String lastName, Date dob, double height, double weight, int currentYear) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.currentYear = currentYear;
    }

    public HealthProfile(HealthProfile hp) {
        this.firstName = hp.firstName;
        this.lastName = hp.lastName;
        this.dob = hp.dob;
        this.height = hp.height;
        this.weight = hp.weight;
        this.currentYear = hp.currentYear;
    }

    public void setBMIInfo(double height, double weight) {
        this.height = height;
        this.weight = weight;
    }

    public double getBMI() {
        return weight / (height * height);
    }


    public int getMaximumHeartRate() {
        /**
         * According to the American Heart Association,
         * 220 - your age = Max Heart Rate
         * if you are 35 years old, then your maxHr = 220 - 35 = 185 beats per minute.
         */
        int age = currentYear - dob.getYear();
        int maxHr = 220 - age;
        return maxHr;
    }

    public void printInfo() {

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        System.out.println("Name: " + getFirstName() + ", " + getLastName());
        System.out.println("Date of Birth: " + getDob().getDay() + " " + getDob().getMonth().name() + " " + getDob().getYear());
        System.out.println("Your weight: " + getWeight() + " kg");
        System.out.println("Your Height: " + getHeight() + " meter");
        System.out.println("Current Year: " + getCurrentYear());
        System.out.println("Your age: " + (getCurrentYear() - getDob().getYear()) + " years old");
        System.out.println("Clinic analysis, base on your age:");
        System.out.println("\t\tYour maximum heart rate is " + df.format(getMaximumHeartRate()));
        System.out.println("\t\tYour minimum target heart rate is " + df.format(getMinimumTargetHeartRate()));
        System.out.println("\t\tYour maximum target heart rate is " + df.format(getMaximumTargetHeartRate()));
        System.out.println("Your BMI is " +df.format( getBMI()));
        System.out.println("\t\tWeight Categories\t\t\tRange");
        System.out.println("\t\tUnderweight / too low\t\tBelow 18.5");
        System.out.println("\t\tHealthy range\t\t\t\t18.5 - 25");
        System.out.println("\t\tOverweight\t\t\t\t\t25 - 30");
        System.out.println("\t\tObese\t\t\t\t\t\t30 - 35");
        System.out.println("\t\tSevere Obesity\t\t\t\t35 - 40");
        System.out.println("\t\tMorbid Obesity\t\t\t\tOver 40");
        System.out.println();
    }

    public double getMinimumTargetHeartRate() {
        double maxHr = getMaximumHeartRate();
        return maxHr / 2;
    }

    public double getMaximumTargetHeartRate() {
        double maxHr = getMaximumHeartRate();
        double maxTargetHeartRate = maxHr * 0.85;
        return maxTargetHeartRate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }
}

class Date {

    private int day;
    private Month month;
    private int year;

    public Date() {
        this.day = 1;
        this.month = Month.Jan;
        this.year = 2021;
    }

    public Date(int day, Month month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date(Date d) {
        this.day = d.day;
        this.month = d.month;
        this.year = d.year;
    }

    public void setDate(int day, Month month, int year) {
        this.year = year;
        this.month = month;
        this.day = day;

    }

    public int getDay() {
        return day;
    }


    public Month getMonth() {
        return month;
    }


    public int getYear() {
        return year;
    }

}
