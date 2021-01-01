package com.company;

import java.util.Scanner;
/*
    For running the dataStore,
    use this class.
 */
public class DataStoreClient {
    static final String fileName="data.json";
    public static void main(String[] args) throws Exception {
        /*
        Some template for running the program, so that it will be clear to the clients.
         */
        System.out.println("Welcome to DataStore, here you can create, read and delete the key-value pair.");
        String path = "Default path is projectDirectory/dataStoreFile\n"
                + "For manual path these are some convention that you need to follows\n"
                + "for window:- C:/Users/<current user>/directory/\n"
                + "for linux:- /home/<current user>/directory/\n";
        String filePath = "";
        System.out.println(path);

        System.out.print("Default path for dataStore, please enter (y/n) :- ");
        Scanner scanner = new Scanner(System.in);

        //used for checking the path.
        char ch=scanner.next().charAt(0);
        if(ch=='y'||ch=='Y'){
            //It will create the json file in project directory.
            filePath=fileName;
            loadAllFunctions(scanner,filePath);

        }else if(ch=='n'||ch=='N'){
            System.out.println("Please enter only the file path, not the file name.");
            filePath = scanner.next();
            //This will create the json file, under client path.
            filePath += fileName;
            loadAllFunctions(scanner, filePath);
        }else{
            System.out.println("Program terminate you enter wrong key, please run it again.");
        }
    }
    /*
        It's a menu driven program,
        where client can access all three operation
        create, read and delete.
     */
    static void loadAllFunctions(Scanner scanner, String filePath) throws Exception {
        //variable for choice between function.
        int choice = 0;

        //variable used for taking inputs from client.
        String key = "", value = "";
        long timeToLive;

        //Creating the object of DataStore class where all function written.
        DataStore dataStore = new DataStore(filePath);
        do {
            System.out.println("Select form 1-4 for your purpose.");
            System.out.println("1. Create new key - value pair.");
            System.out.println("2. Read key - value pair.");
            System.out.println("3. Delete key - value pair.");
            System.out.println("4. For exit.");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter key:- ");
                    key = scanner.next();
                    System.out.println("Enter value:- ");
                    value = scanner.next();
                    System.out.println("Enter timeToLive(in seconds) :- ");
                    timeToLive = scanner.nextLong();

                    //calling the create function to create the key-value pair in dataStore.
                    dataStore.create(key, value, timeToLive);
                    break;
                case 2:
                    System.out.println("Enter key:- ");
                    key = scanner.next();

                    //calling the read function for getting the key-value pair from dataStore.
                    System.out.println(dataStore.read(key));
                    break;
                case 3:
                    System.out.println("Enter key:- ");
                    key = scanner.next();

                    //calling the delete function for deleting the key-value pair from dataStore.
                    dataStore.delete(key);
                    break;
                default:
                    //for exit form program.
                    System.out.println("Program terminate.");
                    break;
            }
        } while (choice == 1 || choice == 2 || choice == 3);

        //This is called to unlock the file, so that after this other process can also access the file.
        dataStore.unlockFile();
    }
}
