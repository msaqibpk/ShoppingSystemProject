package OSS;
import java.util.*;
import java.io.*;
import java.util.Scanner;
import java.text.DecimalFormat;

class bankerThread implements Runnable {

    ArrayList<bankCustomer> clients = new ArrayList<bankCustomer>();
    messageResponseBuffer m;

    class bankCustomer {
        double balance = 100.00;
        String name;
        String cardNumber;

        void setup(String name, String cardNumber, double balance) {
            this.name = name;
            this.cardNumber = cardNumber;
            this.balance = balance;
        }

        boolean isMatch(String cardNumber) {
            return cardNumber.equals(this.cardNumber);
        }

        String writeInfo() {
            return name+","+cardNumber+","+balance;
        }
    }

    bankerThread(messageResponseBuffer m) {
        this.m = m;
        new Thread(this, "BANK").start();
    }

    private int chargeAmount(String ossInput) {
        Random yeet = new Random();
        String[] input = ossInput.split(",");
        String number = input[0];
        double charge = Double.parseDouble(input[1]);
        for(bankCustomer current : clients) {
            if(current.isMatch(number)) {
                System.out.println("Match found.");
                if(current.balance > charge){
                    current.balance -= charge;
                    System.out.println("Updated balance is " + current.balance);
                    return (yeet.nextInt(8999) + 1000); //Confirm purchase.
                }
                else
                    return -2;
            }
        }
        return -1; //If OSS sees false in buffer, then it'll prompt for number again.
    }

    public void savetoFile() {
        try
        {
            File log = new File("clients.txt");
            if (log.createNewFile())
            {
                System.out.println("Log Generated; Populating...");
                writeToFile();
            }
            else
            {
                System.out.println("Existing Log Found; Updating...");
                writeToFile();
            }
        }
        catch (IOException e)
        {
            System.out.println("What did you dooooooo!");
        }
    }

    public void writeToFile() throws IOException
    {
        String output = "";
        PrintWriter clerk = new PrintWriter("clients.txt");
        for (bankCustomer current : clients) {
            output += current.writeInfo() + "\n";
        }
        clerk.write(output.trim());
        clerk.close();
    }

    public void readFromFile() throws IOException
    {
        //setUpLog will create a file of this type if one did not already exist.
        File file = new File("clients.txt");
        Scanner clerk = new Scanner(file);
        String name;
        String cardNumber;
        double balance;

        String[] input = new String[3];

        while(clerk.hasNextLine())
        {
            input = clerk.nextLine().split(",");
            name = input[0];
            cardNumber = input[1];
            balance = Double.parseDouble(input[2]);
            bankCustomer newClient = new bankCustomer();
            newClient.setup(name, cardNumber, balance);
            clients.add(newClient);
        }
        clerk.close();
    }

    public void run() {
        try {
            readFromFile();
        }
        catch(IOException o) {
            System.out.println("All you had to do was write the damn file CJ");
        }
        String ossInput = m.receive();
        try {
            m.reply(chargeAmount(ossInput));
        }
        catch(InterruptedException e) {
            System.out.println("You fool!");
        }
        try {
            writeToFile();
        }
        catch(IOException o){
            System.out.println("Please do not delete the file during runtime.");
        }
    }
}


class messageResponseBuffer {
    private boolean messageBufferFull = false;
    private boolean responseBufferFull = false;
    String message;
    int response;

    synchronized int send(String inMess) {
        message = inMess;
        messageBufferFull = true;
        System.out.println("Message: " + inMess + " sent to Buffer");
        notify();
        while(!responseBufferFull){
            try{
                System.out.println("Waiting for response buffer: ");
                wait();
            }
            catch(InterruptedException e){
                System.out.println("Thread Interrupted");
            }
        }
        int received = response;
        response = 0;
        responseBufferFull = false;
        System.out.println("Response buffer cleared");
        return received;
    }

    synchronized String receive() {
        while(!messageBufferFull) {
            try {
                System.out.println("Waiting on Message Buffer");
                wait();
            }
            catch(InterruptedException e) {
                System.out.println("Thread Interrupted");
            }
        }
        String outMess = message;
        System.out.println(outMess + " received.");
        message = "";
        messageBufferFull = false;
        System.out.println("Message buffer cleared.");
        return outMess;
    }

    synchronized void reply(int inRes) throws InterruptedException {
        response = inRes;
        responseBufferFull = true;
        System.out.println(response);
        notify();
    }

}