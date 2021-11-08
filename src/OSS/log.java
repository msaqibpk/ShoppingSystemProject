package OSS;
import java.util.*;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;


class log {
    private ArrayList<Customer> CustomerLog = new ArrayList<Customer>();

    public void insertNew(Customer newCustomer) {
        CustomerLog.add(newCustomer);
    }

    public ArrayList<Customer> getCustomerLog() {
        return CustomerLog;
    }

    public Boolean verifyLogIn(String username, String password) throws IOException {
        readFromFile();
        for (Customer Customers : CustomerLog)  //This isn't calling for some reason
        {
            if (username.equals(Customers.username) && password.equals(Customers.password))
                return true;
        }
        return false;
    }
    public Boolean noDupUser(String username) throws IOException {
        readFromFile();
        for (Customer Customers : CustomerLog)
        {
            if (username.equals(Customers.username)){
                return true;//The username is a duplicate
            }

        }return false;//Not a duplicate
    }

    public Customer associateUser(String username, String password) {
        for (Customer Customers : CustomerLog) {
            if (Customers.nameMatch(username) && Customers.passMatch(password))
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
        PrintWriter clerk = new PrintWriter("CustomerLog.txt");
        for (Customer current : CustomerLog) {
            output += current.writeInfo() + "\n";
        }
        clerk.write(output.trim());
        clerk.close();
    }

    public void readFromFile() throws IOException {
        //setUpLog will create a file of this type if one did not already exist.
        File file = new File("CustomerLog.txt");
        Scanner clerk = new Scanner(file);
        String id;
        String pw;
        String nm;
        String pn;
        Boolean iP = false;
        String bID = null;
        String sID = null;
        String[] input;


        while (clerk.hasNextLine()) {
            //Lupin,World,George Lupin,(123)456-7890,P,BANK:,630724666,SHIP:,H!5327 Dank Run!Lubbock!Texas!75555
            input = clerk.nextLine().split(",");
            int length = input.length;
//            System.out.println(length+ "= length");
//            for(int i = 0; i<input.length;i++){
//                System.out.println(input[i]);
//            }
            //System.out.println(input[0]);
            id = input[0];
            //System.out.println(input[1]);
            pw = input[1];
            nm = input[2];
            pn = input[3];

            //Checks to see if Customer is a premium user.
            if (input[4].equals("P"))
                iP = true;
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
            newCustomer = newCustomer.setUp(id, pw, nm, pn, iP, bID, sID);
            insertNew(newCustomer);
        }
        clerk.close();
    }
}

//Commented out because the customer log is in 1 file
//    public void setUpLog()
//    {
//        try
//        {
//            File logger = new File("CustomerLog.txt");
//            if(logger.createNewFile())
//                System.out.println("CustomerLog was created as new.");
//            else
//                System.out.println("CustomerLog was found existing.");
//            readFromFile();
//        }
//        catch (IOException x)
//        {
//            System.out.println("The file failed to generate. We kinda need that to work.");
//        }
//    }
//
//}


