import window.ProgramInterface;

import java.sql.SQLException;

public class StartProgram {

    public static void main(String[] args) throws SQLException {
        //AddToBase.addToBase();
        ProgramInterface programInterface = new ProgramInterface();
        programInterface.initInterface();
    }
}
