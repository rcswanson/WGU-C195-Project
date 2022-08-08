package Main;

import Helper.JDBC;

public class Main {

    public static void main(String[] args) {
	// write your code here

        JDBC.openConnection();

        JDBC.closeConnection();

    }
}
