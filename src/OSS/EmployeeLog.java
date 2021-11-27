package OSS;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

class EmployeeLog {
    private ArrayList<Employee> EmployeeLog = new ArrayList<Employee>();

    public void insertNew(Employee newEmployee) {
        EmployeeLog.add(newEmployee);
    }

    public ArrayList<Employee> getEmployeeLog() {
        return EmployeeLog;
    }

    public Boolean verifyLogIn(String username, String password) throws IOException {
        readFromFile();
        for (Employee Employees : EmployeeLog)  //This isn't calling for some reason
        {
            if (username.equals(Employees.username) && password.equals(Employees.passcode))
                return true;
        }
        return false;
    }
    public Boolean noDupUser(String username) throws IOException {
        readFromFile();
        for (Employee Employees : EmployeeLog)
        {
            if (username.equals(Employees.username)){
                return true;//The username is a duplicate
            }

        }return false;//Not a duplicate
    }

    public Employee associateUser(String username, String password) {
        for (Employee Employees : EmployeeLog) {
            if (Employees.nameMatch(username) && Employees.passMatch(password))
                return Employees;
        }
        return new Employee(); //This should never trigger, associateUser should only be called AFTER verifyLogin!
    }

    public void saveToFile(ArrayList<Employee> EmployeeLog) {
        try {
            File log = new File("EmployeeLog.txt");
            if (log.createNewFile()) {
                //System.out.println("Log Generated; Populating...");
                writeToFile();
            } else {
                //System.out.println("Existing Log Found; Updating...");
                writeToFile();
            }
        } catch (IOException e) {
            System.out.println("What did you dooooooo!");
        }
    }

    private void writeToFile() throws IOException {
        String output = "";
        PrintWriter clerk = new PrintWriter("EmployeeLog.txt");
        for (Employee current : EmployeeLog) {
            output += current.writeInfo() + "\n";
        }
        clerk.write(output.trim());
        clerk.close();
    }

    public void readFromFile() throws IOException {
        //setUpLog will create a file of this type if one did not already exist.
        File file = new File("EmployeeLog.txt");
        Scanner clerk = new Scanner(file);
        String eID;
        String dob;
        String nm;
        String salary;
        String address;
        String username;
        String pw;
        String[] input;


        while (clerk.hasNextLine()) {
            //Example Employee
            //C37A4B,06/18/1940,Arson Lupin,10000,H!5327 Dank Run!Lubbock!Texas!75555,LupinUsername,LupinPassword
            input = clerk.nextLine().split(",");
            int length = input.length;
            eID = input[0];
            dob = input[1];
            nm = input[2];
            salary = input[3];
            address = input[4];
            username = input[5];
            pw = input[6];
            Employee newEmployee = new Employee();
            newEmployee = newEmployee.setUp(eID, dob, nm, Double.parseDouble(salary), address, username, pw);
            insertNew(newEmployee);
        }
        clerk.close();
    }
}

//Commented out because the Employee log is in 1 file
//    public void setUpLog()
//    {
//        try
//        {
//            File logger = new File("EmployeeLog.txt");
//            if(logger.createNewFile())
//                System.out.println("EmployeeLog was created as new.");
//            else
//                System.out.println("EmployeeLog was found existing.");
//            readFromFile();
//        }
//        catch (IOException x)
//        {
//            System.out.println("The file failed to generate. We kinda need that to work.");
//        }
//    }
//
//}


