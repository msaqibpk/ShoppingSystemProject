import java.io.FileWriter;
import java.util.*;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;


class CustomerLog {
    private ArrayList<Customer> CustomerLog = new ArrayList<Customer>();

    public void insertNew(Customer newCustomer) {
        CustomerLog.add(newCustomer);
    }

    public ArrayList<Customer> getCustomerLog() {
        return CustomerLog;
    }

    public Boolean verifyLogIn(String phoneNum, String custPIN) throws IOException {
        readFromFile();
        for (Customer Customers : CustomerLog)  //This isn't calling for some reason
        {
            if (phoneNum.equals(Customers.phoneNum) && custPIN.equals(Customers.custPIN))
                return true;
        }
        return false;
    }

    public Customer associateUser(String phoneNum, String custPIN) {
        for (Customer Customers : CustomerLog) {
            if (Customers.phoneMatch(phoneNum) && Customers.pinMatch(custPIN))
                return Customers;
        }
        return new Customer(); //This should never trigger, associateUser should only be called AFTER verifyLogin!
    }

    public void saveToFile(ArrayList<Customer> CustomerLog) {
        try {
            File log = new File("CustomerLog.txt");
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
        File log = new File("CustomerLog.txt");
        FileWriter clerk = new FileWriter(log);
        for (Customer current : CustomerLog) {
            output += current.writeInfo() + "\n";
        }
        clerk.write(output.trim());
        clerk.close();
    }

    public void readFromFile() throws IOException {
        CustomerLog.clear();
        //setUpLog will create a file of this type if one did not already exist.
        File file = new File("CustomerLog.txt");
        Scanner clerk = new Scanner(file);

        Boolean iP = false;
        String phone;
        String cID;
        int custPoints;
        String nm;
        String bID = null;
        String sID = null;
        String[] input;


        while (clerk.hasNextLine()) {
            //ExampleCustomer
            //P,(123)456-7890,C37A4B,(CustomerPoints),Arsene Lupin,BANK:,630724666,SHIP:,H!5327 Dank Run!Lubbock!Texas!75555
            input = clerk.nextLine().split(",");
            int length = input.length;
//            System.out.println(length+ "= length");
//            for(int i = 0; i<input.length;i++){
//                System.out.println(input[i]);
//            }
            //System.out.println(input[0]);
            if(input[0].equals("P"))
                iP = true;
            cID = input[1];
            phone = input[2];
            //System.out.println(input[1]);
            custPoints = Integer.parseInt(input[3]);
            nm = input[4];

            //Checks if banking information is available.
            if (input[5].equals("BANK:")) {
                bID = input[6];
                //Subsequent check for shipping info.
                if (input[7].equals("SHIP:"))
                    sID = input[8];
            }
            //Standalone check for shipping.
            else if (input[5].equals("SHIP:")) {
                sID = input[6];
            }
            Customer newCustomer = new Customer();
            newCustomer = newCustomer.setUp(iP, phone, cID, custPoints, nm, bID, sID);
            insertNew(newCustomer);
        }
        clerk.close();
    }
}